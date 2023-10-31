package routes.resources

import features.resources.ResourcesController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.io.File

fun Application.configureResourcesRouting() {
    routing {
        get(path = "/resources/{name_resource}") {
            val resourcesController by closestDI().instance<ResourcesController>()
            val resourceName = call.parameters["name_resource"]!!
            resourcesController.getImageFromName(resourceName = resourceName, call = call)
        }
        post(path = "/resources/create") {
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.createImagesForResources(call = call)
        }

        post(path = "/images/create") {
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.createImages(call = call)
        }

        post(path="/videos/create") {
            val resourcesController by closestDI().instance<ResourcesController>()
            resourcesController.createVideos(call=call)
        }

        get(path = "/images/{name}") {
            val filename = call.parameters["name"]!!
            val file = File("app/images/$filename")
            if (file.exists()) {
                call.respondFile(file)
            } else call.respond(HttpStatusCode.NotFound)
        }

        get(path = "/videos/{name}") {
            val filename = call.parameters["name"]!!
            val file = File("app/videos/$filename")
            if (file.exists()) {
                call.respondFile(file)
            } else call.respond(HttpStatusCode.NotFound)
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