package data

import androidx.compose.ui.graphics.ImageBitmap

expect class AppBitmap{
    fun toByteArray(): ByteArray

    fun toBase64():String

    fun toBase64WithCompress(maxSize:Int):String

    fun toImageBitmap(): ImageBitmap
}