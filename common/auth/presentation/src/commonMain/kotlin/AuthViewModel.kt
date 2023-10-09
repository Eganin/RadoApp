import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import models.AuthAction
import models.AuthEvent
import models.AuthViewState

class AuthViewModel: ViewModel() {
    private val _viewState : CMutableStateFlow<AuthViewState> = CMutableStateFlow(flow = MutableStateFlow(AuthViewState()))
    val viewState : CStateFlow<AuthViewState> = CStateFlow(flow = _viewState.asStateFlow())

    private val _actions : MutableSharedFlow<AuthAction> = MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val actions : SharedFlow<AuthAction> = _actions.asSharedFlow()

    fun obtainEvent(event: AuthEvent){
        when(event){
            is AuthEvent.FullNameChanged ->{}
            is AuthEvent.PositionChanged ->{}
            is AuthEvent.PhoneChanged ->{}
            is AuthEvent.RegisterClick ->{}
        }
    }
}