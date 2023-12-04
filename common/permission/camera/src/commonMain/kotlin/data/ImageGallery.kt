package data

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect interface ImageGallery {
    suspend fun getImages():List<Image>
}