package views

import LocalPhoneControllerProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import models.DriverActiveEvent
import models.DriverActiveViewState
import org.company.rado.core.MainRes
import other.Position
import platform.LocalPlatform
import platform.Platform
import theme.Theme
import time.convertDateLongToString
import time.datetimeStringToPrettyString
import views.create.CalendarView
import views.create.CreateRequestAlertDialog
import views.create.RequestCells
import views.info.InfoRequestAlertDialog
import views.recreate.RecreateRequestAlertDialog
import widgets.common.ActionButton
import widgets.common.TextStickyHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRequestsForDriverView(
    state: DriverActiveViewState,
    modifier: Modifier = Modifier,
    eventHandler: (DriverActiveEvent) -> Unit
) {

    val phoneController = LocalPhoneControllerProvider.current

    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop

    val datePickerState = rememberDatePickerState()

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            eventHandler.invoke(
                DriverActiveEvent.SelectedDateChanged(
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
            eventHandler.invoke(DriverActiveEvent.PullRefresh)
            delay(30000L)
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground)
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
                eventHandler.invoke(DriverActiveEvent.PullRefresh)
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

        ActionButton(
            text = MainRes.string.create_request_button_title,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                eventHandler.invoke(DriverActiveEvent.OpenDialogCreateRequest)
            })

        Spacer(modifier = Modifier.height(8.dp))

        if (state.errorTextForRequestList.isEmpty()) {
            TextStickyHeader(
                textTitle = MainRes.string.active_requests_title,
                modifier = Modifier.fillMaxWidth()
            )

            if (state.requests.isNotEmpty()) {
                state.requests.forEach {
                    RequestCells(
                        firstText = datetimeStringToPrettyString(dateTime = it.datetime),
                        secondText = it.mechanicName,
                        onClick = {
                            eventHandler.invoke(
                                DriverActiveEvent.OpenDialogInfoRequest(
                                    requestId = it.id,
                                    isActiveRequest = true
                                )
                            )
                        },
                        isReissueRequest = false
                    )
                }
            } else {
                TextStickyHeader(
                    textTitle = MainRes.string.empty_title,
                    fontSize = 16,
                    modifier = Modifier.fillMaxWidth().padding(all = 16.dp)
                )
            }

            TextStickyHeader(
                textTitle = MainRes.string.unconfirmed_requests_title,
                modifier = Modifier.fillMaxWidth()
            )

            if (state.unconfirmedRequests.isNotEmpty()) {
                state.unconfirmedRequests.forEach {
                    RequestCells(
                        firstText = it.vehicleType,
                        secondText = it.vehicleNumber,
                        onClick = {
                            eventHandler.invoke(
                                DriverActiveEvent.OpenDialogInfoRequest(
                                    requestId = it.id,
                                    isActiveRequest = false
                                )
                            )
                        },
                        isReissueRequest = true,
                        onReissueRequest = {
                            eventHandler.invoke(
                                DriverActiveEvent.OpenDialogRecreateForUnconfirmedRequest(
                                    requestId = it.id
                                )
                            )
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

        if (state.showCreateDialog) {
            CreateRequestAlertDialog(
                onDismiss = { eventHandler.invoke(DriverActiveEvent.CloseCreateDialog) },
                onExit = { eventHandler.invoke(DriverActiveEvent.CloseCreateDialog) })
        }

        if (state.showRecreateDialog) {
            RecreateRequestAlertDialog(
                requestId = state.requestIdForInfo,
                isRejectRequest = false,
                onDismiss = { eventHandler.invoke(DriverActiveEvent.CloseRecreateDialog) },
                onExit = { eventHandler.invoke(DriverActiveEvent.CloseRecreateDialog) }
            )
        }

        if (state.showInfoDialog) {
            InfoRequestAlertDialog(
                onDismiss = { eventHandler.invoke(DriverActiveEvent.CloseInfoDialog) },
                requestId = state.requestIdForInfo,
                infoForPosition = Position.DRIVER,
                isActiveRequest = state.isActiveDialog,
                isArchiveRequest = false,
                isRejectRequest = false,
                actionControl = { infoRequestState ->
                    if (infoRequestState.mechanicPhone.isNotEmpty()) {
                        Text(
                            text = MainRes.string.contact_a_mechanic + infoRequestState.mechanicPhone,
                            fontSize = 12.sp,
                            color = Color.Blue,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                phoneController.openDialerPhone(phoneNumber = infoRequestState.mechanicPhone)
                            }.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

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

                    ActionButton(
                        text = MainRes.string.close_window_title,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { eventHandler.invoke(DriverActiveEvent.CloseInfoDialog) })
                }
            )
        }
    }
}