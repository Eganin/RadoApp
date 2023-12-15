package models.recreate

sealed class RecreateRequestEvent {

    data class GetInfoForOldUnconfirmedRequest(val requestId: Int) : RecreateRequestEvent()

    data class GetInfoForOldRejectRequest(val requestId: Int) : RecreateRequestEvent()

    data object RecreateRequest : RecreateRequestEvent()

    data object SelectedTypeVehicleTractor : RecreateRequestEvent()
    data object SelectedTypeVehicleTrailer : RecreateRequestEvent()

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

    data class RemoveImage(val imagePath: String) : RecreateRequestEvent()

    data class RemoveVideo(val videoPath: String) : RecreateRequestEvent()

    data class RemoveImageFromResource(val imagePath: String) : RecreateRequestEvent()

    data class RemoveVideoFromResource(val videoPath: String) : RecreateRequestEvent()

    data class ArrivalDateChanged(val arrivalDate: Long) : RecreateRequestEvent()

    data object ShowDatePicker : RecreateRequestEvent()

    data object CloseDatePicker : RecreateRequestEvent()

    data class CameraPermissionDenied(val value: Boolean) : RecreateRequestEvent()

    data object OpenAppSettings : RecreateRequestEvent()

    data object CameraClick : RecreateRequestEvent()

    data object VideoClick : RecreateRequestEvent()
}