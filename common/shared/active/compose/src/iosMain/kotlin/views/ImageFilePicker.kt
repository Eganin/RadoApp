package views

import androidx.compose.runtime.Composable


@Composable
actual fun ImageFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, ByteArray) -> Unit
) {

}