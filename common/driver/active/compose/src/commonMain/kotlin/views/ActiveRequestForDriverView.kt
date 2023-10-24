package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.DriverActiveEvent
import models.DriverActiveViewState
import org.company.rado.core.MainRes
import theme.Theme
import time.convertDateLongToString
import time.datetimeStringToPrettyString
import views.create.CalendarView
import views.create.CreateRequestAlertDialog
import views.create.RequestCells
import views.info.InfoRequestAlertDialog
import widgets.common.ActionButton
import widgets.common.TextStickyHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRequestsForDriverView(
    state: DriverActiveViewState,
    modifier: Modifier = Modifier,
    eventHandler: (DriverActiveEvent) -> Unit
) {

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

    Column(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground)
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CalendarView(
            state = datePickerState,
            modifier = Modifier
                .padding(12.dp)
                .heightIn(max = 500.dp)
                .widthIn(max = 500.dp)
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
                            eventHandler.invoke(DriverActiveEvent.OpenDialogInfoRequest(requestId = it.id))
                        },
                        isReissueRequest = true,
                        onReissueRequest = {}
                    )
                }
            } else {
                TextStickyHeader(
                    textTitle = MainRes.string.empty_title,
                    fontSize = 16,
                    modifier = Modifier.fillMaxWidth()
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
                            eventHandler.invoke(DriverActiveEvent.OpenDialogInfoRequest(requestId = it.id))
                        },
                        isReissueRequest = false
                    )
                }
            } else {
                TextStickyHeader(
                    textTitle = MainRes.string.empty_title,
                    fontSize = 16,
                    modifier = Modifier.fillMaxWidth()
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

        if (state.showInfoDialog) {
            InfoRequestAlertDialog(
                onDismiss = { eventHandler.invoke(DriverActiveEvent.CloseInfoDialog) },
                requestId = state.requestIdForInfo,
                actionControl = { infoRequestState ->
                    if (infoRequestState.mechanicPhone.isNotEmpty()) {
                        Text(
                            text = MainRes.string.contact_a_mechanic + infoRequestState.mechanicPhone,
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
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
                    }

                    ActionButton(
                        text = MainRes.string.close_window_title,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { eventHandler.invoke(DriverActiveEvent.CloseInfoDialog) })
                }
            )
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = Theme.colors.highlightColor,
                    trackColor = Theme.colors.primaryAction
                )
            }
        }
    }
}