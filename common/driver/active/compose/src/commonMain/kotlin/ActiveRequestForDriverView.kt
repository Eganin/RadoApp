import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.company.rado.core.MainRes
import theme.Theme
import widgets.active.CalendarView
import widgets.common.ActionButton

@Composable
fun ActiveRequestsForDriverView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground).padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CalendarView(
            submitInfo = { date ->

            },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )

        Spacer(modifier=Modifier.height(8.dp))

        ActionButton(text = MainRes.string.create_request_button_title, onClick = {

        })
    }
}