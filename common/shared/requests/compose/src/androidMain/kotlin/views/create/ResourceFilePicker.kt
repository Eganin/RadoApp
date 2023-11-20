package views.create

import androidx.compose.runtime.Composable

import com.darkrockstudios.libraries.mpfilepicker.AndroidFile
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import di.Inject
import io.github.aakira.napier.log
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
        file?.let {file->
            val newFile = file as AndroidFile
            val imageByteArray = context.contentResolver.openInputStream(newFile.platformFile)
                ?.use { it.buffered().readBytes() }
            val isImage = newFile.path.split("/").last().split("%").first() == "image"
            log(tag="IMAGE") {newFile.path }
            log(tag="IMAGE") {isImage.toString() }
            val path = newFile.path.split("%").last()+".png"
            imageByteArray?.let {
                receiveFilePathAndByteArray.invoke(
                    path,
                    isImage,
                    imageByteArray
                )
            }
        }
    }
}