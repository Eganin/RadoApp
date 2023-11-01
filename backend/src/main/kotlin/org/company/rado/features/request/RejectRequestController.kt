package org.company.rado.features.request

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.company.rado.services.request.RejectRequestService

class RejectRequestController(
    private val rejectRequestService: RejectRequestService
) {
    suspend fun rejectRequest(call: ApplicationCall, requestId: Int, commentMechanic: String, mechanicId: Int) {
        val response = rejectRequestService.rejectRequest(
            requestId = requestId,
            commentMechanic = commentMechanic,
            mechanicId = mechanicId
        )
        if (response) {
            call.respond(HttpStatusCode.OK, message = "Request is reject")
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "Request is not reject")
        }
    }

    suspend fun rejectRequestsForObserver(call: ApplicationCall) {
        val response = rejectRequestService.getAllRejectRequest()
        call.respond(response)
    }

    suspend fun rejectRequestsForDriver(call: ApplicationCall, driverId: Int) {
        val response = rejectRequestService.getAllRejectRequest(driverId = driverId)
        call.respond(response)
    }

    suspend fun fullRejectRequestById(call: ApplicationCall, requestId: Int) {
        val response = rejectRequestService.getRejectRequestById(requestId = requestId)
        call.respond(response)
    }
}