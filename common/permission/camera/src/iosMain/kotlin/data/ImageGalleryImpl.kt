package data

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.*
import platform.Photos.*
import platform.UIKit.UIImage

class ImageGalleryImpl : ImageGallery {
    private val imageManager = PHImageManager.defaultManager()
    private var selectedImages = mutableListOf<Pair<UIImage, NSURL>>()

    private fun getImages(completion: (List<Pair<UIImage, NSURL>>) -> Unit) {
        val status = PHPhotoLibrary.authorizationStatus()
        if (status == PHAuthorizationStatusAuthorized) {
            fetchImages(completion)
        } else if (status == PHAuthorizationStatusDenied || status == PHAuthorizationStatusRestricted) {
            completion(emptyList())
        } else {
            PHPhotoLibrary.requestAuthorization { newStatus ->
                if (newStatus == PHAuthorizationStatusAuthorized) {
                    fetchImages(completion)
                } else {
                    completion(emptyList())
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class, ExperimentalForeignApi::class)
    private fun fetchImages(completion: (List<Pair<UIImage, NSURL>>) -> Unit) {
        val fetchOptions = PHFetchOptions()
        fetchOptions.sortDescriptors =
            listOf(NSSortDescriptor.sortDescriptorWithKey("creationDate", ascending = false))
        val fetchResult =
            PHAsset.fetchAssetsWithMediaType(PHAssetMediaTypeImage, options = fetchOptions)

        fetchResult.let { result ->
            val requestOptions = PHImageRequestOptions().apply {
                deliveryMode = PHImageRequestOptionsDeliveryModeFastFormat
                synchronous = true
            }

            GlobalScope.launch(Dispatchers.Default) {
                for (i in 0 until result.count.toInt()) {
                    imageManager.requestImageForAsset(
                        result.objectAtIndex(1.toULong()) as PHAsset,
                        targetSize = CGSizeMake(width = 200.0, height = 200.0),
                        contentMode = PHImageContentModeAspectFill,
                        options = requestOptions
                    ) { image, info ->
                        if (image != null && info != null) {
                            info["PHImageFileURLKey"]?.let { url ->
                                selectedImages.add(Pair(image, url as NSURL))
                            }
                        }
                    }
                }

                GlobalScope.launch(Dispatchers.Main) {
                    completion(selectedImages)
                }
            }
        }
    }

    override suspend fun getImages(): List<Image> {
        val image = mutableListOf<Image>()
        getImages {
            image.addAll(it)
        }
        return image
    }
}

private fun <E> MutableList<E>.addAll(elements: List<Pair<UIImage, NSURL>>) {

}