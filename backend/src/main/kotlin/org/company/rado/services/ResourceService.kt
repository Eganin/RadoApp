package org.company.rado.services

import org.company.rado.dao.images.ImagesDaoFacade
import org.company.rado.dao.videos.VideosDaoFacade
import io.ktor.http.content.*
import java.io.File
import kotlin.properties.Delegates

class ResourceService(
    private val imageRepository: ImagesDaoFacade,
    private val videoRepository: VideosDaoFacade
) {

    suspend fun createImageAndVideo(data: MultiPartData, isImage: Boolean) {
        var requestId by Delegates.notNull<Int>()
        data.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    val fileBytes = part.streamProvider().readBytes()
                    val resourcePath =
                        if (isImage) "app/images/${part.originalFileName}" else "app/videos/${part.originalFileName}"
                    val mFile = File(resourcePath)
                    mFile.writeBytes(fileBytes)
                    if (isImage) {
                        imageRepository.createImage(imagePath = resourcePath, requestId = requestId)
                    } else {
                        videoRepository.createVideo(videoPath = resourcePath, requestId = requestId)
                    }
                }

                is PartData.FormItem -> {
                    requestId = part.value.toInt()
                }

                else -> {}
            }
            part.dispose()
        }
    }

    suspend fun deleteImageAndVideo(requestId: Int, isImage: Boolean): Boolean {
        val (isDelete, resourcesPath) = if (isImage) imageRepository.deleteImages(requestId = requestId) else videoRepository.deleteVideos(
            requestId = requestId
        )
        val isDeleteResourceList = mutableListOf<Boolean>()
        resourcesPath.forEach {
            val file = File(it)
            isDeleteResourceList.add(file.delete())
        }

        return isDeleteResourceList.all { it } && isDelete
    }
}
