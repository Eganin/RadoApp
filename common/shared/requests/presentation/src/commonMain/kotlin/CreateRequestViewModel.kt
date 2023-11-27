import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.CreateRequestIdItem
import models.create.CreateRequestAction
import models.create.CreateRequestEvent
import models.create.CreateRequestViewState
import models.create.getVehicleType
import other.BaseSharedViewModel
import other.WrapperForResponse
import time.convertDateLongToString

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
            is CreateRequestEvent.SelectedTypeVehicleTractor -> {
                obtainIsSelectedTractor()
                obtainEvent(CreateRequestEvent.TractorIsExpandedChanged)
            }

            is CreateRequestEvent.SelectedTypeVehicleTrailer -> {
                obtainIsSelectedTrailer()
                obtainEvent(CreateRequestEvent.TrailerIsExpandedChanged)
            }

            is CreateRequestEvent.NumberVehicleChanged -> obtainNumberVehicleChange(numberVehicle = viewEvent.value)
            is CreateRequestEvent.FaultDescriptionChanged -> obtainFaultDescriptionChange(
                faultDescription = viewEvent.value
            )

            is CreateRequestEvent.TractorIsExpandedChanged -> obtainTractorIsExpandedChange()
            is CreateRequestEvent.TrailerIsExpandedChanged -> obtainTrailerIsExpandedChange()
            is CreateRequestEvent.CloseSuccessDialog -> closeSuccessDialog()
            is CreateRequestEvent.CloseFailureDialog -> closeFailureDialog()
            is CreateRequestEvent.FilePickerVisibilityChanged -> obtainFilePickerVisibilityChange()
            is CreateRequestEvent.SetResource -> saveResourceToStateList(
                resource = Triple(
                    first = viewEvent.filePath,
                    second = viewEvent.isImage,
                    third = viewEvent.imageByteArray
                )
            )

            is CreateRequestEvent.ImageRepairExpandedChanged -> obtainImageIsExpandedChange()
            is CreateRequestEvent.OnBackClick -> removeCacheResources()
            is CreateRequestEvent.ArrivalDateChanged ->obtainArrivalDate(arrivalDate = convertDateLongToString(date=viewEvent.arrivalDate))
            is CreateRequestEvent.ShowDatePicker->obtainShowDatePicker(value = true)
            is CreateRequestEvent.CloseDatePicker->obtainShowDatePicker(value = false)
        }
    }

    private fun createRequest() {
        coroutineScope.launch {
            viewState = viewState.copy(isLoading = !viewState.isLoading)

            //check choose vehicle
            if (!viewState.isSelectedTractor && !viewState.isSelectedTrailer) {
                obtainNotChooseVehicle(value = true)
            } else {
                obtainNotChooseVehicle(value = false)
            }

            if (viewState.numberVehicle.isNotEmpty() && !viewState.notChooseVehicle) {
                viewState = viewState.copy(notVehicleNumber = false)
                log(tag = "VEHICLE") { viewState.isSelectedTractor.toString() }
                log(tag = "VEHICLE") { viewState.isSelectedTrailer.toString() }
                val createRequestIdItem = activeRequestsRepository.createRequest(
                    typeVehicle = getVehicleType(
                        isSelectedTractor = viewState.isSelectedTractor,
                        isSelectedTrailer = viewState.isSelectedTrailer
                    ),
                    numberVehicle = viewState.numberVehicle,
                    faultDescription = viewState.faultDescription,
                    arrivalDate = viewState.arrivalDate
                )
                if (createRequestIdItem is CreateRequestIdItem.Success) {
                    log(tag = TAG) { "Create request is success" }
                    saveResources(requestId = createRequestIdItem.requestId)

                    removeCacheResources(requestId = createRequestIdItem.requestId)

                    obtainShowSuccessDialog()
                } else if (createRequestIdItem is CreateRequestIdItem.Error) {
                    log(tag = TAG) { "Create request is failure" }
                    obtainShowFailureDialog(value = !viewState.showFailureCreateRequestDialog)
                }
            } else if (viewState.numberVehicle.isEmpty()) {
                viewState = viewState.copy(notVehicleNumber = true)
            }
            viewState = viewState.copy(isLoading = !viewState.isLoading)
        }
    }

    private fun saveResources(requestId: Int) = coroutineScope.launch {
        //save images
        if (viewState.resources.isNotEmpty()) {
            viewState.resources.forEach { resource ->
                log(tag = TAG) { resource.first }
                val response = activeRequestsRepository.createResourceForRequest(
                    requestId = requestId,
                    resource = resource
                )
                if (response is WrapperForResponse.Failure) {
                    removeRequest(requestId = requestId)
                    obtainShowFailureDialog(value = true)
                }
            }
        }
    }

    private fun removeCacheResources(requestId: Int? = null) = coroutineScope.launch {
        //remove cache images
        viewState.resources.forEach {
            val response = activeRequestsRepository.deleteResourceForCache(resourceName = it.first)
            if (response is WrapperForResponse.Failure) {
                requestId?.let {
                    removeRequest(requestId = it)
                }
                obtainShowFailureDialog(value = true)
            }
        }
        clearResourceList()
    }

    private fun removeRequest(requestId: Int) = coroutineScope.launch {
        activeRequestsRepository.deleteRequest(requestId = requestId)
    }

    private fun obtainArrivalDate(arrivalDate: String) {
        viewState = viewState.copy(arrivalDate = arrivalDate)
    }

    private fun obtainShowDatePicker(value: Boolean){
        viewState=viewState.copy(showDatePicker = value)
    }

    private fun obtainShowSuccessDialog() {
        viewState =
            viewState.copy(showSuccessCreateRequestDialog = !viewState.showSuccessCreateRequestDialog)
    }

    private fun obtainShowFailureDialog(value: Boolean) {
        viewState = viewState.copy(showFailureCreateRequestDialog = value)
    }

    private fun obtainIsSelectedTractor() {
        viewState = viewState.copy(isSelectedTractor = !viewState.isSelectedTractor)
        log(tag = "SELECTED trailer") { viewState.isSelectedTrailer.toString() }
        log(tag = "SELECTED tractor") { viewState.isSelectedTractor.toString() }
    }

    private fun obtainIsSelectedTrailer() {
        viewState = viewState.copy(isSelectedTrailer = !viewState.isSelectedTrailer)
        log(tag = "SELECTED trailer") { viewState.isSelectedTrailer.toString() }
        log(tag = "SELECTED tractor") { viewState.isSelectedTractor.toString() }
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

    private fun saveResourceToStateList(resource: Triple<String, Boolean, ByteArray>) {
        coroutineScope.launch {
            //save image for cache
            viewState = viewState.copy(isLoading = !viewState.isLoading)
            activeRequestsRepository.createResourceForCache(resource = resource)
            val newResourcesList = mutableListOf(resource)
            newResourcesList.addAll(viewState.resources)
            log(tag = TAG) { "Count resources ${newResourcesList.size}" }
            viewState = viewState.copy(resources = newResourcesList)
            log(tag = TAG) { "Add resource ${viewState.resources}" }
            viewState = viewState.copy(isLoading = !viewState.isLoading)
        }
    }

    private fun clearResourceList() {
        viewState = viewState.copy(resources = emptyList())
    }

    private fun obtainFilePickerVisibilityChange() {
        viewState = viewState.copy(showFilePicker = !viewState.showFilePicker)
    }

    private fun obtainTractorIsExpandedChange() {
        viewState = viewState.copy(tractorIsExpanded = !viewState.tractorIsExpanded)
        obtainNotChooseVehicle(value = false)
        log(tag = TAG) { "Tractor expanded changed ${viewState.tractorIsExpanded}" }
    }

    private fun obtainImageIsExpandedChange() {
        viewState = viewState.copy(imageIsExpanded = !viewState.imageIsExpanded)
        log(tag = TAG) { "Image expanded changed ${viewState.imageIsExpanded}" }
    }

    private fun obtainTrailerIsExpandedChange() {
        viewState = viewState.copy(trailerIsExpanded = !viewState.trailerIsExpanded)
        obtainNotChooseVehicle(value = false)
        log(tag = TAG) { "Trailer expanded changed ${viewState.trailerIsExpanded}" }
    }

    private fun obtainNotChooseVehicle(value: Boolean) {
        viewState = viewState.copy(notChooseVehicle = value)
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