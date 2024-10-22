import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.FullRejectRequestItem
import models.FullRequestItem
import models.UnconfirmedRequestInfoItem
import models.create.toVehicleType
import models.info.InfoRequestAction
import models.info.InfoRequestEvent
import models.info.InfoRequestViewState
import other.BaseSharedViewModel
import other.Position

class InfoRequestViewModel :
    BaseSharedViewModel<InfoRequestViewState, InfoRequestAction, InfoRequestEvent>(
        initialState = InfoRequestViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val unconfirmedRequestsRepository: UnconfirmedRequestsRepository = Inject.instance()

    private val activeRequestsRepository: ActiveRequestsRepository = Inject.instance()

    private val archiveRequestsRepository: ArchiveRequestsRepository = Inject.instance()

    private val rejectRequestsRepository: RejectRequestsRepository = Inject.instance()

    override fun obtainEvent(viewEvent: InfoRequestEvent) {
        when (viewEvent) {
            is InfoRequestEvent.ImageRepairExpandedChanged -> obtainImageRepairExpandedChanged()
            is InfoRequestEvent.RequestGetInfo -> getInfoForRequest(
                requestId = viewEvent.requestId,
                infoForPosition = viewEvent.infoForPosition,
                isActiveRequest = viewEvent.isActiveRequest,
                isArchiveRequest = viewEvent.isArchiveRequest,
                isRejectRequest = viewEvent.isRejectRequest
            )

            is InfoRequestEvent.PhoneClick -> {}
        }
    }

    private fun getInfoForRequest(
        requestId: Int,
        infoForPosition: Position,
        isActiveRequest: Boolean,
        isArchiveRequest: Boolean,
        isRejectRequest: Boolean
    ) {
        when {
            isRejectRequest -> getInfoForRejectedRequest(requestId=requestId)

            !isActiveRequest && !isArchiveRequest && !isRejectRequest -> getInfoForUnconfirmedRequest(
                requestId = requestId
            )

            else -> {
                getInfoForActiveOrArchiveRequest(
                    requestId = requestId,
                    isArchive = isArchiveRequest
                )
            }
        }
    }

    private fun getInfoForRejectedRequest(requestId: Int) {
        coroutineScope.launch {
            changeLoading()

            val rejectRequestItem =
                rejectRequestsRepository.getRejectRequestInfo(requestId = requestId)

            if (rejectRequestItem is FullRejectRequestItem.Success) {
                val request = rejectRequestItem.request
                val (isTractor, isTrailer) = request.typeVehicle.toVehicleType()
                viewState = viewState.copy(
                    isSelectedTractor = isTractor,
                    isSelectedTrailer = isTrailer,
                    numberVehicle = request.numberVehicle,
                    faultDescription = request.faultDescription,
                    images = request.images,
                    videos = request.videos,
                    commentMechanic = request.commentMechanic
                )
            } else if (rejectRequestItem is FullRejectRequestItem.Error) {
                viewState = viewState.copy(
                    errorTitleMessage = rejectRequestItem.message
                )
            }

            changeLoading()
        }
    }

    private fun getInfoForUnconfirmedRequest(requestId: Int) {
        coroutineScope.launch {
            changeLoading()
            val unconfirmedRequestInfoItem =
                unconfirmedRequestsRepository.getInfoForUnconfirmedRequest(requestId = requestId)
            if (unconfirmedRequestInfoItem is UnconfirmedRequestInfoItem.Success) {
                log(tag = TAG) { "Get info for unconfirmed request ${unconfirmedRequestInfoItem.requestInfo}" }
                val info = unconfirmedRequestInfoItem.requestInfo
                val (isTractor, isTrailer) = info.vehicleType.toVehicleType()
                viewState = viewState.copy(
                    isSelectedTractor = isTractor,
                    isSelectedTrailer = isTrailer,
                    numberVehicle = info.vehicleNumber,
                    faultDescription = info.faultDescription,
                    images = info.images,
                    videos = info.videos,
                    driverPhone = info.driverPhone,
                    arrivalDate = info.arrivalDate
                )
            } else if (unconfirmedRequestInfoItem is UnconfirmedRequestInfoItem.Error) {
                log(tag = TAG) { "Failure get info for unconfirmed request" }
                viewState = viewState.copy(
                    errorTitleMessage = unconfirmedRequestInfoItem.message
                )
            }
            changeLoading()
        }
    }

    private fun getInfoForActiveOrArchiveRequest(
        requestId: Int,
        isArchive: Boolean
    ) {
        coroutineScope.launch {
            changeLoading()
            val fullRequestItem =
                if (isArchive) archiveRequestsRepository.getArchiveRequestInfo(requestId = requestId)
                else activeRequestsRepository.getActiveRequestInfo(requestId = requestId)
            if (fullRequestItem is FullRequestItem.Success) {
                log(tag = TAG) { "Get info for request ${fullRequestItem.request}" }
                val info = fullRequestItem.request
                val (isTractor, isTrailer) = info.vehicleType.toVehicleType()
                log(tag = "IMAGES") { info.images.toString() }
                log(tag = "VIDEOS") { info.videos.toString() }
                viewState = viewState.copy(
                    numberVehicle = info.vehicleNumber,
                    isSelectedTractor = isTractor,
                    isSelectedTrailer = isTrailer,
                    driverName = info.driverName,
                    driverPhone = info.driverPhone,
                    statusRequest = info.statusRequest,
                    faultDescription = info.faultDescription,
                    mechanicName = info.mechanicName,
                    mechanicPhone = info.mechanicPhone,
                    datetime = info.date + ";" + info.time,
                    statusRepair = info.statusRepair,
                    commentMechanic = info.commentMechanic ?: "",
                    images = info.images,
                    videos = info.videos,
                    arrivalDate = info.arrivalDate,
                    streetRepair = info.streetRepair
                )
            } else if (fullRequestItem is FullRequestItem.Error) {
                log(tag = TAG) { "Failure get info for active request" }
                viewState = viewState.copy(
                    errorTitleMessage = fullRequestItem.message
                )
            }
            changeLoading()
        }
    }

    private fun changeLoading() {
        viewState = viewState.copy(isLoading = !viewState.isLoading)
    }

    private fun obtainImageRepairExpandedChanged() {
        viewState = viewState.copy(
            imageIsExpanded = !viewState.imageIsExpanded
        )
    }

    private companion object {
        const val TAG = "InfoRequestViewModel"
    }
}