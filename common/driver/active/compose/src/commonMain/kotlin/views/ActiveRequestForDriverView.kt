package views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.DriverActiveEvent
import models.DriverActiveViewState
import org.company.rado.core.MainRes
import theme.Theme
import widgets.active.ActiveRequestCells
import widgets.active.CalendarView
import widgets.common.ActionButton

@Composable
fun ActiveRequestsForDriverView(
    state: DriverActiveViewState,
    modifier: Modifier = Modifier,
    eventHandler: (DriverActiveEvent) -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground).padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CalendarView(
            submitInfo = { date ->
                eventHandler.invoke(DriverActiveEvent.SelectedDateChanged(value = date))
            },
            modifier = Modifier
                .padding(12.dp)
                .heightIn(max = 500.dp)
                .widthIn(max = 500.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActionButton(text = MainRes.string.create_request_button_title, onClick = {
            eventHandler.invoke(DriverActiveEvent.OpenDialogCreateRequest)
        })

        Spacer(modifier = Modifier.height(8.dp))

        if (state.errorTextForRequestList.isEmpty()) {
            state.requests.forEach {
                ActiveRequestCells(
                    firstText = it.datetime,
                    secondText = it.mechanicName,
                    onClick = {},
                    isReissueRequest = false
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
}