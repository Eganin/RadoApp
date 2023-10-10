import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import models.AuthEvent
import models.AuthViewState
import theme.Theme

@Composable
fun AuthView(
    state: AuthViewState,
    modifier: Modifier = Modifier,
    eventHandler: (AuthEvent) -> Unit
) {
    Column(modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground).padding(all = 16.dp)) {
        Text(text = "Выберите вашу должность", fontSize = 16.sp, color = Theme.colors.primaryTextColor)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier=Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = state.exposedMenuValue,
                onValueChange = {},
                label = { Text(text = "Должность", color = Theme.colors.primaryTextColor) },
                trailingIcon = {
                    Icon(
                        imageVector = if (state.exposedMenuIsEnabled) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            eventHandler.invoke(AuthEvent.ExposedMenuEnableChanged(value = !state.exposedMenuIsEnabled))
                        }
                    )
                },
                modifier = Modifier.onGloballyPositioned {
                    eventHandler.invoke(AuthEvent.ExposedMenuSizeChanged(value = it.size.toSize()))
                }
            )
            DropdownMenu(
                modifier = Modifier.width(with(LocalDensity.current) { state.exposedMenuSize.width.toDp() }),
                expanded = state.exposedMenuIsEnabled,
                onDismissRequest = { eventHandler.invoke(AuthEvent.ExposedMenuEnableChanged(value = false)) }) {
                state.itemsExposedMenu.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(text = s, color = Theme.colors.primaryTextColor) },
                        onClick = {
                            eventHandler.invoke(AuthEvent.ExposedMenuIndexChanged(value = index))
                            eventHandler.invoke(AuthEvent.ExposedMenuEnableChanged(value = false))
                        })
                }
            }
        }
    }
}