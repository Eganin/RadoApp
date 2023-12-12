package models.recreate

data class RecreateRequestViewState(
    val isSelectedTractor: Boolean = false,
    val isSelectedTrailer: Boolean = false,
    val isSelectedOldTractor: Boolean = false,
    val isSelectedOldTrailer: Boolean = false,
    val numberVehicle: String = "",
    val notVehicleNumber: Boolean = false,
    val oldNumberVehicle: String = "",
    val faultDescription: String = "",
    val showSuccessDialog: Boolean = false,
    val showFailureDialog: Boolean = false,
    val showDatePicker:Boolean=false,
    val trailerIsExpanded: Boolean = false,
    val tractorIsExpanded: Boolean = false,
    val imageIsExpanded: Boolean = false,
    val showFilePicker: Boolean = false,
    val requestId: Int = -1,
    val isLoading: Boolean = false,
    val resources: List<Triple<String, Boolean, ByteArray>> = emptyList(),
    val images: List<String> = emptyList(),
    val videos: List<String> = emptyList(),
    val notChooseVehicle:Boolean=false,
    val arrivalDate:String="",
    val cameraPermissionIsDenied: Boolean=false
)