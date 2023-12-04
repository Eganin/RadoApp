package data

expect interface ImageGallery {
    suspend fun getImage():List<Image>
}