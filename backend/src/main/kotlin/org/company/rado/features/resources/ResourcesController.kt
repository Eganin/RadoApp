package org.company.rado.features.resources

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.application.ApplicationCall
import io.ktor.server.http.content.LocalFileContent
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import org.company.rado.services.ResourceService
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class ResourcesController(
    private val resourceService: ResourceService
) {

    suspend fun getResourceImageFromName(resourceName: String, call: ApplicationCall) {
        try {
            val filename = "app/resources/${resourceName}"
            val file = File(filename)
            call.respondFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    suspend fun getResourceVideoFromName(resourceName: String, call: ApplicationCall) {
        try {
            val filename = "app/resources/${resourceName}"
            val file = File(filename)
            call.respond(LocalFileContent(file, contentType = ContentType.Video.MP4))
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    suspend fun createResources(call: ApplicationCall) {
        val multipartData = call.receiveMultipart()
        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    val imagePath = "app/resources/${part.originalFileName}"
                    val mFile = File(imagePath)
                    part.streamProvider().use { its ->
                        mFile.outputStream().buffered().use { its.copyToSuspend(it) }
                    }
                }

                else -> {}
            }
            part.dispose()
        }
        call.respond(HttpStatusCode.OK, message = "File Uploaded ✅")
    }

    suspend fun createImages(call: ApplicationCall) {
        val multipartData = call.receiveMultipart()
        resourceService.createImageAndVideo(data = multipartData, isImage = true)
        call.respond(HttpStatusCode.OK, message = "File Uploaded ✅")
    }

    suspend fun createVideos(call: ApplicationCall) {
        val multipartData = call.receiveMultipart()
        resourceService.createImageAndVideo(data = multipartData, isImage = false)
        call.respond(HttpStatusCode.OK, message = "File Uploaded ✅")
    }

    suspend fun deleteVideos(call: ApplicationCall, requestId: Int) {
        val response = resourceService.deleteImageAndVideo(requestId = requestId, isImage = false)
        if (response) {
            call.respond(HttpStatusCode.OK, message = "The videos has been deleted")
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "The videos has not been deleted")
        }
    }

    suspend fun deleteImages(call: ApplicationCall, requestId: Int) {
        val response = resourceService.deleteImageAndVideo(requestId = requestId, isImage = true)
        if (response) {
            call.respond(HttpStatusCode.OK, message = "The images has been deleted")
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "The images has not been deleted")
        }
    }

    suspend fun deleteVideoByName(call: ApplicationCall,videoName: String){
        val videoUrlSplit = "app/videos/"+videoName.split("/videos/").last()
        val response = resourceService.deleteImageAndVideoByName(resourceName = videoUrlSplit,isImage = false)
        if (response){
            call.respond(HttpStatusCode.OK, message = "The image has been deleted by name")
        }else{
            call.respond(HttpStatusCode.BadRequest,message = "The image has not been deleted")
        }
    }

    suspend fun deleteImageByName(call:ApplicationCall,imageName:String){
        val imageNameSplit = "app/images/"+ imageName.split("/images/").last()
        val response = resourceService.deleteImageAndVideoByName(resourceName = imageNameSplit,isImage = true)
        if (response){
            call.respond(HttpStatusCode.OK, message = "The video has been deleted by name")
        }else{
            call.respond(HttpStatusCode.BadRequest,message = "The video has not been deleted")
        }
    }

    suspend fun deleteResource(call: ApplicationCall, resourceName: String) {
        val file = File("app/resources/${resourceName}")
        file.delete()
        call.respond(HttpStatusCode.OK, message = "File Deleted✅")
    }

    companion object{
        suspend fun InputStream.copyToSuspend(
            out: OutputStream,
            bufferSize: Int = DEFAULT_BUFFER_SIZE,
            yieldSize: Int = 4 * 1024 * 1024,
            dispatcher: CoroutineDispatcher = Dispatchers.IO
        ): Long {
            return withContext(dispatcher) {
                val buffer = ByteArray(bufferSize)
                var bytesCopied = 0L
                var bytesAfterYield = 0L
                while (true) {
                    val bytes = read(buffer).takeIf { it >= 0 } ?: break
                    out.write(buffer, 0, bytes)
                    if (bytesAfterYield >= yieldSize) {
                        yield()
                        bytesAfterYield %= yieldSize
                    }
                    bytesCopied += bytes
                    bytesAfterYield += bytes
                }
                return@withContext bytesCopied
            }
        }
    }
}