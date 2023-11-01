package org.company.rado.routes.requests

import org.company.rado.features.request.RejectRequestController
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.company.rado.models.requests.reject.RejectRequestRemote
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureRejectedRequestRouting() {
    routing {
        put(path = "/request/reject") {
            val requestController by closestDI().instance<RejectRequestController>()
            val rejectRequestRemote = call.receive<RejectRequestRemote>()
            requestController.rejectRequest(
                call = call,
                requestId = rejectRequestRemote.requestId,
                commentMechanic = rejectRequestRemote.commentMechanic,
                mechanicId = rejectRequestRemote.mechanicId
            )
        }

        get(path = "/request/reject/observer") {
            val requestController by closestDI().instance<RejectRequestController>()
            requestController.rejectRequestsForObserver(call = call)
        }

        get(path = "/request/reject/driver/{driverId}") {
            val driverId = call.parameters["driverId"]!!.toInt()
            val requestController by closestDI().instance<RejectRequestController>()
            requestController.rejectRequestsForDriver(call = call, driverId = driverId)
        }

        get(path = "/request/reject/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val requestController by closestDI().instance<RejectRequestController>()
            requestController.fullRejectRequestById(call = call, requestId = requestId)
        }
    }
}