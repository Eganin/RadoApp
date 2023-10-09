package other

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*

interface Closeable {
    fun close()
}

class WrappedStateFlow<T : Any>(private val origin: StateFlow<T>) : StateFlow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable = watchFlow(block)
}

class WrappedSharedFlow<T : Any?>(private val origin: SharedFlow<T>) : SharedFlow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable = watchFlow(block)
}

private fun <T> Flow<T>.watchFlow(block: (T) -> Unit): Closeable {
    val context = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    onEach(block).launchIn(context)

    return object : Closeable {
        override fun close() {
            context.cancel()
        }
    }
}