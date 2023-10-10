package usecases

import org.company.rado.core.MainRes
import other.ValidationResult

class ValidateFullName {

    operator fun invoke(firstname:String,secondName:String,thirdName:String) : ValidationResult {
        return when{
            firstname.isEmpty()-> ValidationResult(successful = false, errorMessage = MainRes.string.name_error)
            secondName.isEmpty()->ValidationResult(successful = false, errorMessage = MainRes.string.family_error)
            thirdName.isEmpty()->ValidationResult(successful = false, errorMessage = MainRes.string.patronymic_error)
            else->ValidationResult(successful = true)
        }
    }
}