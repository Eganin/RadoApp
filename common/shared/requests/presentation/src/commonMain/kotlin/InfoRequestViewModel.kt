import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.UnconfirmedRequestInfoItem
import models.create.toVehicleType
import models.info.InfoRequestAction
import models.info.InfoRequestEvent
import models.info.InfoRequestViewState
import other.BaseSharedViewModel

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

    override fun obtainEvent(viewEvent: InfoRequestEvent) {
        when (viewEvent) {
            is InfoRequestEvent.UnconfirmedRequestGetInfo -> getInfoForUnconfirmedRequest(requestId = viewEvent.value)
            is InfoRequestEvent.PhoneClick -> {}
        }
    }

    private fun getInfoForUnconfirmedRequest(requestId: Int) {
        coroutineScope.launch {
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
                    driverPhone = info.driverPhone
                )
            } else if (unconfirmedRequestInfoItem is UnconfirmedRequestInfoItem.Error) {
                log(tag = TAG) { "Failure get info for unconfirmed request" }
                viewState = viewState.copy(
                    errorTitleMessage = unconfirmedRequestInfoItem.message
                )
            }
        }
    }

    private companion object {
        const val TAG = "InfoRequestViewModel"
    }
}