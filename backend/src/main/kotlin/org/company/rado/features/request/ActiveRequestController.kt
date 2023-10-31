package org.company.rado.features.request

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.company.rado.models.requests.StatusRequest
import org.company.rado.models.requests.active.ActiveRequestForMechanicOrDriverRemote
import org.company.rado.models.requests.active.ActiveRequestForObserverRemote
import org.company.rado.models.requests.confirmation.ConfirmationRequestRemote
import org.company.rado.services.request.ActiveRequestService
import org.company.rado.services.request.RequestService

class ActiveRequestController(
    private val activeRequestService: ActiveRequestService,
    private val requestService: RequestService
) {
    suspend fun confirmationRequest(call: ApplicationCall) {
        val confirmationRequestRemote = call.receive<ConfirmationRequestRemote>()
        val response = activeRequestService.confirmationRequest(
            requestId = confirmationRequestRemote.requestId,
            time = confirmationRequestRemote.time,
            date = confirmationRequestRemote.date,
            mechanicId = confirmationRequestRemote.mechanicId
        )
        if (response) {
            call.respond(HttpStatusCode.OK, message = "Request is confirmation")
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "Request is not confirmation")
        }
    }

    suspend fun getActiveRequestsForMechanic(call: ApplicationCall) {
        val activeRequestRemote = call.receive<ActiveRequestForMechanicOrDriverRemote>()
        val response = activeRequestService.activeRequestsForMechanic(
            mechanicId = activeRequestRemote.userId,
            date = activeRequestRemote.date
        )
        call.respond(response)
    }

    suspend fun getActiveRequestsForDriver(call: ApplicationCall) {
        val activeRequestRemote = call.receive<ActiveRequestForMechanicOrDriverRemote>()
        val response = activeRequestService.activeRequestsForDriver(
            driverId = activeRequestRemote.userId,
            date = activeRequestRemote.date
        )
        call.respond(response)
    }

    suspend fun getActiveRequestsForObserver(call: ApplicationCall) {
        val activeRequestRemote = call.receive<ActiveRequestForObserverRemote>()
        val response = activeRequestService.activeRequestsForObserver(
            date = activeRequestRemote.date
        )
        call.respond(response)
    }

    suspend fun getFullActiveRequest(call: ApplicationCall, requestId: Int) {
        val response = requestService.getFullRequest(requestId = requestId, requestType = StatusRequest.ACTIVE)
        call.respond(response)
    }
}