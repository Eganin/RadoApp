import androidx.compose.ui.geometry.Size
import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.*
import models.*
import other.BaseSharedViewModel
import usecases.ValidateFullName
import usecases.ValidatePhone

class AuthViewModel : BaseSharedViewModel<AuthViewState, AuthAction, AuthEvent>(
    initialState = AuthViewState()
) {
    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default +
                CoroutineExceptionHandler { _, throwable ->
                    log(tag = TAG) { throwable.message ?: "Error" }
                })

    private val authRepository: AuthRepository = Inject.instance()
    private val validatePhone = ValidatePhone()
    private val validateFullName = ValidateFullName()

    init {
        checkUserLoggedIn()
    }

    override fun obtainEvent(viewEvent: AuthEvent) {
        when (viewEvent) {
            is AuthEvent.FirstNameChanged -> obtainFirstNameChanged(firstName = viewEvent.value)
            is AuthEvent.SecondNameChanged -> obtainSecondNameChanged(secondName = viewEvent.value)
            is AuthEvent.ThirdNameChanged -> obtainThirdNameChanged(thirdName = viewEvent.value)
            is AuthEvent.PositionChanged -> obtainPositionChanged(position = viewEvent.value)
            is AuthEvent.PhoneChanged -> obtainPhoneChanged(phone = viewEvent.value)
            is AuthEvent.RegisterClick -> sendLogin()
            is AuthEvent.ExposedMenuEnableChanged -> obtainExposedMenuIsEnabledChange(enabled = viewEvent.value)
            is AuthEvent.ExposedMenuSizeChanged -> obtainExposedMenuSizeChange(size = viewEvent.value)
            is AuthEvent.ExposedMenuIndexChanged -> obtainExposedMenuIndexChange(index = viewEvent.value)
        }
    }

    private fun sendLogin() {
        val phoneIsValid = validatePhone.invoke(phone = viewState.phone)
        val fullNameIsValid = validateFullName.invoke(
            firstname = viewState.firstName,
            secondName = viewState.secondName,
            thirdName = viewState.thirdName
        )
        when {
            !phoneIsValid.successful -> {
                log(tag = TAG) { "Phone is not valid" }
                viewAction = AuthAction.ShowErrorSnackBar(message = phoneIsValid.errorMessage ?: "Произошла ошибка")
            }

            !fullNameIsValid.successful -> {
                log(tag = TAG) { "FullName is not valid" }
                viewAction = AuthAction.ShowErrorSnackBar(message = fullNameIsValid.errorMessage ?: "Произошла ошибка")
            }

            phoneIsValid.successful && fullNameIsValid.successful -> {
                coroutineScope.launch {
                    log(tag = TAG) { viewState.position.positionName }
                    log(tag = TAG) { viewState.firstName + " " + viewState.secondName + " " + viewState.thirdName }
                    log(tag = TAG) { viewState.phone }
                    val userIdItem = authRepository.register(
                        position = viewState.position.positionName,
                        fullName = viewState.firstName + " " + viewState.secondName + " " + viewState.thirdName,
                        phone = viewState.phone
                    )
                    if (userIdItem is UserIdItem.Success) {
                        log(tag = TAG) { "User logged in" }
                        viewAction = AuthAction.OpenMainFlow(position = viewState.position)
                    } else if (userIdItem is UserIdItem.Error) {
                        log(tag = TAG) { "User not logged in" }
                        viewAction = AuthAction.ShowErrorSnackBar(message = userIdItem.message)
                    }
                }
            }
        }
    }

    private fun obtainExposedMenuIndexChange(index: Int) {
        viewState =
            viewState.copy(
                exposedMenuSelectedIndex = index,
                exposedMenuValue = viewState.itemsExposedMenu[index].positionName,
                position = viewState.itemsExposedMenu[index]
            )
        log(tag = TAG) { "Changed position in exposed menu" }
    }

    private fun obtainExposedMenuSizeChange(size: Size) {
        viewState = viewState.copy(
            exposedMenuSize = size
        )
        log(tag = TAG) { "Changed size exposed menu for position" }
    }

    private fun obtainExposedMenuIsEnabledChange(enabled: Boolean) {
        viewState = viewState.copy(
            exposedMenuIsEnabled = enabled
        )
        log(tag = TAG) { "Changed visibility exposed menu for position" }
    }

    private fun checkUserLoggedIn() {
        coroutineScope.launch {
            log(tag = TAG) { "Check User logged in" }
            val loginInfoItem = authRepository.isUserLoggedIn()
            if (loginInfoItem is LoginInfoItem.Success) {
                log(tag = TAG) { "User logged in" }
                viewState = viewState.copy(position = loginInfoItem.position.fromPositionNameToPosition())
                viewAction = AuthAction.OpenMainFlow(position = viewState.position)
            }
        }
    }

    private fun obtainFirstNameChanged(firstName: String) {
        viewState = viewState.copy(
            firstName = firstName.trim()
        )
        log(tag = TAG) { "First name has been changed" }
    }

    private fun obtainSecondNameChanged(secondName: String) {
        viewState = viewState.copy(
            secondName = secondName.trim()
        )
        log(tag = TAG) { "Second name has been changed" }
    }

    private fun obtainThirdNameChanged(thirdName: String) {
        viewState = viewState.copy(
            thirdName = thirdName.trim()
        )
        log(tag = TAG) { "Third name has been changed" }
    }

    private fun obtainPositionChanged(position: Position) {
        viewState = viewState.copy(
            position = position
        )
        log(tag = TAG) { "Position has been changed" }
    }

    private fun obtainPhoneChanged(phone: String) {
        viewState = viewState.copy(
            phone = phone.trim()
        )
        log(tag = TAG) { "Position has been changed" }
    }

    private companion object {
        const val TAG = "AuthViewModel"
    }
}