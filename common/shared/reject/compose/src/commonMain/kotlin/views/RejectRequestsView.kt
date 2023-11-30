package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import models.RejectEvent
import models.RejectRequestViewState
import org.company.rado.core.MainRes
import other.Position
import theme.Theme
import views.create.RequestCells
import views.info.InfoRequestAlertDialog
import views.recreate.RecreateRequestAlertDialog
import widgets.common.ActionButton
import widgets.common.CircularLoader
import widgets.common.TextStickyHeader

@Composable
fun RejectRequestsView(
    state: RejectRequestViewState,
    position: Position,
    modifier: Modifier = Modifier,
    eventHandler: (RejectEvent) -> Unit
) {
    //pull refresh every half minute
    LaunchedEffect(key1 = Unit) {
        while (true) {
            eventHandler.invoke(RejectEvent.PullRefresh)
            delay(30000L)
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
            .background(color = Theme.colors.primaryBackground)
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ActionButton(
            text = MainRes.string.update_date_title,
            onClick = {
                eventHandler.invoke(RejectEvent.PullRefresh)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        CircularLoader(isLoading = state.isLoading)

        if (state.errorTextForRequestList.isEmpty()) {
            if (state.requests.isNotEmpty()) {
                state.requests.forEach {
                    RequestCells(
                        firstText = it.numberVehicle,
                        secondText = it.mechanicName,
                        isReissueRequest = true,
                        onClick = {
                            eventHandler.invoke(RejectEvent.OpenInfoDialog(requestId = it.id))
                        },
                        onReissueRequest = {
                            eventHandler.invoke(RejectEvent.OpenRecreateDialog(requestId = it.id))
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
    }

    if (state.showInfoDialog) {
        InfoRequestAlertDialog(
            onDismiss = { eventHandler.invoke(RejectEvent.CloseInfoDialog) },
            requestId = state.requestIdForInfo,
            infoForPosition = position,
            isActiveRequest = false,
            isArchiveRequest = false,
            isRejectRequest = true,
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

                if (infoRequestState.driverName.isNotEmpty()) {
                    Text(
                        text = MainRes.string.driver_title + infoRequestState.driverName,
                        fontSize = 12.sp,
                        color = Theme.colors.primaryTextColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (infoRequestState.mechanicPhone.isNotEmpty()) {
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

                if (infoRequestState.mechanicName.isNotEmpty()) {
                    Text(
                        text = MainRes.string.mechanic_title + infoRequestState.mechanicName,
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
                    onClick = { eventHandler.invoke(RejectEvent.CloseInfoDialog) })
            }
        )
    }

    if (state.showRecreateDialog) {
        RecreateRequestAlertDialog(
            requestId = state.requestIdForInfo,
            isRejectRequest = true,
            onDismiss = { eventHandler.invoke(RejectEvent.CloseRecreateDialog) },
            onExit = { eventHandler.invoke(RejectEvent.CloseRecreateDialog) }
        )
    }
}