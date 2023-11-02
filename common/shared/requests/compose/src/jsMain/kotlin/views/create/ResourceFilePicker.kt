package views.create

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.WebFile

@Composable
actual fun ResourceFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, Boolean, ByteArray) -> Unit
) {
    val fileTypeImage = listOf("jpg", "png", "jpeg")
    val fileTypeVideo = listOf("mp4", "mov", "avi", "mkv")
    val fileType = fileTypeImage + fileTypeVideo
    FilePicker(showFilePicker, fileExtensions = fileType) { file ->
        closeFilePicker.invoke()
        val newFile = file as WebFile
        val isImage = fileTypeImage.any { it == newFile.path.split(".").last() }
        receiveFilePathAndByteArray.invoke(
            newFile.path.split("/").last(),
            isImage,
            ByteArray(size = 0)
        )
    }
}
