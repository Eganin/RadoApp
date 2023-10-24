package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.MechanicRequestsEvent
import models.MechanicRequestsViewState
import org.company.rado.core.MainRes
import theme.Theme
import views.create.RequestCells
import views.info.InfoRequestAlertDialog
import widgets.common.ActionButton

@Composable
fun RequestsMechanicView(
    state: MechanicRequestsViewState,
    modifier: Modifier = Modifier,
    eventHandler: (MechanicRequestsEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground)
            .padding(all = 16.dp)
    ) {
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

                ActionButton(
                    text = MainRes.string.choose_time_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { eventHandler.invoke(MechanicRequestsEvent.ChooseDateTime) })

                Spacer(modifier = Modifier.height(16.dp))

                ActionButton(
                    text = MainRes.string.reject_request_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { eventHandler.invoke(MechanicRequestsEvent.RejectRequest) })

                Spacer(modifier = Modifier.height(16.dp))

                ActionButton(
                    text = MainRes.string.close_window_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { eventHandler.invoke(MechanicRequestsEvent.CloseInfoDialog) })
            }
        )
    }
}