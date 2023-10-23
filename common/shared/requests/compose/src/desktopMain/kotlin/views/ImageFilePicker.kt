package views

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.JvmFile

@Composable
actual fun ImageFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, ByteArray) -> Unit
) {
    val fileType = listOf("jpg", "png", "jpeg")
    FilePicker(showFilePicker, fileExtensions = fileType) { file ->
        closeFilePicker.invoke()
        val newFile = file as JvmFile
        receiveFilePathAndByteArray.invoke(newFile.path, newFile.platformFile.readBytes())
    }
}