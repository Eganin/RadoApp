import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import models.AuthEvent
import models.AuthViewState
import org.company.rado.core.MainRes
import theme.Theme

@Composable
fun AuthView(
    state: AuthViewState,
    modifier: Modifier = Modifier,
    eventHandler: (AuthEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground).padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = MainRes.string.sign_in_title,
            fontSize = 32.sp,
            color = Theme.colors.primaryTextColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(64.dp))

        Text(text = MainRes.string.choose_position, fontSize = 16.sp, color = Theme.colors.primaryTextColor)

        Spacer(modifier = Modifier.height(8.dp))
        //Dropdown menu
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = state.exposedMenuValue,
                onValueChange = {},
                label = { Text(text = MainRes.string.position_title, color = Theme.colors.primaryTextColor) },
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
                state.itemsExposedMenu.forEachIndexed { index, position ->
                    DropdownMenuItem(
                        text = { Text(text = position.positionName, color = Theme.colors.primaryTextColor) },
                        onClick = {
                            eventHandler.invoke(AuthEvent.ExposedMenuIndexChanged(value = index))
                            eventHandler.invoke(AuthEvent.ExposedMenuEnableChanged(value = false))
                        })
                }
            }
        }

        // input texts region
        val textFieldsForFullName by mutableStateOf(
            listOf(
                Pair(first = state.firstName, second = MainRes.string.name_title),
                Pair(first = state.secondName, second = MainRes.string.family_title),
                Pair(first = state.thirdName, second = MainRes.string.patronymic_title)
            )
        )

        textFieldsForFullName.forEach { (value, title) ->
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = value,
                onValueChange = {
                    if (title == MainRes.string.name_title) {
                        eventHandler.invoke(AuthEvent.FirstNameChanged(value = it))
                    } else if (title == MainRes.string.family_title) {
                        eventHandler.invoke(AuthEvent.SecondNameChanged(value = it))
                    } else {
                        eventHandler.invoke(AuthEvent.ThirdNameChanged(value = it))
                    }
                },
                label = { Text(text = title, color = Theme.colors.primaryTextColor) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.phone,
            onValueChange = { eventHandler.invoke(AuthEvent.PhoneChanged(value = it)) },
            label = { Text(text = MainRes.string.phone_title, color = Theme.colors.primaryTextColor) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        //register/login button
        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { eventHandler.invoke(AuthEvent.RegisterClick) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size = 16.dp)
        ) {
            Text(text = MainRes.string.sign_in_button_title, fontSize = 24.sp, color = Theme.colors.secondaryTextColor)
        }
    }
}