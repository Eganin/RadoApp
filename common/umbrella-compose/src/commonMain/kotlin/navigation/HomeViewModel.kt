package navigation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*

class HomeViewModel: ViewModel() {
    fun test() {
        CoroutineScope(context = Dispatchers.Default).launch {
            Napier.d("VIEWMODEL", tag = "HomeViewModel")
            delay(1000L)
            Napier.d("VIEWMODEL", tag = "HomeViewModel")
            delay(1000L)
            Napier.d("VIEWMODEL", tag = "HomeViewModel")
            delay(1000L)
            Napier.d("VIEWMODEL", tag = "HomeViewModel")
            delay(1000L)
            Napier.d("VIEWMODEL", tag = "HomeViewModel")
        }
    }
}