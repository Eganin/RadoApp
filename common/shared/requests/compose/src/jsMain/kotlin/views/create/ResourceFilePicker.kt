package views.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.WebFile
import kotlinx.coroutines.launch
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.files.File
import org.w3c.files.FileReader
import kotlin.coroutines.suspendCoroutine

@Composable
actual fun ResourceFilePicker(
    showFilePicker: Boolean,
    closeFilePicker: () -> Unit,
    receiveFilePathAndByteArray: (String, Boolean, ByteArray) -> Unit
) {
    val fileTypeImage = listOf("jpg", "png", "jpeg")
    val fileTypeVideo = listOf("mp4", "mov", "avi", "mkv")
    val fileType = fileTypeImage + fileTypeVideo
    val scope = rememberCoroutineScope()
    FilePicker(showFilePicker, fileExtensions = fileType) { file ->
        closeFilePicker.invoke()
        file?.let {file->
            val newFile = file as WebFile
            val isImage = fileTypeImage.any { it == newFile.path.split(".").last() }
            scope.launch {
                val byteArray = readFileAsArrayBuffer(file = newFile.platformFile).toByteArray()
                receiveFilePathAndByteArray.invoke(
                    newFile.path.split("/").last(),
                    isImage,
                    byteArray
                )
            }
        }
    }
}

suspend fun readFileAsArrayBuffer(file: File): ArrayBuffer = suspendCoroutine {
    val reader = FileReader()
    reader.onload = { loadEvt ->
        val content = loadEvt.target.asDynamic().result as ArrayBuffer
        it.resumeWith(Result.success(content))
    }
    reader.readAsArrayBuffer(file)
}


fun ArrayBuffer.toByteArray(): ByteArray = Int8Array(this).unsafeCast<ByteArray>()