import com.benasher44.uuid.uuid4
import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.FullRejectRequestItem
import models.RecreateRequestItem
import models.UnconfirmedRequestInfoItem
import models.create.getVehicleType
import models.create.toVehicleType
import models.recreate.RecreateRequestAction
import models.recreate.RecreateRequestEvent
import models.recreate.RecreateRequestViewState
import other.BaseSharedViewModel
import other.WrapperForResponse
import picker.LocalMediaController
import picker.MediaSource
import time.convertDateLongToString

class RecreateRequestViewModel(
    private val mediaController: LocalMediaController
) :
    BaseSharedViewModel<RecreateRequestViewState, RecreateRequestAction, RecreateRequestEvent>(
        initialState = RecreateRequestViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val activeRequestsRepositoryForDriver: ActiveRequestsForDriverRepository =
        Inject.instance()

    private val unconfirmedRequestsRepository: UnconfirmedRequestsRepository = Inject.instance()

    private val operationsOnRequestsRepository: OperationsOnRequestsRepository = Inject.instance()

    private val rejectRequestsRepository: RejectRequestsRepository = Inject.instance()

    override fun obtainEvent(viewEvent: RecreateRequestEvent) {
        when (viewEvent) {
            is RecreateRequestEvent.GetInfoForOldUnconfirmedRequest -> getInfoForOldUnconfirmedRequest(
                requestId = viewEvent.requestId
            )

            is RecreateRequestEvent.GetInfoForOldRejectRequest -> getInfoForOldRejectRequest(
                requestId = viewEvent.requestId
            )

            is RecreateRequestEvent.RecreateRequest -> recreateRequest()

            is RecreateRequestEvent.SelectedTypeVehicleTractor -> {
                obtainEvent(RecreateRequestEvent.TractorIsExpandedChanged)
                obtainIsSelectedTractor()
            }

            is RecreateRequestEvent.SelectedTypeVehicleTrailer -> {
                obtainEvent(RecreateRequestEvent.TrailerIsExpandedChanged)
                obtainIsSelectedTrailer()
            }

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

            is RecreateRequestEvent.ShowDatePicker -> obtainShowDatePicker(value = true)

            is RecreateRequestEvent.CloseDatePicker -> obtainShowDatePicker(value = false)

            is RecreateRequestEvent.ArrivalDateChanged -> obtainArrivalDate(
                arrivalDate = convertDateLongToString(
                    date = viewEvent.arrivalDate
                )
            )

            is RecreateRequestEvent.CameraClick -> cameraClicked()

            is RecreateRequestEvent.CameraPermissionDenied -> obtainCameraPermissionIsDenied(value = viewEvent.value)

            is RecreateRequestEvent.OpenAppSettings -> openSettings()
        }
    }


    private fun cameraClicked() {
        viewModelScope.launch {
            try {
                mediaController.permissionsController.providePermission(permission = Permission.CAMERA)
            } catch (e: Exception) {
                log(tag = TAG) { "Camera permission is failure" }
            }

            when (mediaController.permissionsController.getPermissionState(Permission.CAMERA)) {
                PermissionState.NotDetermined -> obtainEvent(
                    viewEvent = RecreateRequestEvent.CameraPermissionDenied(
                        value = true
                    )
                )

                PermissionState.Granted -> {
                    try {
                        obtainEvent(
                            viewEvent = RecreateRequestEvent.CameraPermissionDenied(
                                value = false
                            )
                        )
                        val image = mediaController.pickImage(MediaSource.CAMERA)
                        obtainEvent(
                            viewEvent = RecreateRequestEvent.SetResource(
                                filePath = "${uuid4().mostSignificantBits}.png",
                                isImage = true,
                                imageByteArray = image.toByteArray()
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                PermissionState.Denied -> obtainEvent(
                    viewEvent = RecreateRequestEvent.CameraPermissionDenied(
                        value = true
                    )
                )

                PermissionState.DeniedAlways -> obtainEvent(
                    viewEvent = RecreateRequestEvent.CameraPermissionDenied(
                        value = true
                    )
                )
            }
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
            log(tag = "RESOURCE") { isResource.toString() }
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

            //check choose vehicle
            if (!viewState.isSelectedTractor && !viewState.isSelectedTrailer) {
                obtainNotChooseVehicle(value = true)
            } else {
                obtainNotChooseVehicle(value = false)
            }
            if (viewState.numberVehicle.isNotEmpty() && !viewState.notChooseVehicle) {
                viewState = viewState.copy(notVehicleNumber = false)
                val recreateRequestItem = operationsOnRequestsRepository.recreateRequest(
                    requestId = viewState.requestId,
                    typeVehicle = getVehicleType(
                        isSelectedTractor = viewState.isSelectedTractor,
                        isSelectedTrailer = viewState.isSelectedTrailer
                    ),
                    numberVehicle = viewState.numberVehicle,
                    oldTypeVehicle = getVehicleType(
                        isSelectedTractor = viewState.isSelectedOldTractor,
                        isSelectedTrailer = viewState.isSelectedOldTrailer
                    ),
                    oldNumberVehicle = viewState.oldNumberVehicle,
                    faultDescription = viewState.faultDescription,
                    arrivalDate = viewState.arrivalDate
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
            } else if (viewState.numberVehicle.isEmpty()) {
                viewState = viewState.copy(notVehicleNumber = true)
            }
            obtainIsLoadingChange()
        }
    }

    private fun getInfoForOldRejectRequest(requestId: Int) {
        coroutineScope.launch {
            obtainIsLoadingChange()
            obtainRequestIdChange(requestId = requestId)
            val rejectedRequestInfoItem =
                rejectRequestsRepository.getRejectRequestInfo(requestId = requestId)
            if (rejectedRequestInfoItem is FullRejectRequestItem.Success) {
                log(tag = TAG) { "Get info for old rejected request ${rejectedRequestInfoItem.request}" }
                val info = rejectedRequestInfoItem.request
                val (isTractor, isTrailer) = info.typeVehicle.toVehicleType()
                viewState = viewState.copy(
                    isSelectedOldTractor = isTractor,
                    isSelectedOldTrailer = isTrailer,
                    oldNumberVehicle = info.numberVehicle,
                    faultDescription = info.faultDescription,
                    images = info.images,
                    videos = info.videos,
                    numberVehicle = info.numberVehicle
                )
                if (viewState.isSelectedOldTractor) {
                    obtainIsSelectedTractor()
                    obtainTractorIsExpandedChange()
                }
                if (viewState.isSelectedOldTrailer) {
                    obtainIsSelectedTrailer()
                    obtainTrailerIsExpandedChange()
                }
            } else if (rejectedRequestInfoItem is FullRejectRequestItem.Error) {
                log(tag = TAG) { "get info for rejected request is failure" }
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
                val (isTractor, isTrailer) = info.vehicleType.toVehicleType()
                viewState = viewState.copy(
                    isSelectedOldTractor = isTractor,
                    isSelectedOldTrailer = isTrailer,
                    oldNumberVehicle = info.vehicleNumber,
                    faultDescription = info.faultDescription,
                    images = info.images,
                    videos = info.videos,
                    numberVehicle = info.vehicleNumber,
                    arrivalDate = info.arrivalDate
                )
                if (viewState.isSelectedOldTractor) {
                    obtainIsSelectedTractor()
                    obtainTractorIsExpandedChange()
                }
                if (viewState.isSelectedOldTrailer) {
                    obtainIsSelectedTrailer()
                    obtainTrailerIsExpandedChange()
                }
            } else if (unconfirmedRequestInfoItem is UnconfirmedRequestInfoItem.Error) {
                log(tag = TAG) { "get info for unconfirmed request is failure" }
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

    private fun openSettings() {
        mediaController.permissionsController.openAppSettings()
    }

    private fun obtainCameraPermissionIsDenied(value: Boolean) {
        viewState = viewState.copy(cameraPermissionIsDenied = value)
    }

    private fun clearResourceList() {
        viewState = viewState.copy(resources = emptyList())
    }

    private fun obtainArrivalDate(arrivalDate: String) {
        viewState = viewState.copy(arrivalDate = arrivalDate)
    }

    private fun obtainNotChooseVehicle(value: Boolean) {
        viewState = viewState.copy(notChooseVehicle = value)
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

    private fun obtainIsSelectedTractor() {
        viewState = viewState.copy(isSelectedTractor = !viewState.isSelectedTractor)
    }

    private fun obtainIsSelectedTrailer() {
        viewState = viewState.copy(isSelectedTrailer = !viewState.isSelectedTrailer)
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
        obtainNotChooseVehicle(value = false)
        log(tag = TAG) { "Trailer expanded changed ${viewState.trailerIsExpanded}" }
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

    private fun obtainShowDatePicker(value: Boolean) {
        viewState = viewState.copy(showDatePicker = value)
    }

    private companion object {
        const val TAG = "RecreateRequestViewModel"
    }
}