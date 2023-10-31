package features.request

import io.ktor.server.application.*
import io.ktor.server.response.*
import services.request.UnconfirmedRequestService

class UnconfirmedRequestController(
    private val unconfirmedRequestService: UnconfirmedRequestService
) {
    suspend fun getUnconfirmedRequestsForMechanic(call: ApplicationCall) {
        val response = unconfirmedRequestService.getUnconfirmedRequests()
        call.respond(response)
    }

    suspend fun getUnconfirmedRequestsForDriver(call: ApplicationCall, driverId: Int) {
        val response = unconfirmedRequestService.getUnconfirmedRequests(driverId=driverId)
        call.respond(response)
    }

    suspend fun getFullUnconfirmedRequest(call: ApplicationCall, requestId: Int) {
        val response = unconfirmedRequestService.getFullUnconfirmedRequest(requestId = requestId)
        call.respond(response)
    }
}