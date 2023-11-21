package org.company.rado.routes.resources

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.http.content.LocalFileContent
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.company.rado.features.resources.ResourcesController
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.io.File


@Resource(path = "/videos/{name}")
class VideoStreamForRequest

@Resource(path = "/resources/videos/{name_resource}")
class VideoStreamForResource

fun Application.configureResourcesRouting() {
    routing {
        get(path = "/resources/images/{name_resource}") {
            val resourcesController by closestDI().instance<ResourcesController>()
            val resourceName = call.parameters["name_resource"]!!
            resourcesController.getResourceImageFromName(resourceName = resourceName, call = call)
        }

        get<VideoStreamForResource> {
            val resourcesController by closestDI().instance<ResourcesController>()
            val resourceName = call.parameters["name_resource"]!!
            resourcesController.getResourceVideoFromName(resourceName = resourceName, call = call)
        }

        post(path = "/resources/create") {
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.createResources(call = call)
        }

        post(path = "/images/create") {
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.createImages(call = call)
        }

        post(path = "/videos/create") {
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.createVideos(call = call)
        }

        get(path = "/images/{name}") {
            val filename = call.parameters["name"]!!
            val file = File("app/images/$filename")
            if (file.exists()) {
                call.respondFile(file)
            } else call.respond(HttpStatusCode.NotFound)
        }

        get<VideoStreamForRequest> {
            val filename = call.parameters["name"]!!
            val file = File("app/videos/$filename")
            if (file.exists()) {
                call.respond(LocalFileContent(file, contentType = ContentType.Video.MP4))
                //call.respondBytes(bytes = file.readBytes(), contentType = ContentType.Video.MP4)
                //call.respondFile(file)
            } else call.respond(HttpStatusCode.NotFound)
        }

        delete(path = "/resources/delete/{name}") {
            val resourceName = call.parameters["name"]!!
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.deleteResource(call = call, resourceName = resourceName)
        }

        delete(path = "/videos/delete/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.deleteVideos(call = call, requestId = requestId)
        }

        delete(path = "/images/delete/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.deleteImages(call = call, requestId = requestId)
        }
    }
}