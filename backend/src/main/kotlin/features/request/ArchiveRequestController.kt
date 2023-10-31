package features.request

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import models.requests.StatusRequest
import services.request.ArchiveRequestService
import services.request.RequestService

class ArchiveRequestController(
    private val archiveRequestService: ArchiveRequestService,
    private val requestService: RequestService
) {
    suspend fun archieveRequest(call: ApplicationCall, requestId: Int) {
        val response = archiveRequestService.archieveRequest(requestId = requestId)
        if (response) {
            call.respond(HttpStatusCode.OK, message = "The request has been archieve")
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "The request has not been archieve")
        }
    }

    suspend fun getArchiveRequestsForObserver(call: ApplicationCall) {
        val response = archiveRequestService.getAllArchiveRequestsForObserver()
        call.respond(response)
    }

    suspend fun getArchiveRequestsForDriver(call: ApplicationCall, driverId: Int) {
        val response = archiveRequestService.getAllArchiveRequestsForDriver(driverId = driverId)
        call.respond(response)
    }

    suspend fun getArchiveRequestsForMechanic(call: ApplicationCall, mechanicId: Int) {
        val response = archiveRequestService.getAllArchiveRequestsForMechanic(mechanicId = mechanicId)
        call.respond(response)
    }

    suspend fun getFullArchiveRequest(call: ApplicationCall, requestId: Int) {
        val response = requestService.getFullRequest(requestId = requestId, requestType = StatusRequest.ARCHIVE)
        call.respond(response)
    }
}