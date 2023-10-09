package usecases

class ValidatePhone {

    operator fun invoke(phone: String): ValidationResult {
        val regex = """^[+][0-9]{10,13}${'$'}""".toRegex()
        val isPhone = regex.containsMatchIn(input = phone)
        return if (isPhone) {
            ValidationResult(successful = true)
        } else {
            ValidationResult(successful = false, errorMessage = "Номер телефона введен неправильно")
        }
    }
}