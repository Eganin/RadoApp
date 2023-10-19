import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.CreateRequestAction
import models.CreateRequestEvent
import models.CreateRequestIdItem
import models.CreateRequestViewState
import models.VehicleType
import org.company.rado.core.MainRes
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
        }
    }

    private fun createRequest() {
        coroutineScope.launch {
            if (viewState.numberVehicle.isNotEmpty()) {
                val createRequestIdItem = activeRequestsRepository.createRequest(
                    typeVehicle = viewState.selectedVehicleType.nameVehicleType,
                    numberVehicle = viewState.numberVehicle,
                    faultDescription = viewState.faultDescription
                )
                if (createRequestIdItem is CreateRequestIdItem.Success) {
                    log(tag = TAG) { "Create request is success" }
                    viewState =
                        viewState.copy(showSuccessCreateRequestDialog = !viewState.showSuccessCreateRequestDialog)
                } else if (createRequestIdItem is CreateRequestIdItem.Error) {
                    log(tag = TAG) { "Create request is failure" }
                    viewAction =
                        CreateRequestAction.ShowErrorSnackBar(message = createRequestIdItem.message)
                }
            } else {
                viewAction =
                    CreateRequestAction.ShowErrorSnackBar(message = MainRes.string.number_vehicle_is_empty)
            }
        }
    }

    private fun obtainSelectedTypeVehicleChange(typeVehicle: VehicleType) {
        viewState = viewState.copy(
            selectedVehicleType = typeVehicle
        )
        log(tag = TAG) { "Type vehicle is changed" }
    }

    private fun obtainNumberVehicleChange(numberVehicle: String) {
        viewState = viewState.copy(
            numberVehicle = numberVehicle
        )
        log(tag = TAG) { "Number vehicle is changed" }
    }

    private fun obtainFaultDescriptionChange(faultDescription: String) {
        viewState = viewState.copy(
            faultDescription = faultDescription
        )
        log(tag = TAG) { "Fault description is changed" }
    }

    private companion object {
        const val TAG = "CreateRequestViewModel"
    }
}