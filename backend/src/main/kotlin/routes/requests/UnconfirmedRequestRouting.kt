package routes.requests

import features.request.UnconfirmedRequestController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureUnconfirmedRequestRouting() {
    routing {
        get(path = "/request/unconfirmed/mechanic") {
            val requestController by closestDI().instance<UnconfirmedRequestController>()
            requestController.getUnconfirmedRequestsForMechanic(call = call)
        }

        get(path = "/request/unconfirmed/driver/{driverId}") {
            val driverId = call.parameters["driverId"]!!.toInt()
            val requestController by closestDI().instance<UnconfirmedRequestController>()
            requestController.getUnconfirmedRequestsForDriver(call = call,driverId=driverId)
        }

        get(path = "/request/unconfirmed/info/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val requestController by closestDI().instance<UnconfirmedRequestController>()
            requestController.getFullUnconfirmedRequest(call = call, requestId = requestId)
        }
    }
}