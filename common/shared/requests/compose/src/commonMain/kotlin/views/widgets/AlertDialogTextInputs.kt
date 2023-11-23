package views.widgets

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

@Composable
internal fun AlertDialogTextInputs(
    numberVehicle: String,
    notVehicleNumber: Boolean,
    faultDescription: String,
    isLargePlatform: Boolean,
    numberVehicleOnChange: (String) -> Unit,
    faultDescriptionOnChange: (String) -> Unit,
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
}