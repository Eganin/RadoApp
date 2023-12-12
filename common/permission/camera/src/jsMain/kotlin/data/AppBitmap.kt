package data

import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.skia.Bitmap

actual class AppBitmap constructor(
    private val delegate: Delegate
) {
    constructor(platformBitmap: Bitmap) : this(AndroidDelegate(platformBitmap))

    private val platformBitmap: Bitmap get() = delegate.getAndroidBitmap()

    actual fun toByteArray(): ByteArray {
        return delegate.getByteArray()
    }

    actual fun toBase64(): String {
        val byteArray = toByteArray()
        return ""
    }

    actual fun toBase64WithCompress(maxSize: Int): String {
        val bitmap = delegate.getResized(maxSize)
        return bitmap.toBase64()
    }

    actual fun toImageBitmap(): ImageBitmap {
        return ImageBitmap(height = 0, width = 0)
    }

    interface Delegate {
        fun getAndroidBitmap(): Bitmap
        fun getByteArray(): ByteArray
        fun getResized(maxSize: Int): AppBitmap
    }

    class AndroidDelegate(private val bitmap: Bitmap) : Delegate {
        override fun getAndroidBitmap(): Bitmap {
            return bitmap
        }

        override fun getByteArray(): ByteArray {
            return ByteArray(0)
        }

        override fun getResized(maxSize: Int): AppBitmap {
            return AppBitmap(bitmap)
        }
    }
}