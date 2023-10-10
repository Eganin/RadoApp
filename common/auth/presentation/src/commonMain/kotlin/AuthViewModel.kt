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
            is AuthEvent.FullNameChanged -> obtainFullNameChanged(fullName = viewEvent.value)
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
        val fullNameIsValid = validateFullName.invoke(fullName = viewState.fullName)
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
                    val userIdItem = authRepository.register(
                        position = viewState.position.positionName,
                        fullName = viewState.fullName,
                        phone = viewState.phone
                    )
                    if (userIdItem is UserIdItem.Success) {
                        log(tag = TAG) { "User logged in" }
                        viewAction = AuthAction.OpenMainFlow
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
            viewState.copy(exposedMenuSelectedIndex = index, exposedMenuValue = viewState.itemsExposedMenu[index])
    }

    private fun obtainExposedMenuSizeChange(size: Size) {
        viewState = viewState.copy(
            exposedMenuSize = size
        )
    }

    private fun obtainExposedMenuIsEnabledChange(enabled: Boolean) {
        viewState = viewState.copy(
            exposedMenuIsEnabled = enabled
        )
    }

    private fun checkUserLoggedIn() {
        coroutineScope.launch {
            val loginInfoItem = authRepository.isUserLoggedIn()
            if (loginInfoItem is LoginInfoItem.Success) {
                log(tag = TAG) { "User logged in" }
                viewAction = AuthAction.OpenMainFlow
            }
        }
    }

    private fun obtainFullNameChanged(fullName: String) {
        viewState = viewState.copy(
            fullName = fullName
        )
        log(tag = TAG) { "Full name has been changed" }
    }

    private fun obtainPositionChanged(position: Position) {
        viewState = viewState.copy(
            position = position
        )
        log(tag = TAG) { "Position has been changed" }
    }

    private fun obtainPhoneChanged(phone: String) {
        viewState = viewState.copy(
            phone = phone
        )
        log(tag = TAG) { "Position has been changed" }
    }

    private companion object {
        const val TAG = "AuthViewModel"
    }
}