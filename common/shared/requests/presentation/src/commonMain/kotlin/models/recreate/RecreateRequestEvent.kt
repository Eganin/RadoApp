package models.recreate

import models.create.VehicleType

sealed class RecreateRequestEvent {

    data class GetInfoForOldRequest(val requestId: Int) : RecreateRequestEvent()
    data object RecreateRequest : RecreateRequestEvent()

    data class SelectedTypeVehicleChanged(val value: VehicleType) : RecreateRequestEvent()

    data class NumberVehicleChanged(val value: String) : RecreateRequestEvent()

    data class FaultDescriptionChanged(val value: String) : RecreateRequestEvent()

    data object TrailerIsExpandedChanged : RecreateRequestEvent()

    data object TractorIsExpandedChanged : RecreateRequestEvent()

    data object ImageRepairExpandedChanged : RecreateRequestEvent()

    data object CloseSuccessDialog : RecreateRequestEvent()

    data object CloseFailureDialog : RecreateRequestEvent()

    data object FilePickerVisibilityChanged : RecreateRequestEvent()

    data class SetResource(
        val filePath: String,
        val isImage: Boolean,
        val imageByteArray: ByteArray
    ) : RecreateRequestEvent()

    data object OnBackClick : RecreateRequestEvent()

    data object DeleteRequest : RecreateRequestEvent()
}