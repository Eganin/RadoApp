package usecases

class ValidateFullName {

    operator fun invoke(fullName: String): ValidationResult {
        val pathFullName = fullName.split("").count()
        return if (pathFullName == 3) {
            ValidationResult(successful = true)
        } else {
            ValidationResult(successful = false, errorMessage = "Полное имя указано неправильно")
        }
    }
}