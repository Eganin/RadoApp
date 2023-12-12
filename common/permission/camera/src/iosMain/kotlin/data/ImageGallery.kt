package data

actual interface ImageGallery {
    actual suspend fun getImages(): List<Image>
}