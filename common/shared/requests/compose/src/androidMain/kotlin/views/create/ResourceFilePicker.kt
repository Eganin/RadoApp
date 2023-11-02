package views.create

import androidx.compose.runtime.Composable

import com.darkrockstudios.libraries.mpfilepicker.AndroidFile
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import di.Inject
import platform.PlatformConfiguration

@Composable
actual fun ResourceFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, Boolean, ByteArray) -> Unit
) {
    val context = Inject.instance<PlatformConfiguration>().androidContext

    val fileTypeImage = listOf("jpg", "png", "jpeg")
    val fileTypeVideo = listOf("mp4", "mov", "avi", "mkv")
    val fileType = fileTypeImage + fileTypeVideo
    FilePicker(showFilePicker, fileExtensions = fileType) { file ->
        closeFilePicker.invoke()
        val newFile = file as AndroidFile
        val imageByteArray = context.contentResolver.openInputStream(newFile.platformFile)
            ?.use { it.buffered().readBytes() }
        val isImage = fileTypeImage.any { it == newFile.path.split(".").last() }
        imageByteArray?.let {
            receiveFilePathAndByteArray.invoke(
                newFile.path.split("/").last(),
                isImage,
                imageByteArray
            )
        }
    }
}