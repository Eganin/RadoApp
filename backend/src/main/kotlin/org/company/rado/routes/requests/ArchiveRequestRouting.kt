package org.company.rado.routes.requests

import org.company.rado.features.request.ArchiveRequestController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureArchiveRequestRouting() {
    routing {
        put(path = "/request/archieve/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val requestController by closestDI().instance<ArchiveRequestController>()
            requestController.archieveRequest(call = call, requestId = requestId)
        }

        get(path = "/request/archive/observer") {
            val requestController by closestDI().instance<ArchiveRequestController>()
            requestController.getArchiveRequestsForObserver(call = call)
        }

        get(path = "/request/archive/driver/{driverId}") {
            val driverId = call.parameters["driverId"]!!.toInt()
            val requestController by closestDI().instance<ArchiveRequestController>()
            requestController.getArchiveRequestsForDriver(call = call, driverId = driverId)
        }

        get(path = "/request/archive/mechanic/{mechanicId}") {
            val mechanicId = call.parameters["mechanicId"]!!.toInt()
            val requestController by closestDI().instance<ArchiveRequestController>()
            requestController.getArchiveRequestsForMechanic(call = call, mechanicId = mechanicId)
        }

        get(path = "/request/archive/info/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val requestController by closestDI().instance<ArchiveRequestController>()
            requestController.getFullArchiveRequest(requestId = requestId, call = call)
        }
    }
}