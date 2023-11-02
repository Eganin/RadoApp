package views.create

import androidx.compose.runtime.Composable


@Composable
expect fun ResourceFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String,Boolean, ByteArray) -> Unit
)