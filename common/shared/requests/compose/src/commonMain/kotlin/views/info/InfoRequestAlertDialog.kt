package views.info

import InfoRequestViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.icerock.moko.mvvm.compose.viewModelFactory
import models.info.InfoRequestEvent
import other.observeAsState
import theme.Theme

@Composable
fun InfoRequestAlertDialog(
    onDismiss: () -> Unit,
    requestId: Int,
    modifier: Modifier = Modifier,
    actionControl: @Composable () -> Unit = {},
) {
    val viewModel = viewModelFactory { InfoRequestViewModel() }.createViewModel()
    val state = viewModel.viewStates().observeAsState()

    LaunchedEffect(Unit) {
        viewModel.obtainEvent(InfoRequestEvent.UnconfirmedRequestGetInfo(requestId = requestId))
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.primaryBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                actionControl()
            }
        }
    }
}