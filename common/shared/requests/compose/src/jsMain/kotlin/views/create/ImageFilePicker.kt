package views.create

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.WebFile

@Composable
actual fun ImageFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, ByteArray) -> Unit
) {
    val fileType = listOf("jpg", "png", "jpeg")
    FilePicker(showFilePicker, fileExtensions = fileType) { file ->
        closeFilePicker.invoke()
        val newFile = file as WebFile
        newFile.platformFile
        receiveFilePathAndByteArray.invoke(newFile.path, ByteArray(size=0))
    }
}
