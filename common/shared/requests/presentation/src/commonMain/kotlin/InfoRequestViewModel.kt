import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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

    private val unconfirmedRequestsRepository: UnconfirmedRequestsRepository =
        Inject.instance()

    private val activeRequestsRepository: ActiveRequestsRepository = Inject.instance()

    override fun obtainEvent(viewEvent: InfoRequestEvent) {
        when (viewEvent) {
            is InfoRequestEvent.ImageRepairExpandedChanged -> obtainImageRepairExpandedChanged()
            is InfoRequestEvent.RequestGetInfo -> getInfoForRequest(
                requestId = viewEvent.requestId,
                infoForPosition = viewEvent.infoForPosition,
                isActiveRequest = viewEvent.isActiveRequest
            )

            is InfoRequestEvent.PhoneClick -> {}
        }
    }

    private fun getInfoForRequest(
        requestId: Int,
        infoForPosition: Position,
        isActiveRequest: Boolean
    ) {
        when {
            infoForPosition == Position.DRIVER && isActiveRequest -> getInfoForActiveRequest(
                requestId = requestId
            )

            infoForPosition == Position.DRIVER && !isActiveRequest -> getInfoForUnconfirmedRequest(
                requestId = requestId
            )

            infoForPosition == Position.MECHANIC -> getInfoForUnconfirmedRequest(requestId = requestId)
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
                viewState = viewState.copy(
                    selectedVehicleType = info.vehicleType.toVehicleType(),
                    numberVehicle = info.vehicleNumber,
                    faultDescription = info.faultDescription,
                    images = info.images,
                    videos=info.videos,
                    driverPhone = info.driverPhone
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

    private fun getInfoForActiveRequest(requestId: Int) {
        coroutineScope.launch {
            changeLoading()
            val fullRequestItem =
                activeRequestsRepository.getActiveRequestInfo(requestId = requestId)
            if (fullRequestItem is FullRequestItem.Success) {
                log(tag = TAG) { "Get info for active request ${fullRequestItem.request}" }
                val info = fullRequestItem.request
                log(tag = "IMAGES") { info.images.toString() }
                viewState = viewState.copy(
                    numberVehicle = info.vehicleNumber,
                    selectedVehicleType = info.vehicleType.toVehicleType(),
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
                    videos = info.videos
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