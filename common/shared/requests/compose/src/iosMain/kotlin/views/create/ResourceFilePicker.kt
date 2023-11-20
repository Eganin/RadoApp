package views.create

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.IosFile
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.posix.memcpy


@Composable
actual fun ResourceFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, Boolean, ByteArray) -> Unit
) {
    val fileTypeImage = listOf("JPG", "JPEG","PNG")
    val fileTypeVideo = listOf("MP4", "MOV", "AVI", "MKV")
    val fileType = fileTypeImage + fileTypeVideo
    FilePicker(showFilePicker, fileExtensions = fileType) { file ->
        closeFilePicker.invoke()
        file?.let {file->
            val newFile = file as IosFile
            val isImage = fileTypeImage.any { it == newFile.path.split(".").last() }
            val path = newFile.path.split("/").last()
            val byteArray = newFile.platformFile.dataRepresentation.toByteArray()
            receiveFilePathAndByteArray.invoke(
                path,
                isImage,
                byteArray
            )
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    return ByteArray(length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), bytes, length)
        }
    }
}