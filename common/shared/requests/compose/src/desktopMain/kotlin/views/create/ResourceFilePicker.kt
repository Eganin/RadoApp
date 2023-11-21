package views.create

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.JvmFile
import views.isMacOS

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
        file?.let { file ->
            val newFile = file as JvmFile
            val isImage = fileTypeImage.any { it == newFile.path.split(".").last() }
            val path =
                if (isMacOS()) newFile.path.split("/").last() else newFile.path.split("\\").last()
            val byteArray = newFile.platformFile.readBytes()
            receiveFilePathAndByteArray.invoke(
                path,
                isImage,
                byteArray
            )
        }
    }
}