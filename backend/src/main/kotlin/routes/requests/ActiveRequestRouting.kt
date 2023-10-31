package routes.requests

import features.request.ActiveRequestController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureActiveRequestRouting() {
    routing {
        put(path = "/request/confirmation") {
            val requestController by closestDI().instance<ActiveRequestController>()
            requestController.confirmationRequest(call = call)
        }

        post(path = "/request/active/mechanic") {
            val requestController by closestDI().instance<ActiveRequestController>()
            requestController.getActiveRequestsForMechanic(call = call)
        }

        post(path = "/request/active/driver") {
            val requestController by closestDI().instance<ActiveRequestController>()
            requestController.getActiveRequestsForDriver(call = call)
        }

        post(path = "/request/active/observer") {
            val requestController by closestDI().instance<ActiveRequestController>()
            requestController.getActiveRequestsForObserver(call = call)
        }

        get(path = "/request/active/info/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val requestController by closestDI().instance<ActiveRequestController>()
            requestController.getFullActiveRequest(requestId = requestId, call = call)
        }
    }
}