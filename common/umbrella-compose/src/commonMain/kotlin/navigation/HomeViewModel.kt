package navigation

import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {
    private val _state : CMutableStateFlow<String> = CMutableStateFlow(flow = MutableStateFlow(""))
    val state : CStateFlow<String> = CStateFlow(flow = _state.asStateFlow())
    fun test() {
        CoroutineScope(context = Dispatchers.Default).launch {
            _state.value="VIEWMODEL1"
            Napier.d(state.value, tag = "HomeViewModel")
            delay(1000L)
            _state.value="VIEWMODEL2"
            Napier.d(state.value, tag = "HomeViewModel")
        }
    }
}