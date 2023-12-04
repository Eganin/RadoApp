package data

import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class AppBitmap constructor(
    private val delegate: Delegate
) {
    constructor(platformBitmap: android.graphics.Bitmap) : this(AndroidDelegate(platformBitmap))

    val platformBitmap: android.graphics.Bitmap get() = delegate.getAndroidBitmap()

    actual fun toByteArray(): ByteArray {
        return delegate.getByteArray()
    }

    actual fun toBase64(): String {
        val byteArray = toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    actual fun toBase64WithCompress(maxSize: Int): String {
        val bitmap = delegate.getResized(maxSize)
        return bitmap.toBase64()
    }

    actual fun toImageBitmap(): ImageBitmap {
        return this.platformBitmap.asImageBitmap()
    }

    interface Delegate {
        fun getAndroidBitmap(): android.graphics.Bitmap
        fun getByteArray(): ByteArray
        fun getResized(maxSize: Int): AppBitmap
    }

    class AndroidDelegate(private val bitmap: android.graphics.Bitmap) : Delegate {
        override fun getAndroidBitmap(): android.graphics.Bitmap {
            return bitmap
        }

        override fun getByteArray(): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            @Suppress("MagicNumber")
            bitmap.compress(
                android.graphics.Bitmap.CompressFormat.PNG,
                50,
                byteArrayOutputStream
            )
            return byteArrayOutputStream.toByteArray()
        }

        override fun getResized(maxSize: Int): AppBitmap {
            val compressedBitmap = BitmapUtils.getResizedBitmap(bitmap, maxSize)
            return AppBitmap(compressedBitmap)
        }
    }
}