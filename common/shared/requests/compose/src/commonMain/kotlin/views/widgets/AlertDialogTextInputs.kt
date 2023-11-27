package views.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertCircle
import org.company.rado.core.MainRes
import theme.Theme
import widgets.common.ActionButton

@Composable
internal fun AlertDialogTextInputs(
    numberVehicle: String,
    notVehicleNumber: Boolean,
    faultDescription: String,
    isLargePlatform: Boolean,
    arrivalDate: String,
    numberVehicleOnChange: (String) -> Unit,
    faultDescriptionOnChange: (String) -> Unit,
    arrivalDateOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Theme.colors.primaryTextColor,
            unfocusedTextColor = Theme.colors.primaryTextColor,
            disabledTextColor = Theme.colors.primaryTextColor,
        ),
        value = numberVehicle,
        isError = notVehicleNumber,
        supportingText = {
            if (notVehicleNumber) Text(
                text = MainRes.string.number_vehicle_error_message,
                color = Theme.colors.errorColor
            )
        },
        trailingIcon = {
            if (notVehicleNumber) Icon(
                FeatherIcons.AlertCircle,
                contentDescription = null
            )
        },
        onValueChange = numberVehicleOnChange,
        label = {
            Text(
                text = MainRes.string.number_vehicle_label,
                color = Theme.colors.primaryTextColor,
                fontSize = if (isLargePlatform) 16.sp else 8.sp
            )
        },
        modifier = modifier.fillMaxWidth(),
        maxLines = 1
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Theme.colors.primaryTextColor,
            unfocusedTextColor = Theme.colors.primaryTextColor,
            disabledTextColor = Theme.colors.primaryTextColor,
        ),
        value = faultDescription,
        onValueChange = faultDescriptionOnChange,
        label = {
            Text(
                text = MainRes.string.fault_description_label,
                color = Theme.colors.primaryTextColor,
                fontSize = if (isLargePlatform) 16.sp else 8.sp
            )
        },
        modifier = modifier.fillMaxWidth(),
        maxLines = 20
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = MainRes.string.optional_title,
        color = Theme.colors.primaryTextColor,
        fontSize = if (isLargePlatform) 20.sp else 12.sp
    )

    Spacer(modifier = Modifier.height(16.dp))

    ActionButton(text = MainRes.string.arrival_date_button_title, onClick = arrivalDateOnClick)

    Spacer(modifier = Modifier.height(16.dp))

    if (arrivalDate.isNotEmpty()) {
        Column {
            Text(
                text = MainRes.string.arrival_date_title,
                color = Theme.colors.primaryTextColor,
                fontSize = if (isLargePlatform) 20.sp else 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = arrivalDate,
                color = Theme.colors.primaryTextColor,
                fontSize = if (isLargePlatform) 20.sp else 12.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}