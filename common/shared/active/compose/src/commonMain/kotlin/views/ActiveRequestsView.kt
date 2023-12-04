package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import models.ActiveEvent
import models.ActiveViewState
import org.company.rado.core.MainRes
import other.Position
import platform.LocalPlatform
import platform.Platform
import theme.Theme
import time.convertDateLongToString
import time.datetimeStringToPrettyString
import views.create.CalendarView
import views.create.FailureRequestDialog
import views.create.RequestCells
import views.create.SuccessRequestDialog
import views.info.InfoRequestAlertDialog
import widgets.common.ActionButton
import widgets.common.TextStickyHeader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRequestsView(
    state: ActiveViewState,
    position: Position,
    modifier: Modifier = Modifier,
    eventHandler: (ActiveEvent) -> Unit
) {

    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop

    val datePickerState = rememberDatePickerState()

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            eventHandler.invoke(
                ActiveEvent.SelectedDateChanged(
                    value = convertDateLongToString(
                        date = it
                    )
                )
            )
        }
    }

    //pull refresh every half minute
    LaunchedEffect(key1 = Unit) {
        while (true) {
            eventHandler.invoke(ActiveEvent.PullRefresh)
            delay(30000L)
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
            .background(color = Theme.colors.primaryBackground)
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = MainRes.string.active_requests_page_title,
            fontSize = 16.sp,
            color = Theme.colors.primaryTextColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActionButton(
            text = MainRes.string.update_date_title,
            onClick = {
                eventHandler.invoke(ActiveEvent.PullRefresh)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        CalendarView(
            state = datePickerState,
            modifier = Modifier
                .heightIn(max = if (isLargePlatform) 600.dp else 500.dp)
                .widthIn(max = if (isLargePlatform) 600.dp else 400.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (state.errorTextForRequestList.isEmpty()) {
            if (state.requestsForMechanic.isNotEmpty()) {
                state.requestsForMechanic.forEach {
                    RequestCells(
                        firstText = it.typeVehicle,
                        secondText = it.numberVehicle,
                        isReissueRequest = true,
                        onClick = {
                            eventHandler.invoke(ActiveEvent.OpenDialogInfoRequest(requestId = it.id))
                        },
                        onReissueRequest = {
                            eventHandler.invoke(ActiveEvent.OpenDialogInfoRequest(requestId = it.id))
                        },
                        reissueRequestText = MainRes.string.archieve_request_title
                    )
                }
            } else if (state.requestsForObserver.isNotEmpty()) {
                state.requestsForObserver.forEach {
                    RequestCells(
                        firstText = datetimeStringToPrettyString(dateTime = it.datetime),
                        secondText = it.mechanicName,
                        isReissueRequest = false,
                        onClick = {
                            eventHandler.invoke(ActiveEvent.OpenDialogInfoRequest(requestId = it.id))
                        }
                    )
                }
            } else {
                TextStickyHeader(
                    textTitle = MainRes.string.empty_title,
                    fontSize = 16,
                    modifier = Modifier.fillMaxWidth().padding(all = 16.dp)
                )
            }
        } else {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = MainRes.string.requests_error_loading,
                color = Theme.colors.primaryTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (state.showInfoDialog) {
            InfoRequestAlertDialog(
                onDismiss = { eventHandler.invoke(ActiveEvent.CloseInfoDialog) },
                requestId = state.requestIdForInfo,
                infoForPosition = position,
                isActiveRequest = true,
                isArchiveRequest = false,
                isRejectRequest = false,
                actionControl = { infoRequestState ->
                    if (infoRequestState.driverPhone.isNotEmpty()) {
                        Text(
                            text = MainRes.string.contact_a_driver + infoRequestState.driverPhone,
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (infoRequestState.mechanicPhone.isNotEmpty() && position==Position.OBSERVER) {
                        Text(
                            text = MainRes.string.contact_a_mechanic + infoRequestState.mechanicPhone,
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (infoRequestState.datetime.isNotEmpty()) {
                        Text(
                            text = MainRes.string.datetime_title + datetimeStringToPrettyString(
                                dateTime = infoRequestState.datetime
                            ),
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (position == Position.MECHANIC){
                        ActionButton(
                            text = MainRes.string.archieve_request_title,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { eventHandler.invoke(ActiveEvent.ArchieveRequest) })

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    ActionButton(
                        text = MainRes.string.close_window_title,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { eventHandler.invoke(ActiveEvent.CloseInfoDialog) })
                }
            )
        }

        if (state.showArchieveRequestSuccessDialog) {
            SuccessRequestDialog(
                onDismiss = {
                    eventHandler.invoke(ActiveEvent.CloseSuccessDialog)
                }, onExit = {
                    eventHandler.invoke(ActiveEvent.CloseSuccessDialog)
                },
                firstText = MainRes.string.archieve_request_success_title,
                secondText = ""
            )
        }

        if (state.showArchieveRequestFailureDialog) {
            FailureRequestDialog(
                onDismiss = { eventHandler.invoke(ActiveEvent.CloseFailureDialog) },
                onExit = { eventHandler.invoke(ActiveEvent.CloseFailureDialog) },
                firstText = MainRes.string.archieve_request_failure_title
            )
        }
    }
}