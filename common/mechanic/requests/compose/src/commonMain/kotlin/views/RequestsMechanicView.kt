package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import models.MechanicRequestsEvent
import models.MechanicRequestsViewState
import org.company.rado.core.MainRes
import other.Position
import theme.Theme
import views.create.FailureRequestDialog
import views.create.RequestCells
import views.create.SuccessRequestDialog
import views.info.InfoRequestAlertDialog
import views.widgets.DatePicker
import widgets.common.ActionButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun RequestsMechanicView(
    state: MechanicRequestsViewState,
    modifier: Modifier = Modifier,
    eventHandler: (MechanicRequestsEvent) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit) {
        while (true) {
            eventHandler.invoke(MechanicRequestsEvent.PullRefresh)
            delay(30000L)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground)
            .padding(all = 16.dp)
    ) {
        item {
            ActionButton(
                text = MainRes.string.update_date_title,
                onClick = {
                    eventHandler.invoke(MechanicRequestsEvent.PullRefresh)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        if (state.errorTextForRequestList.isNotEmpty()) {
            item {
                Text(
                    text = MainRes.string.base_error_message,
                    fontSize = 24.sp,
                    color = Theme.colors.primaryTextColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        items(state.unconfirmedRequests) {
            RequestCells(
                firstText = it.vehicleType,
                secondText = it.vehicleNumber,
                isReissueRequest = false,
                onClick = {
                    eventHandler.invoke(MechanicRequestsEvent.OpenDialogInfoRequest(requestId = it.id))
                }
            )
        }
    }

    if (state.showInfoDialog) {
        InfoRequestAlertDialog(
            onDismiss = { eventHandler.invoke(MechanicRequestsEvent.CloseInfoDialog) },
            requestId = state.requestsIdForInfo,
            infoForPosition = Position.MECHANIC,
            isActiveRequest = false,
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
                }
                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Checkbox(
                            checked = state.repairOnBase,
                            onCheckedChange = {
                                eventHandler.invoke(
                                    MechanicRequestsEvent.CheckRepairOnBase(
                                        isChecked = it
                                    )
                                )
                            },
                            modifier = Modifier.padding(5.dp),
                            colors = CheckboxDefaults.colors(
                                checkedColor = Theme.colors.primaryAction
                            )
                        )

                        Text(
                            text = MainRes.string.repair_on_base_title,
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Checkbox(
                            checked = state.repairOnOtherPlace,
                            onCheckedChange = {
                                eventHandler.invoke(
                                    MechanicRequestsEvent.CheckRepairOnOtherPlace(
                                        isChecked = it
                                    )
                                )
                            },
                            modifier = Modifier.padding(5.dp),
                            colors = CheckboxDefaults.colors(
                                checkedColor = Theme.colors.primaryAction
                            )
                        )

                        Text(
                            text = MainRes.string.repair_on_other_place_title,
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }

                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Theme.colors.primaryTextColor,
                            unfocusedTextColor = Theme.colors.primaryTextColor,
                            disabledTextColor = Theme.colors.hintTextColor.copy(alpha = 0.5f),
                            disabledContainerColor = Theme.colors.hintTextColor.copy(alpha = 0.5f)
                        ),
                        enabled = state.isActiveInputFieldForStreet,
                        value = state.streetForRepair,
                        onValueChange = {
                            eventHandler.invoke(
                                MechanicRequestsEvent.StreetForRepairChanged(
                                    street = it
                                )
                            )
                        },
                        label = {
                            Text(
                                text = MainRes.string.repair_on_other_place_street_label,
                                color = Theme.colors.primaryTextColor
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                }


                Spacer(modifier = Modifier.height(16.dp))

                if (state.reopenDialog) {
                    Text(
                        text = MainRes.string.datetime_title + state.datetime,
                        fontSize = 21.sp,
                        color = Theme.colors.highlightColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                ActionButton(
                    text = if (state.reopenDialog) MainRes.string.modify_time_title else MainRes.string.choose_time_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        keyboardController?.hide()
                        if (state.reopenDialog) {
                            eventHandler.invoke(MechanicRequestsEvent.ReopenDialogInfoRequest)
                            eventHandler.invoke(MechanicRequestsEvent.OpenDatePicker)
                        } else {
                            eventHandler.invoke(MechanicRequestsEvent.OpenDatePicker)
                        }
                    })

                Spacer(modifier = Modifier.height(16.dp))

                if (state.reopenDialog) {
                    ActionButton(
                        text = MainRes.string.confirm_request_title,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { eventHandler.invoke(MechanicRequestsEvent.ConfirmationRequest) })
                } else {
                    ActionButton(
                        text = MainRes.string.reject_request_title,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { eventHandler.invoke(MechanicRequestsEvent.ShowRejectRequest) })
                }

                Spacer(modifier = Modifier.height(16.dp))

                ActionButton(
                    text = MainRes.string.close_window_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { eventHandler.invoke(MechanicRequestsEvent.CloseInfoDialog) })
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

    if (state.showSuccessDialog) {
        SuccessRequestDialog(
            onDismiss = {
                eventHandler.invoke(MechanicRequestsEvent.CloseSuccessDialog)
            }, onExit = {
                eventHandler.invoke(MechanicRequestsEvent.CloseSuccessDialog)
            },
            firstText = MainRes.string.success_request_confirmation_title,
            secondText = MainRes.string.success_request_confirmation_text
        )
    }

    if (state.showFailureDialog) {
        FailureRequestDialog(
            onDismiss = { eventHandler.invoke(MechanicRequestsEvent.CloseFailureDialog) },
            onExit = { eventHandler.invoke(MechanicRequestsEvent.CloseFailureDialog) },
            firstText = MainRes.string.failure_request_confirmation_title
        )
    }

    if (state.showDatePicker) {
        DatePicker(
            confirmAction = { dateLong ->
                eventHandler.invoke(MechanicRequestsEvent.OpenTimePicker(date = dateLong))
            }, exitAction = {
                eventHandler.invoke(MechanicRequestsEvent.CloseDatePicker)
            })
    }

    if (state.showTimePicker) {
        MechanicTimePicker(
            confirmAction = { (hour, minute) ->
                eventHandler.invoke(MechanicRequestsEvent.CloseDatePicker)
                eventHandler.invoke(MechanicRequestsEvent.CloseTimePicker)
                eventHandler.invoke(
                    MechanicRequestsEvent.SubmitDateTime(
                        hour = hour,
                        minute = minute
                    )
                )
                eventHandler.invoke(MechanicRequestsEvent.ReopenDialogInfoRequest)
            }, exitAction = {
                eventHandler.invoke(MechanicRequestsEvent.CloseTimePicker)
            })
    }

}