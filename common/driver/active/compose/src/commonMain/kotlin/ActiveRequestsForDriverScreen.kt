import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.DriverActiveAction
import other.observeAsState
import pullRefresh.PullRefreshIndicator
import pullRefresh.pullRefresh
import pullRefresh.rememberPullRefreshState
import theme.Theme
import views.ActiveRequestsForDriverView

object ActiveRequestsForDriverScreen : Screen {

    private val viewModel = viewModelFactory { DriverActiveViewModel() }.createViewModel()

    @Composable
    override fun Content() {
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()

        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }

        var isRefreshing by remember {
            mutableStateOf(false)
        }
        var pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
            scope.launch {
                isRefreshing = true
                delay(3_000)
                isRefreshing = false
            }
        })

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            modifier = Modifier.pullRefresh(pullRefreshState)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "Pull down to refresh")
                ActiveRequestsForDriverView(
                    state = state.value,
                    modifier = Modifier.padding(bottom = 90.dp)
                ) { event ->
                    viewModel.obtainEvent(viewEvent = event)
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = Theme.colors.highlightColor,
                    contentColor = Theme.colors.primaryAction,
                )
            }
        }

        when (action.value) {
            is DriverActiveAction.ShowErrorSnackBar -> {
                val snackBarAction = action.value as DriverActiveAction.ShowErrorSnackBar
                scope.launch {
                    snackBarHostState.showSnackbar(message = snackBarAction.message)
                }
            }

            null -> {}
        }
    }
}