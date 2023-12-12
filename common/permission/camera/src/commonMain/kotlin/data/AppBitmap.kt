package data

import androidx.compose.ui.graphics.ImageBitmap

expect class AppBitmap{
    fun toByteArray(): ByteArray

    fun toBase64():String

    fun toBase64WithCompress(maxSize:Int):String

    fun toImageBitmap(): ImageBitmap
}

private const val BASE64_IMAGE_MIME_PREFIX = "data:image/png;base64,"

fun AppBitmap.toBase64WithCompressMIME(maxSize: Int) =
    "$BASE64_IMAGE_MIME_PREFIX${toBase64WithCompress(maxSize)}"

fun AppBitmap.toBase64MIME() = "$BASE64_IMAGE_MIME_PREFIX${toBase64()}"