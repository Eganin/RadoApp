package models

sealed class CreateRequestEvent {

    data object CreateRequest : CreateRequestEvent()

    data class SelectedTypeVehicleChanged(val value: VehicleType) : CreateRequestEvent()

    data class NumberVehicleChanged(val value: String) : CreateRequestEvent()

    data class FaultDescriptionChanged(val value: String) : CreateRequestEvent()

    data object TrailerIsExpandedChanged : CreateRequestEvent()

    data object TractorIsExpandedChanged : CreateRequestEvent()

    data object CloseSuccessDialog : CreateRequestEvent()

    data object CloseFailureDialog : CreateRequestEvent()

    data object FilePickerVisibilityChanged : CreateRequestEvent()

    data class SetImage(val filePath: String, val imageByteArray: ByteArray) : CreateRequestEvent()
}