import models.recreate.RecreateRequestAction
import models.recreate.RecreateRequestEvent
import models.recreate.RecreateRequestViewState
import other.BaseSharedViewModel

class RecreateRequestViewModel :
    BaseSharedViewModel<RecreateRequestViewState, RecreateRequestAction, RecreateRequestEvent>(
        initialState = RecreateRequestViewState()
    ) {
    override fun obtainEvent(viewEvent: RecreateRequestEvent) {
        when (viewEvent) {
            is RecreateRequestEvent.GetInfoForOldRequest -> {}
            is RecreateRequestEvent.RecreateRequest -> {}
            is RecreateRequestEvent.SelectedTypeVehicleChanged -> {}
            is RecreateRequestEvent.NumberVehicleChanged -> {}
            is RecreateRequestEvent.FaultDescriptionChanged -> {}
            is RecreateRequestEvent.TrailerIsExpandedChanged -> {}
            is RecreateRequestEvent.TractorIsExpandedChanged -> {}
            is RecreateRequestEvent.ImageRepairExpandedChanged -> {}
            is RecreateRequestEvent.CloseSuccessDialog -> {}
            is RecreateRequestEvent.CloseFailureDialog -> {}
            is RecreateRequestEvent.FilePickerVisibilityChanged -> {}
            is RecreateRequestEvent.SetResource -> {}
            is RecreateRequestEvent.OnBackClick -> {}
            is RecreateRequestEvent.DeleteRequest -> {}
        }
    }
}