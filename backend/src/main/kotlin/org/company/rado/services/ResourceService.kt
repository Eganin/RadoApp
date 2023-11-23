package org.company.rado.services

import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import org.company.rado.dao.images.ImagesDaoFacade
import org.company.rado.dao.videos.VideosDaoFacade
import org.company.rado.features.resources.ResourcesController.Companion.copyToSuspend
import java.io.File
import java.util.UUID
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
                    val resourcePath =
                        if (isImage) "app/images/${System.currentTimeMillis()}-${UUID.randomUUID()}-${part.originalFileName}"
                        else "app/videos/${System.currentTimeMillis()}-${UUID.randomUUID()}-${part.originalFileName}"
                    val mFile = File(resourcePath)
                    part.streamProvider().use { its ->
                        mFile.outputStream().buffered().use { its.copyToSuspend(it) }
                    }
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

    suspend fun deleteImageAndVideoByName(resourceName: String, isImage: Boolean): Boolean {
        val (isDelete, resourcePath) = if (isImage) imageRepository.deleteImageByName(imageName = resourceName) else videoRepository.deleteVideoByName(
            videoName = resourceName
        )
        val isDeleteFromDisk = File(resourcePath).delete()

        return isDelete && isDeleteFromDisk
    }
}
