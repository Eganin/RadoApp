import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.*
import models.*
import other.BaseSharedViewModel
import usecases.ValidatePhone

class AuthViewModel : BaseSharedViewModel<AuthViewState, AuthAction, AuthEvent>(
    initialState = AuthViewState()
) {
    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default +
                CoroutineExceptionHandler { _, throwable ->
                    log(tag=TAG) { throwable.message ?: "Error" }
                })

    private val authRepository: AuthRepository = Inject.instance()
    private val validatePhone = ValidatePhone()

    init {
        checkUserLoggedIn()
    }

    override fun obtainEvent(viewEvent: AuthEvent) {
        when (viewEvent) {
            is AuthEvent.FullNameChanged -> obtainFullNameChanged(fullName = viewEvent.value)
            is AuthEvent.PositionChanged -> obtainPositionChanged(position = viewEvent.value)
            is AuthEvent.PhoneChanged -> obtainPhoneChanged(phone = viewEvent.value)
            is AuthEvent.RegisterClick -> {}
        }
    }

    private fun sendLogin(){
        val phoneIsValid = validatePhone.invoke(phone = viewState.phone)
    }

    private fun checkUserLoggedIn() {
        coroutineScope.launch {
            val loginInfoItem = authRepository.isUserLoggedIn()
            if (loginInfoItem is LoginInfoItem.Success) {
                log(tag=TAG) { "User logged in" }
                viewAction = AuthAction.OpenMainFlow
            }
        }
    }

    private fun obtainFullNameChanged(fullName:String){
        viewState=viewState.copy(
            fullName=fullName
        )
        log(tag=TAG) { "Full name has been changed" }
    }

    private fun obtainPositionChanged(position:Position){
        viewState=viewState.copy(
            position=position
        )
        log(tag=TAG) { "Position has been changed" }
    }

    private fun obtainPhoneChanged(phone:String){
        viewState=viewState.copy(
            phone=phone
        )
        log(tag=TAG) { "Position has been changed" }
    }

    private companion object {
        const val TAG = "AuthViewModel"
    }
}