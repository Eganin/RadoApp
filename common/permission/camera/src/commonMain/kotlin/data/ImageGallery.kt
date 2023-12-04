package data

expect interface ImageGallery {
    suspend fun getImages():List<Image>
}