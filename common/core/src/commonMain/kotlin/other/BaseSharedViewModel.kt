package other

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseSharedViewModel<State : Any, Action, Event>(initialState: State) : ViewModel() {
    private val _viewStates: MutableStateFlow<State> = MutableStateFlow(initialState)

    protected var viewState: State
        get() = _viewStates.value
        set(value) {
            _viewStates.value = value
        }

    fun viewStates(): WrappedStateFlow<State> = WrappedStateFlow(_viewStates.asStateFlow())

    private val _viewActions: MutableSharedFlow<Action> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    protected var viewAction: Action?
        get() = _viewActions.replayCache.last()
        set(value) {
            if (value != null) _viewActions.tryEmit(value)
        }

    fun viewActions(): WrappedSharedFlow<Action?> = WrappedSharedFlow(_viewActions.asSharedFlow())
}