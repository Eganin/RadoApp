package views.recreate

import RecreateRequestViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.github.aakira.napier.log
import models.recreate.RecreateRequestAction
import models.recreate.RecreateRequestEvent
import org.company.rado.core.MainRes
import other.observeAsState
import platform.LocalPlatform
import platform.Platform
import theme.Theme
import views.create.FailureRequestDialog
import views.create.ResourceFilePicker
import views.create.SuccessRequestDialog
import views.widgets.AlertDialogChooseImageAndVideo
import views.widgets.AlertDialogChooseMachine
import views.widgets.AlertDialogTextInputs
import views.widgets.AlertDialogTopBar
import widgets.common.ActionButton
import widgets.common.CircularLoader

@Composable
fun RecreateRequestAlertDialog(
    requestId: Int,
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModelFactory { RecreateRequestViewModel() }.createViewModel()
    val state = viewModel.viewStates().observeAsState()
    val action = viewModel.viewActions().observeAsState()

    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop
    val imageSize =
        if (isLargePlatform) (LocalDensity.current.density.dp * 70) else (LocalDensity.current.density.dp * 25)

    LaunchedEffect(key1 = Unit) {
        viewModel.obtainEvent(
            viewEvent = RecreateRequestEvent.GetInfoForOldUnconfirmedRequest(
                requestId = requestId
            )
        )
    }

    Dialog(
        onDismissRequest = {
            viewModel.obtainEvent(viewEvent = RecreateRequestEvent.OnBackClick)
            onDismiss.invoke()
        },
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
                AlertDialogTopBar {
                    viewModel.obtainEvent(viewEvent = RecreateRequestEvent.OnBackClick)
                    onExit.invoke()
                }

                AlertDialogChooseMachine(
                    title = MainRes.string.choose_machine_title,
                    imageSize = imageSize,
                    tractorIsExpanded = state.value.tractorIsExpanded,
                    trailerIsExpanded = state.value.trailerIsExpanded,
                    isError = state.value.notChooseVehicle,
                    onClickTractor = {
                        viewModel.obtainEvent(RecreateRequestEvent.SelectedTypeVehicleTractor)
                    },
                    onClickTrailer = {
                        viewModel.obtainEvent(RecreateRequestEvent.SelectedTypeVehicleTrailer)
                    })

                AlertDialogTextInputs(
                    numberVehicle = state.value.numberVehicle,
                    notVehicleNumber = state.value.notVehicleNumber,
                    faultDescription = state.value.faultDescription,
                    isLargePlatform = isLargePlatform,
                    arrivalDate="",
                    numberVehicleOnChange = {
                        viewModel.obtainEvent(
                            viewEvent = RecreateRequestEvent.NumberVehicleChanged(
                                value = it
                            )
                        )
                    },
                    faultDescriptionOnChange = {
                        viewModel.obtainEvent(
                            viewEvent = RecreateRequestEvent.FaultDescriptionChanged(value = it)
                        )
                    },
                    arrivalDateOnClick = {

                    }
                )

                AlertDialogChooseImageAndVideo(
                    imageSize = imageSize,
                    resources = state.value.resources,
                    resourceIsExpanded = state.value.imageIsExpanded,
                    createdImages = state.value.images,
                    createdVideos = state.value.videos,
                    isRemoveImageAndVideo = true,
                    addResource = {
                        viewModel.obtainEvent(viewEvent = RecreateRequestEvent.FilePickerVisibilityChanged)
                    },
                    imageOnClick = {
                        viewModel.obtainEvent(viewEvent = RecreateRequestEvent.ImageRepairExpandedChanged)
                    },
                    videoOnClick = {
                        viewModel.obtainEvent(viewEvent = RecreateRequestEvent.ImageRepairExpandedChanged)
                    },
                    removeImageAction = { imagePath ->
                        viewModel.obtainEvent(viewEvent = RecreateRequestEvent.RemoveImage(imagePath = imagePath))
                    },
                    removeVideoAction = { videoPath ->
                        viewModel.obtainEvent(viewEvent = RecreateRequestEvent.RemoveVideo(videoPath = videoPath))
                    },
                    removeImageFromResourceAction = { imagePath ->
                        viewModel.obtainEvent(
                            viewEvent = RecreateRequestEvent.RemoveImageFromResource(
                                imagePath = imagePath
                            )
                        )
                    },
                    removeVideoFromResourceAction = { videoPath ->
                        viewModel.obtainEvent(
                            viewEvent = RecreateRequestEvent.RemoveVideoFromResource(
                                videoPath = videoPath
                            )
                        )
                    }
                )

                ActionButton(
                    text = MainRes.string.send_recreate_request_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.obtainEvent(viewEvent = RecreateRequestEvent.RecreateRequest)
                    })

                Spacer(modifier = Modifier.height(16.dp))

                ActionButton(
                    text = MainRes.string.send_delete_request_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.obtainEvent(viewEvent = RecreateRequestEvent.DeleteRequest)
                    })
            }
        }

        CircularLoader(isLoading = state.value.isLoading)

        ResourceFilePicker(
            showFilePicker = state.value.showFilePicker,
            closeFilePicker = { viewModel.obtainEvent(viewEvent = RecreateRequestEvent.FilePickerVisibilityChanged) },
            receiveFilePathAndByteArray = { filePath, isImage, imageBytearray ->
                viewModel.obtainEvent(
                    viewEvent = RecreateRequestEvent.SetResource(
                        filePath = filePath,
                        imageByteArray = imageBytearray,
                        isImage = isImage
                    )
                )
            })

        if (state.value.showSuccessDialog) {
            SuccessRequestDialog(
                onDismiss = {
                    viewModel.obtainEvent(viewEvent = RecreateRequestEvent.CloseSuccessDialog)
                }, onExit = {
                    viewModel.obtainEvent(viewEvent = RecreateRequestEvent.CloseSuccessDialog)
                },
                firstText = "",
                secondText = MainRes.string.base_success_message
            )
        }

        if (state.value.showFailureDialog) {
            FailureRequestDialog(
                onDismiss = { viewModel.obtainEvent(viewEvent = RecreateRequestEvent.CloseFailureDialog) },
                onExit = { viewModel.obtainEvent(viewEvent = RecreateRequestEvent.CloseFailureDialog) },
                firstText = MainRes.string.base_error_message
            )
        }
    }

    when (action.value) {
        is RecreateRequestAction.CloseReCreateRequestAlertDialog -> {
            log(tag = "CLOSE") { "close dialog" }
            onExit()
        }

        null -> {}
    }
}