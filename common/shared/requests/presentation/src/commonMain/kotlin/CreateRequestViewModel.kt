import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.create.CreateRequestAction
import models.create.CreateRequestEvent
import models.CreateRequestIdItem
import models.create.CreateRequestViewState
import models.create.VehicleType
import other.BaseSharedViewModel

class CreateRequestViewModel :
    BaseSharedViewModel<CreateRequestViewState, CreateRequestAction, CreateRequestEvent>(
        initialState = CreateRequestViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val activeRequestsRepository: ActiveRequestsForDriverRepository = Inject.instance()
    override fun obtainEvent(viewEvent: CreateRequestEvent) {
        when (viewEvent) {
            is CreateRequestEvent.CreateRequest -> createRequest()
            is CreateRequestEvent.SelectedTypeVehicleChanged -> obtainSelectedTypeVehicleChange(
                typeVehicle = viewEvent.value
            )

            is CreateRequestEvent.NumberVehicleChanged -> obtainNumberVehicleChange(numberVehicle = viewEvent.value)
            is CreateRequestEvent.FaultDescriptionChanged -> obtainFaultDescriptionChange(
                faultDescription = viewEvent.value
            )

            is CreateRequestEvent.TractorIsExpandedChanged -> obtainTractorIsExpandedChange()
            is CreateRequestEvent.TrailerIsExpandedChanged -> obtainTrailerIsExpandedChange()
            is CreateRequestEvent.CloseSuccessDialog -> closeSuccessDialog()
            is CreateRequestEvent.CloseFailureDialog -> closeFailureDialog()
            is CreateRequestEvent.FilePickerVisibilityChanged -> obtainFilePickerVisibilityChange()
            is CreateRequestEvent.SetImage -> saveImageToStateList(
                image = Pair(
                    first = viewEvent.filePath, second = viewEvent.imageByteArray
                )
            )

            is CreateRequestEvent.ImageRepairExpandedChanged -> obtainImageIsExpandedChange()
        }
    }

    private fun createRequest() {
        coroutineScope.launch {
            viewState = viewState.copy(isLoading = !viewState.isLoading)
            if (viewState.numberVehicle.isNotEmpty()) {
                viewState = viewState.copy(notVehicleNumber = false)
                val createRequestIdItem = activeRequestsRepository.createRequest(
                    typeVehicle = viewState.selectedVehicleType.nameVehicleType,
                    numberVehicle = viewState.numberVehicle,
                    faultDescription = viewState.faultDescription
                )
                if (createRequestIdItem is CreateRequestIdItem.Success) {
                    log(tag = TAG) { "Create request is success" }
                    //save images
                    if (viewState.images.isNotEmpty()) {
                        activeRequestsRepository.createImagesForRequest(
                            requestId = createRequestIdItem.requestId, images = viewState.images
                        )
                    }
                    viewState =
                        viewState.copy(showSuccessCreateRequestDialog = !viewState.showSuccessCreateRequestDialog)
                } else if (createRequestIdItem is CreateRequestIdItem.Error) {
                    log(tag = TAG) { "Create request is failure" }
                    viewState =
                        viewState.copy(showFailureCreateRequestDialog = !viewState.showFailureCreateRequestDialog)
                }
            } else {
                viewState = viewState.copy(notVehicleNumber = true)
            }
            viewState = viewState.copy(isLoading = !viewState.isLoading)
        }
    }

    private fun closeSuccessDialog() {
        viewState =
            viewState.copy(showSuccessCreateRequestDialog = !viewState.showSuccessCreateRequestDialog)
        viewAction = CreateRequestAction.CloseCreateRequestAlertDialog
        log(tag = TAG) { "Create request success dialog close" }
    }

    private fun closeFailureDialog() {
        viewState =
            viewState.copy(showFailureCreateRequestDialog = !viewState.showFailureCreateRequestDialog)
        viewAction = CreateRequestAction.CloseCreateRequestAlertDialog
        log(tag = TAG) { "Create request failure dialog close" }
    }

    private fun saveImageToStateList(image: Pair<String, ByteArray>) {
        val newImagesList = mutableListOf(image)
        newImagesList.addAll(viewState.images)
        log(tag = TAG) { "Count images ${newImagesList.size}" }
        viewState = viewState.copy(images = newImagesList)
        log(tag = TAG) { "Add image ${viewState.images}" }
    }

    private fun obtainFilePickerVisibilityChange() {
        viewState = viewState.copy(showFilePicker = !viewState.showFilePicker)
    }

    private fun obtainTractorIsExpandedChange() {
        viewState = viewState.copy(tractorIsExpanded = !viewState.tractorIsExpanded)
        viewState = viewState.copy(trailerIsExpanded = !viewState.trailerIsExpanded)
        log(tag = TAG) { "Tractor expanded changed ${viewState.tractorIsExpanded}" }
    }

    private fun obtainImageIsExpandedChange() {
        viewState = viewState.copy(imageIsExpanded = !viewState.imageIsExpanded)
        log(tag = TAG) { "Image expanded changed ${viewState.imageIsExpanded}" }
    }

    private fun obtainTrailerIsExpandedChange() {
        viewState = viewState.copy(trailerIsExpanded = !viewState.trailerIsExpanded)
        viewState = viewState.copy(tractorIsExpanded = !viewState.tractorIsExpanded)
        log(tag = TAG) { "Trailer expanded changed ${viewState.trailerIsExpanded}" }
    }

    private fun obtainSelectedTypeVehicleChange(typeVehicle: VehicleType) {
        viewState = viewState.copy(
            selectedVehicleType = typeVehicle
        )
        log(tag = TAG) { "Type vehicle is changed ${viewState.selectedVehicleType}" }
    }

    private fun obtainNumberVehicleChange(numberVehicle: String) {
        viewState = viewState.copy(
            numberVehicle = numberVehicle
        )
        log(tag = TAG) { "Number vehicle is changed ${viewState.numberVehicle}" }
    }

    private fun obtainFaultDescriptionChange(faultDescription: String) {
        viewState = viewState.copy(
            faultDescription = faultDescription
        )
        log(tag = TAG) { "Fault description is changed ${viewState.faultDescription}" }
    }

    private companion object {
        const val TAG = "CreateRequestViewModel"
    }
}