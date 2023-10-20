package views

import CreateRequestViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertCircle
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Plus
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.github.aakira.napier.log
import models.CreateRequestAction
import models.CreateRequestEvent
import models.VehicleType
import org.company.rado.core.MainRes
import other.observeAsState
import platform.LocalPlatform
import platform.Platform
import theme.Theme
import widgets.common.ActionButton

@Composable
fun CreateRequestAlertDialog(
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {

    val viewModel = viewModelFactory { CreateRequestViewModel() }.createViewModel()
    val state = viewModel.viewStates().observeAsState()
    val action = viewModel.viewActions().observeAsState()

    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop
    val imageSize =
        if (isLargePlatform) (LocalDensity.current.density.dp * 70) else (LocalDensity.current.density.dp * 25)

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
                Row(modifier = Modifier.clickable { onExit.invoke() }) {
                    Icon(imageVector = FeatherIcons.ArrowLeft, contentDescription = null)

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = MainRes.string.back_title,
                        fontSize = 18.sp,
                        color = Theme.colors.primaryTextColor,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = MainRes.string.choose_machine_title,
                    fontSize = 18.sp,
                    color = Theme.colors.primaryTextColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    ImageMachineCells(
                        imageSize = imageSize,
                        title = MainRes.string.tractor_title,
                        imageLink = "https://radoapp.serveo.net/resources/tractor.jpg",
                        isExpanded = state.value.tractorIsExpanded,
                        eventHandler = {
                            viewModel.obtainEvent(
                                CreateRequestEvent.SelectedTypeVehicleChanged(
                                    value = VehicleType.Tractor
                                )
                            )
                            viewModel.obtainEvent(CreateRequestEvent.TractorIsExpandedChanged)
                        }
                    )

                    ImageMachineCells(
                        imageSize = imageSize,
                        title = MainRes.string.trailer_title,
                        imageLink = "https://radoapp.serveo.net/resources/trailer.jpg",
                        isExpanded = state.value.trailerIsExpanded,
                        eventHandler = {
                            viewModel.obtainEvent(
                                CreateRequestEvent.SelectedTypeVehicleChanged(
                                    value = VehicleType.Trailer
                                )
                            )
                            viewModel.obtainEvent(CreateRequestEvent.TrailerIsExpandedChanged)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Theme.colors.primaryTextColor,
                        unfocusedTextColor = Theme.colors.primaryTextColor,
                        disabledTextColor = Theme.colors.primaryTextColor,
                    ),
                    value = state.value.numberVehicle,
                    isError = state.value.notVehicleNumber,
                    supportingText = {
                        if (state.value.notVehicleNumber) Text(
                            text = MainRes.string.number_vehicle_error_message,
                            color = Theme.colors.errorColor
                        )
                    },
                    trailingIcon = {
                        if (state.value.notVehicleNumber) Icon(
                            FeatherIcons.AlertCircle,
                            contentDescription = null
                        )
                    },
                    onValueChange = {
                        viewModel.obtainEvent(
                            viewEvent = CreateRequestEvent.NumberVehicleChanged(
                                value = it
                            )
                        )
                    },
                    label = {
                        Text(
                            text = MainRes.string.number_vehicle_label,
                            color = Theme.colors.primaryTextColor,
                            fontSize = if (isLargePlatform) 16.sp else 8.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Theme.colors.primaryTextColor,
                        unfocusedTextColor = Theme.colors.primaryTextColor,
                        disabledTextColor = Theme.colors.primaryTextColor,
                    ),
                    value = state.value.faultDescription,
                    onValueChange = {
                        viewModel.obtainEvent(
                            viewEvent = CreateRequestEvent.FaultDescriptionChanged(value = it)
                        )
                    },
                    label = {
                        Text(
                            text = MainRes.string.fault_description_label,
                            color = Theme.colors.primaryTextColor,
                            fontSize = if (isLargePlatform) 16.sp else 8.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = MainRes.string.image_fault_title,
                    fontSize = 18.sp,
                    color = Theme.colors.primaryTextColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Box(modifier = Modifier.clickable {
                            viewModel.obtainEvent(viewEvent = CreateRequestEvent.FilePickerVisibilityChanged)
                        }.size(imageSize).background(Theme.colors.primaryAction)) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = FeatherIcons.Plus,
                                contentDescription = null
                            )
                        }
                    }
                    items(state.value.images.count()) {
                        ImageCells(
                            size = imageSize,
                            isExpanded = state.value.imageIsExpanded,
                            imageLink = state.value.images.get(it).first,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            eventHandler = {
                                viewModel.obtainEvent(viewEvent = CreateRequestEvent.ImageRepairExpandedChanged)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ActionButton(
                    text = MainRes.string.send_repair_request_title,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.obtainEvent(viewEvent = CreateRequestEvent.CreateRequest)
                    })
            }
        }

        ImageFilePicker(
            showFilePicker = state.value.showFilePicker,
            closeFilePicker = { viewModel.obtainEvent(viewEvent = CreateRequestEvent.FilePickerVisibilityChanged) },
            receiveFilePathAndByteArray = { filePath, imageBytearray ->
                viewModel.obtainEvent(
                    viewEvent = CreateRequestEvent.SetImage(
                        filePath = filePath,
                        imageByteArray = imageBytearray
                    )
                )
            })

        if (state.value.showSuccessCreateRequestDialog) {
            SuccessCreateRequestDialog(
                onDismiss = {
                    viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseSuccessDialog)
                }, onExit = {
                    viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseSuccessDialog)
                })
        }

        if (state.value.showFailureCreateRequestDialog) {
            FailureCreateRequestDialog(
                onDismiss = { viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseFailureDialog) },
                onExit = { viewModel.obtainEvent(viewEvent = CreateRequestEvent.CloseFailureDialog) }
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