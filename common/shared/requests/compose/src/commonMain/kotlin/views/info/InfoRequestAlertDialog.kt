package views.info

import InfoRequestViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.icerock.moko.mvvm.compose.viewModelFactory
import models.info.InfoRequestEvent
import models.info.InfoRequestViewState
import org.company.rado.core.MainRes
import other.Position
import other.observeAsState
import platform.LocalPlatform
import platform.Platform
import theme.Theme
import views.create.ImageCells
import views.shared.videoplayer.VideoPlayerCell
import views.widgets.AlertDialogChooseMachine

@Composable
fun InfoRequestAlertDialog(
    onDismiss: () -> Unit,
    requestId: Int,
    infoForPosition: Position,
    isActiveRequest: Boolean,
    isArchiveRequest: Boolean,
    isRejectRequest: Boolean,
    modifier: Modifier = Modifier,
    actionControl: @Composable (InfoRequestViewState) -> Unit = {}
) {
    val viewModel = viewModelFactory { InfoRequestViewModel() }.createViewModel()
    val state = viewModel.viewStates().observeAsState()

    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop
    val isWeb = LocalPlatform.current == Platform.Web
    val imageSize =
        if (isLargePlatform) (LocalDensity.current.density.dp * 70) else (LocalDensity.current.density.dp * 25)

    LaunchedEffect(key1 = Unit) {
        viewModel.obtainEvent(
            InfoRequestEvent.RequestGetInfo(
                requestId = requestId,
                infoForPosition = infoForPosition,
                isActiveRequest = isActiveRequest,
                isArchiveRequest = isArchiveRequest,
                isRejectRequest = isRejectRequest
            )
        )
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
                AlertDialogChooseMachine(
                    title = MainRes.string.type_machine_title,
                    imageSize = imageSize,
                    tractorIsExpanded = state.value.isSelectedTractor,
                    trailerIsExpanded = state.value.isSelectedTrailer
                )

                Text(
                    text = MainRes.string.number_vehicle_title,
                    fontSize = 12.sp,
                    color = Theme.colors.primaryTextColor,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = state.value.numberVehicle,
                    fontSize = 12.sp,
                    color = Theme.colors.primaryTextColor,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (state.value.faultDescription.isNotEmpty()) {
                    Text(
                        text = MainRes.string.fault_description_title,
                        fontSize = 12.sp,
                        color = Theme.colors.primaryTextColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = state.value.faultDescription,
                        fontSize = 12.sp,
                        color = Theme.colors.primaryTextColor,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (state.value.arrivalDate.isNotEmpty()) {
                    Text(
                        text = MainRes.string.arrival_date_title,
                        color = Theme.colors.primaryTextColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = state.value.arrivalDate,
                        color = Theme.colors.primaryTextColor,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (state.value.streetRepair.isNotEmpty()) {
                    Text(
                        text = MainRes.string.repair_on_other_place_street_label,
                        color = Theme.colors.primaryTextColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = state.value.streetRepair,
                        color = Theme.colors.primaryTextColor,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (state.value.images.isNotEmpty() || state.value.videos.isNotEmpty()) {
                    Text(
                        text = MainRes.string.image_fault_unconfirmed_request,
                        fontSize = 12.sp,
                        color = Theme.colors.primaryTextColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        items(state.value.images) {
                            ImageCells(
                                size = imageSize,
                                isExpanded = state.value.imageIsExpanded,
                                imageLink = it,
                                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                                isRemove = false,
                                eventHandler = {
                                    viewModel.obtainEvent(viewEvent = InfoRequestEvent.ImageRepairExpandedChanged)
                                }
                            )
                        }

                        if (!isWeb){
                            items(state.value.videos) {
                                VideoPlayerCell(
                                    size = imageSize,
                                    isExpanded = state.value.imageIsExpanded,
                                    url = it,
                                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                                    eventHandler = {
                                        viewModel.obtainEvent(viewEvent = InfoRequestEvent.ImageRepairExpandedChanged)
                                    },
                                    isRemove = false
                                )
                            }
                        }
                    }
                }

                actionControl(state.value)

                if (state.value.errorTitleMessage.isNotEmpty()) {
                    Text(
                        text = MainRes.string.base_error_message,
                        fontSize = 24.sp,
                        color = Theme.colors.primaryTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                if (state.value.isLoading) {
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
    }
}