package routes.requests

import features.request.DeleteRequestController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureDeleteRequestRouting() {
    routing {
        delete(path = "/requests/delete/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val deleteRequestController by closestDI().instance<DeleteRequestController>()
            deleteRequestController.deleteRequest(call = call, requestId = requestId)
        }
    }
}