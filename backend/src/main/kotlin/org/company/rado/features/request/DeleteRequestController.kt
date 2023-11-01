package org.company.rado.features.request

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.company.rado.services.request.DeleteRequestService

class DeleteRequestController(
    private val deleteRequestService: DeleteRequestService
) {

    suspend fun deleteRequest(call: ApplicationCall, requestId: Int) {
        val response = deleteRequestService.deleteRequest(requestId = requestId)

        if (response) {
            call.respond(HttpStatusCode.OK, message = "Request has been deleted")
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "Request has not been deleted")
        }
    }
}