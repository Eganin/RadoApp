package org.company.rado.features.resources

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.response.respond
import org.company.rado.services.ResourceService
import java.io.File

class ResourcesController(
    private val resourceService: ResourceService
) {

    suspend fun getImageFromName(resourceName: String, call: ApplicationCall) {
        try {
            val filename = "app/resources/${resourceName}"
            println(filename)
            val file = File(filename)
            call.respondFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    suspend fun createImagesForResources(call: ApplicationCall) {
        val multipartData = call.receiveMultipart()
        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    val fileBytes = part.streamProvider().readBytes()
                    val imagePath = "app/resources/${part.originalFileName}"
                    val mFile = File(imagePath)
                    mFile.writeBytes(fileBytes)
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

    suspend fun createVideos(call:ApplicationCall){
        val multipartData = call.receiveMultipart()
        resourceService.createImageAndVideo(data = multipartData, isImage = false)
        call.respond(HttpStatusCode.OK, message = "File Uploaded ✅")
    }

    suspend fun deleteVideos(call:ApplicationCall,requestId: Int){
        val response = resourceService.deleteImageAndVideo(requestId=requestId, isImage = false)
        if (response){
            call.respond(HttpStatusCode.OK, message = "The videos has been deleted")
        }else{
            call.respond(HttpStatusCode.BadRequest, message = "The videos has not been deleted")
        }
    }

    suspend fun deleteImages(call: ApplicationCall,requestId:Int){
        val response = resourceService.deleteImageAndVideo(requestId=requestId, isImage = true)
        if (response){
            call.respond(HttpStatusCode.OK, message = "The images has been deleted")
        }else{
            call.respond(HttpStatusCode.BadRequest, message = "The images has not been deleted")
        }
    }

    suspend fun deleteResource(call: ApplicationCall,resourceName:String){
        val file = File("app/resources/${resourceName}")
        file.delete()
        call.respond(HttpStatusCode.OK, message = "File Deleted✅")
    }
}