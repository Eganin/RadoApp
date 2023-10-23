package views.create

import androidx.compose.runtime.Composable

import com.darkrockstudios.libraries.mpfilepicker.AndroidFile
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import di.Inject
import platform.PlatformConfiguration

@Composable
actual fun ImageFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, ByteArray) -> Unit
) {
    val context = Inject.instance<PlatformConfiguration>().androidContext

    val fileType = listOf("jpg", "png", "jpeg")
    FilePicker(showFilePicker, fileExtensions = fileType) { file ->
        closeFilePicker.invoke()
        val newFile = file as AndroidFile
        val imageByteArray = context.contentResolver.openInputStream(newFile.platformFile)
            ?.use { it.buffered().readBytes() }
        imageByteArray?.let {
            receiveFilePathAndByteArray.invoke(newFile.path, imageByteArray)
        }
    }
}