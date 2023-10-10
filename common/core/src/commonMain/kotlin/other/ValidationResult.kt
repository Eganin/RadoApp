package other

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String?=null
)
