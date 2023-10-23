package views.create

import androidx.compose.runtime.Composable


//returns ByteArray if android target else only file path
@Composable
expect fun ImageFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, ByteArray) -> Unit
)