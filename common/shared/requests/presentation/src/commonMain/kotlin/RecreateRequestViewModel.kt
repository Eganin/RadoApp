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
            is RecreateRequestEvent.DeleteRequest -> removeRequestWrapper(requestId = viewState.requestId)
            is RecreateRequestEvent.RemoveImage -> removeImage(
                imagePath = viewEvent.imagePath,
                isResource = false
            )

            is RecreateRequestEvent.RemoveVideo -> removeVideo(
                videoPath = viewEvent.videoPath,
                isResource = false
            )

            is RecreateRequestEvent.RemoveImageFromResource -> removeImage(
                imagePath = viewEvent.imagePath,
                isResource = true
            )

            is RecreateRequestEvent.RemoveVideoFromResource -> removeVideo(
                videoPath = viewEvent.videoPath,
                isResource = true
            )
        }
    }

    private fun removeImage(imagePath: String, isResource: Boolean) {
        coroutineScope.launch {
            obtainIsLoadingChange()
            val wrapperForResponse =
                if (isResource) activeRequestsRepositoryForDriver.deleteResourceForCache(
                    resourceName = imagePath
                ) else activeRequestsRepositoryForDriver.deleteImageByPathForRequest(imagePath = imagePath)
            if (wrapperForResponse is WrapperForResponse.Success) {
                viewState = if (isResource) {
                    val newImagesList = mutableListOf<Triple<String, Boolean, ByteArray>>()
                    newImagesList.addAll(viewState.resources)
                    newImagesList.removeAll { it.first == imagePath }
                    viewState.copy(resources = newImagesList)
                } else {
                    val newImagesList = mutableListOf<String>()
                    newImagesList.addAll(viewState.images)
                    newImagesList.remove(imagePath)
                    viewState.copy(images = newImagesList)
                }
                log(tag = TAG) { "Image removed" }
            }
            obtainIsLoadingChange()
        }
    }

    private fun removeVideo(videoPath: String, isResource: Boolean) {
        coroutineScope.launch {
            obtainIsLoadingChange()
            log(tag="RESOURCE") { isResource.toString() }
            val wrapperForResponse =
                if (isResource) activeRequestsRepositoryForDriver.deleteResourceForCache(
                    resourceName = videoPath
                ) else activeRequestsRepositoryForDriver.deleteVideoByPathForRequest(videoPath = videoPath)
            if (wrapperForResponse is WrapperForResponse.Success) {
                viewState = if (isResource) {
                    val newVideosList = mutableListOf<Triple<String, Boolean, ByteArray>>()
                    newVideosList.addAll(viewState.resources)
                    newVideosList.removeAll { it.first == videoPath }
                    viewState.copy(resources = newVideosList)
                } else {
                    val newVideosList = mutableListOf<String>()
                    newVideosList.addAll(viewState.videos)
                    newVideosList.remove(videoPath)
                    viewState.copy(videos = newVideosList)
                }
                log(tag = TAG) { "Video removed" }
            }
            obtainIsLoadingChange()
        }
    }

    private fun recreateRequest() {
        coroutineScope.launch {
            obtainIsLoadingChange()
            if (viewState.numberVehicle.isNotEmpty()) {
                viewState = viewState.copy(notVehicleNumber = false)
                val recreateRequestItem = operationsOnRequestsRepository.recreateRequest(
                    requestId = viewState.requestId,
                    typeVehicle = viewState.selectedVehicleType.nameVehicleType,
                    numberVehicle = viewState.numberVehicle,
                    oldTypeVehicle = viewState.oldSelectedVehicleType.nameVehicleType,
                    oldNumberVehicle = viewState.oldNumberVehicle,
                    faultDescription = viewState.faultDescription
                )
                if (recreateRequestItem is RecreateRequestItem.Success) {
                    log(tag = TAG) { "request recreate success" }
                    saveResources(requestId = viewState.requestId)
                    removeCacheResources(requestId = viewState.requestId)
                    obtainShowSuccessDialog()
                } else if (recreateRequestItem is RecreateRequestItem.Error) {
                    log(tag = TAG) { "request recreate failure" }
                    obtainShowFailureDialog(value = !viewState.showFailureDialog)
                }
            } else {
                viewState = viewState.copy(notVehicleNumber = true)
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
                    videos = info.videos,
                    numberVehicle = info.vehicleNumber
                )
                if (viewState.oldSelectedVehicleType == VehicleType.Tractor) {
                    obtainTractorIsExpandedChange()
                } else {
                    obtainTrailerIsExpandedChange()
                }
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
                    videos = info.videos,
                    numberVehicle = info.vehicleNumber
                )
                if (viewState.oldSelectedVehicleType == VehicleType.Tractor) {
                    obtainTractorIsExpandedChange()
                } else {
                    obtainTrailerIsExpandedChange()
                }
            } else if (fullRequestItem is FullRequestItem.Error) {
                log(tag = TAG) { "get info fo active request is failure" }
                obtainShowFailureDialog(value = !viewState.showFailureDialog)
            }
            obtainIsLoadingChange()
        }
    }

    private fun removeRequestWrapper(requestId: Int) {
        coroutineScope.launch {
            val wrapperForDeleteResources =
                if (viewState.images.isNotEmpty() || viewState.videos.isNotEmpty()) activeRequestsRepositoryForDriver.deleteResourcesForRequest(
                    requestId = requestId
                ) else WrapperForResponse.Success()
            if (wrapperForDeleteResources is WrapperForResponse.Success) {
                val wrapperForResponse =
                    activeRequestsRepositoryForDriver.deleteRequest(requestId = requestId)
                if (wrapperForResponse is WrapperForResponse.Success) {
                    log(tag = TAG) { "request remove success" }
                    obtainShowSuccessDialog()
                } else if (wrapperForResponse is WrapperForResponse.Failure) {
                    log(tag = TAG) { "request remove failure" }
                    obtainShowFailureDialog(value = !viewState.showFailureDialog)
                }
            } else if (wrapperForDeleteResources is WrapperForResponse.Failure) {
                log(tag = TAG) { "resources remove failure" }
                obtainShowFailureDialog(value = !viewState.showFailureDialog)
            }
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

    private fun saveResources(requestId: Int) = coroutineScope.launch {
        //save images
        if (viewState.resources.isNotEmpty()) {
            viewState.resources.forEach { resource ->
                log(tag = TAG) { resource.first }
                val response = activeRequestsRepositoryForDriver.createResourceForRequest(
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
            val response =
                activeRequestsRepositoryForDriver.deleteResourceForCache(resourceName = it.first)
            if (response is WrapperForResponse.Failure) {
                requestId?.let { id ->
                    removeRequest(requestId = id)
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