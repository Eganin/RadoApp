package data

import java.io.File

actual fun FileMedia.toByteArray(): ByteArray {
    val file = File(path)
    return file.readBytes()
}