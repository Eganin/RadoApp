import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.FullRequestItem
import models.RecreateRequestItem
import models.UnconfirmedRequestInfoItem
import models.create.VehicleType
import models.create.toVehicleType
import models.recreate.RecreateRequestAction
import models.recreate.RecreateRequestEvent
import models.recreate.RecreateRequestViewState
import other.BaseSharedViewModel
import other.WrapperForResponse

class RecreateRequestViewModel :
    BaseSharedViewModel<RecreateRequestViewState, RecreateRequestAction, RecreateRequestEvent>(
        initialState = RecreateRequestViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val activeRequestsRepositoryForDriver: ActiveRequestsForDriverRepository =
        Inject.instance()
    private val activeRequestsRepository: ActiveRequestsRepository = Inject.instance()
    private val unconfirmedRequestsRepository: UnconfirmedRequestsRepository = Inject.instance()
    private val operationsOnRequestsRepository: OperationsOnRequestsRepository = Inject.instance()

    override fun obtainEvent(viewEvent: RecreateRequestEvent) {
        when (viewEvent) {
            is RecreateRequestEvent.GetInfoForOldUnconfirmedRequest -> getInfoForOldUnconfirmedRequest(
                requestId = viewEvent.requestId
            )

            is RecreateRequestEvent.GetInfoForOldActiveRequest -> getInfoForOldActiveRequest(
                requestId = viewEvent.requestId
            )

            is RecreateRequestEvent.RecreateRequest -> recreateRequest()
            is RecreateRequestEvent.SelectedTypeVehicleChanged -> obtainSelectedTypeVehicleChange(
                typeVehicle = viewEvent.value
            )

            is RecreateRequestEvent.NumberVehicleChanged -> obtainNumberVehicleChange(numberVehicle = viewEvent.value)
            is RecreateRequestEvent.FaultDescriptionChanged -> obtainFaultDescriptionChange(
                faultDescription = viewEvent.value
            )

            is RecreateRequestEvent.TrailerIsExpandedChanged -> obtainTrailerIsExpandedChange()
            is RecreateRequestEvent.TractorIsExpandedChanged -> obtainTractorIsExpandedChange()
            is RecreateRequestEvent.ImageRepairExpandedChanged -> obtainImageIsExpandedChange()
            is RecreateRequestEvent.CloseSuccessDialog -> closeSuccessDialog()
            is RecreateRequestEvent.CloseFailureDialog -> closeFailureDialog()
            is RecreateRequestEvent.FilePickerVisibilityChanged -> obtainFilePickerVisibilityChange()
            is RecreateRequestEvent.SetResource -> saveResourceToStateList(
                resource = Triple(
                    first = viewEvent.filePath,
                    second = viewEvent.isImage,
                    third = viewEvent.imageByteArray
                )
            )

            is RecreateRequestEvent.OnBackClick -> removeCacheResources()
            is RecreateRequestEvent.DeleteRequest -> removeRequest(requestId = viewState.requestId)
        }
    }

    private fun recreateRequest() {
        coroutineScope.launch {
            obtainIsLoadingChange()
            val recreateRequestItem = operationsOnRequestsRepository.recreateRequest(
                requestId = viewState.requestId,
                typeVehicle = viewState.selectedVehicleType.nameVehicleType,
                numberVehicle = viewState.numberVehicle,
                oldTypeVehicle = viewState.oldSelectedVehicleType.nameVehicleType,
                oldNumberVehicle = viewState.oldNumberVehicle,
                faultDescription = viewState.faultDescription
            )
            if (recreateRequestItem is RecreateRequestItem.Success){
                log(tag = TAG) { "request recreate success" }
                obtainShowSuccessDialog()
            }else if(recreateRequestItem is RecreateRequestItem.Error){
                log(tag = TAG) { "request recreate failure" }
                obtainShowFailureDialog(value = !viewState.showFailureDialog)
            }
            obtainIsLoadingChange()
        }
    }

    private fun getInfoForOldUnconfirmedRequest(requestId: Int) {
        coroutineScope.launch {
            obtainIsLoadingChange()
            obtainRequestIdChange(requestId = requestId)
            val unconfirmedRequestInfoItem =
                unconfirmedRequestsRepository.getInfoForUnconfirmedRequest(requestId = viewState.requestId)
            if (unconfirmedRequestInfoItem is UnconfirmedRequestInfoItem.Success) {
                log(tag = TAG) { "Get info for old unconfirmed request ${unconfirmedRequestInfoItem.requestInfo}" }
                val info = unconfirmedRequestInfoItem.requestInfo
                viewState = viewState.copy(
                    oldSelectedVehicleType = info.vehicleType.toVehicleType(),
                    oldNumberVehicle = info.vehicleNumber,
                    faultDescription = info.faultDescription,
                    images = info.images,
                    videos = info.videos
                )
            } else if (unconfirmedRequestInfoItem is UnconfirmedRequestInfoItem.Error) {
                log(tag = TAG) { "get info for unconfirmed request is failure" }
                obtainShowFailureDialog(value = !viewState.showFailureDialog)
            }
            obtainIsLoadingChange()
        }
    }

    private fun getInfoForOldActiveRequest(requestId: Int) {
        coroutineScope.launch {
            obtainIsLoadingChange()
            obtainRequestIdChange(requestId = requestId)
            val fullRequestItem =
                activeRequestsRepository.getActiveRequestInfo(requestId = viewState.requestId)
            if (fullRequestItem is FullRequestItem.Success) {
                log(tag = TAG) { "Get info for old active request ${fullRequestItem.request}" }
                val info = fullRequestItem.request
                viewState = viewState.copy(
                    oldSelectedVehicleType = info.vehicleType.toVehicleType(),
                    oldNumberVehicle = info.vehicleNumber,
                    faultDescription = info.faultDescription,
                    images = info.images,
                    videos = info.videos
                )
            } else if (fullRequestItem is FullRequestItem.Error) {
                log(tag = TAG) { "get info fo active request is failure" }
                obtainShowFailureDialog(value = !viewState.showFailureDialog)
            }
            obtainIsLoadingChange()
        }
    }

    private fun saveResourceToStateList(resource: Triple<String, Boolean, ByteArray>) {
        coroutineScope.launch {
            //save image for cache
            obtainIsLoadingChange()
            activeRequestsRepositoryForDriver.createResourceForCache(resource = resource)
            val newResourcesList = mutableListOf(resource)
            newResourcesList.addAll(viewState.resources)
            log(tag = TAG) { "Count resources ${newResourcesList.size}" }
            viewState = viewState.copy(resources = newResourcesList)
            log(tag = TAG) { "Add resource ${viewState.resources}" }
            obtainIsLoadingChange()
        }
    }

    private fun removeCacheResources(requestId: Int? = null) = coroutineScope.launch {
        //remove cache images
        viewState.resources.forEach {
            val response =
                activeRequestsRepositoryForDriver.deleteResourceForCache(resourceName = it.first)
            if (response is WrapperForResponse.Failure) {
                requestId?.let {
                    removeRequest(requestId = it)
                }
                obtainShowFailureDialog(value = true)
            }
        }
        clearResourceList()
    }

    private fun clearResourceList() {
        viewState = viewState.copy(resources = emptyList())
    }

    private fun obtainRequestIdChange(requestId: Int) {
        viewState = viewState.copy(requestId = requestId)
    }

    private fun obtainShowSuccessDialog() {
        viewState = viewState.copy(showSuccessDialog = !viewState.showSuccessDialog)
    }

    private fun obtainShowFailureDialog(value: Boolean) {
        viewState = viewState.copy(showFailureDialog = value)
    }

    private fun removeRequest(requestId: Int) = coroutineScope.launch {
        activeRequestsRepositoryForDriver.deleteRequest(requestId = requestId)
    }

    private fun obtainIsLoadingChange() {
        viewState = viewState.copy(isLoading = !viewState.isLoading)
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

    private fun obtainTrailerIsExpandedChange() {
        viewState = viewState.copy(trailerIsExpanded = !viewState.trailerIsExpanded)
        viewState = viewState.copy(tractorIsExpanded = !viewState.tractorIsExpanded)
        log(tag = TAG) { "Trailer expanded changed ${viewState.trailerIsExpanded}" }
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

    private fun closeSuccessDialog() {
        viewState =
            viewState.copy(showSuccessDialog = !viewState.showSuccessDialog)
        viewAction = RecreateRequestAction.CloseReCreateRequestAlertDialog
        log(tag = TAG) { "Create request success dialog close" }
    }

    private fun closeFailureDialog() {
        viewState =
            viewState.copy(showFailureDialog = !viewState.showFailureDialog)
        viewAction = RecreateRequestAction.CloseReCreateRequestAlertDialog
        log(tag = TAG) { "Create request failure dialog close" }
    }

    private fun obtainFilePickerVisibilityChange() {
        viewState = viewState.copy(showFilePicker = !viewState.showFilePicker)
    }

    private companion object {
        const val TAG = "RecreateRequestViewModel"
    }
}