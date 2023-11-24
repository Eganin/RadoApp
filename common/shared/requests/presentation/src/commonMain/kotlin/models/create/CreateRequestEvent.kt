package models.create

sealed class CreateRequestEvent {

    data object CreateRequest : CreateRequestEvent()

    data object SelectedTypeVehicleTractor : CreateRequestEvent()
    data object SelectedTypeVehicleTrailer : CreateRequestEvent()

    data class NumberVehicleChanged(val value: String) : CreateRequestEvent()

    data class FaultDescriptionChanged(val value: String) : CreateRequestEvent()

    data object TrailerIsExpandedChanged : CreateRequestEvent()

    data object TractorIsExpandedChanged : CreateRequestEvent()

    data object ImageRepairExpandedChanged : CreateRequestEvent()

    data object CloseSuccessDialog : CreateRequestEvent()

    data object CloseFailureDialog : CreateRequestEvent()

    data object FilePickerVisibilityChanged : CreateRequestEvent()

    data class SetResource(
        val filePath: String,
        val isImage: Boolean,
        val imageByteArray: ByteArray
    ) : CreateRequestEvent()

    data object OnBackClick : CreateRequestEvent()
}