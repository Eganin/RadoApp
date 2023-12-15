package views.create

import CreateRequestViewModel
import LocalMediaControllerProvider
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.github.aakira.napier.log
import models.create.CreateRequestAction
import models.create.CreateRequestEvent
import org.company.rado.core.MainRes
import other.observeAsState
import platform.LocalPlatform
import platform.Platform
import theme.Theme
import views.widgets.AlertDialogChooseImageAndVideo
import views.widgets.AlertDialogChooseMachine
import views.widgets.AlertDialogTextInputs
import views.widgets.AlertDialogTopBar
import views.widgets.DatePicker
import views.widgets.PermissionDialog
import widgets.common.ActionButton
import widgets.common.CircularLoader

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateRequestAlertDialog(
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val mediaPickerController = LocalMediaControllerProvider.current
    val viewModel =
        viewModelFactory { CreateRequestViewModel(mediaController = mediaPickerController) }.createViewModel()
    val state = viewModel.viewStates().observeAsState()
    val action = viewModel.viewActions().observeAsState()

    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop
    val imageSize =
        if (isLargePlatform) (LocalDensity.current.density.dp * 70) else (LocalDensity.current.density.dp * 25)

    Dialog(
        onDismissRequest = {
            viewModel.obtainEvent(viewEvent = CreateRequestEvent.OnBackClick)
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
                    viewModel.obtainEvent(viewEvent = CreateRequestEvent.OnBackClick)
                    onExit.invoke()
                }

                AlertDialogChooseMachine(
                    title = MainRes.string.choose_machine_title,
                    imageSize = imageSize,
                    tractorIsExpanded = state.value.tractorIsExpanded,
                    trailerIsExpanded = state.value.trailerIsExpanded,
                    isError = state.value.notChooseVehicle,
                    onClickTractor = {
                        viewModel.obtainEvent(CreateRequestEvent.SelectedTypeVehicleTractor)
                    },
                    onClickTrailer = {
                        viewModel.obtainEvent(CreateRequestEvent.SelectedTypeVehicleTrailer)
                    })

                AlertDialogTextInputs(
                    numberVehicle = state.value.numberVehicle,
                    notVehicleNumber = state.value.notVehicleNumber,
                    faultDescription = state.value.faultDescription,
                    isLargePlatform = isLargePlatform,
                    arrivalDate = state.value.arrivalDate,
                    numberVehicleOnChange = {
                        viewModel.obtainEvent(
                            viewEvent = CreateRequestEvent.NumberVehicleChanged(
                                value = it
                            )
                        )
                    },
                    faultDescriptionOnChange = {
                        viewModel.obtainEvent(
                            viewEvent = CreateRequestEvent.FaultDescriptionChanged(value = it)
                        )
                    },
                    arrivalDateOnClick = {
                        keyboardController?.hide()
                        viewModel.obtainEvent(viewEvent = CreateRequestEvent.ShowDatePicker)
                    }
                )

                AlertDialogChooseImageAndVideo(
                    imageSize = imageSize,
                    resources = state.value.resources,
                    resourceIsExpanded = state.value.imageIsExpanded,
                    isRemoveImageAndVideo = false,
                    addImageResource = {
                        if (isLargePlatform) {
                            viewModel.obtainEvent(viewEvent = CreateRequestEvent.FilePickerVisibilityChanged)
                        } else {
                            viewModel.obtainEvent(viewEvent = CreateRequestEvent.CameraClick)
                        }
                    },
                    addVideoResource = {
                        if (isLargePlatform) {
                            viewModel.obtainEvent(viewEvent = CreateRequestEvent.FilePickerVisibilityChanged)
                        } else {
                            viewModel.obtainEvent(viewEvent = CreateRequestEvent.VideoClick)
                        }
                    },
                    imageOnClick = {
                        viewModel.obtainEvent(viewEvent = CreateRequestEvent.ImageRepairExpandedChanged)
                    },
                    videoOnClick = {
                        viewModel.obtainEvent(viewEvent = CreateRequestEvent.ImageRepairExpandedChanged)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ActionButton(
                    text = MainRes.string.send_repair_request_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.obtainEvent(viewEvent = CreateRequestEvent.CreateRequest)
                    })
            }
        }

        CircularLoader(isLoading = state.value.isLoading)

        ResourceFilePicker(
            showFilePicker = state.value.showFilePicker,
            closeFilePicker = { viewModel.obtainEvent(viewEvent = CreateRequestEvent.FilePickerVisibilityChanged) },
            receiveFilePathAndByteArray = { filePath, isImage, imageBytearray ->
                viewModel.obtainEvent(
                    viewEvent = CreateRequestEvent.SetResource(
                        filePath = filePath,
                        imageByteArray = imageBytearray,
                        isImage = isImage
                    )
                )
            })

        if (state.value.showSuccessCreateRequestDialog) {
            SuccessRequestDialog(
                onDismiss = {
                    viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseSuccessDialog)
                }, onExit = {
                    viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseSuccessDialog)
                },
                firstText = MainRes.string.success_create_request_title,
                secondText = MainRes.string.success_create_request_text
            )
        }

        if (state.value.showFailureCreateRequestDialog) {
            FailureRequestDialog(
                onDismiss = { viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseFailureDialog) },
                onExit = { viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseFailureDialog) },
                firstText = MainRes.string.failure_create_request_title
            )
        }

        if (state.value.showDatePicker) {
            DatePicker(
                confirmAction = { dateLong ->
                    viewModel.obtainEvent(
                        viewEvent = CreateRequestEvent.ArrivalDateChanged(
                            arrivalDate = dateLong
                        )
                    )
                    viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseDatePicker)
                },
                exitAction = {
                    viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseDatePicker)
                }
            )
        }

        if (state.value.cameraPermissionIsDenied) {
            PermissionDialog(
                firstText = MainRes.string.camera_permission_is_denied_title,
                secondText = MainRes.string.camera_permission_is_denied_description,
                onDismiss = {
                    viewModel.obtainEvent(
                        viewEvent = CreateRequestEvent.CameraPermissionDenied(
                            value = false
                        )
                    )
                },
                onExit = {
                    viewModel.obtainEvent(
                        viewEvent = CreateRequestEvent.CameraPermissionDenied(
                            value = false
                        )
                    )
                },
                successOnClick = {
                    viewModel.obtainEvent(
                        viewEvent = CreateRequestEvent.CameraPermissionDenied(
                            value = false
                        )
                    )
                    viewModel.obtainEvent(viewEvent = CreateRequestEvent.OpenAppSettings)
                }
            )
        }
    }

    when (action.value) {
        is CreateRequestAction.CloseCreateRequestAlertDialog -> {
            log(tag = "CLOSE") { "close dialog" }
            onExit()
        }

        null -> {}
    }
}