package org.company.rado.features.request

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.company.rado.models.requests.create.CreateRequestRemote
import org.company.rado.models.requests.recreate.RecreateRequestRemote
import org.company.rado.services.request.RequestService

class RequestController(
    private val requestService: RequestService
) {

    suspend fun createRequest(call: ApplicationCall) {
        val requestRemoteInfo = call.receive<CreateRequestRemote>()
        val response = requestService.createRequest(
            typeVehicle = requestRemoteInfo.typeVehicle,
            numberVehicle = requestRemoteInfo.numberVehicle,
            faultDescription = requestRemoteInfo.faultDescription,
            driverUsername = requestRemoteInfo.driverUsername
        )
        call.respond(response)
    }

    suspend fun recreateRequest(call: ApplicationCall, requestId: Int) {
        val requestRemoteInfo = call.receive<RecreateRequestRemote>()
        val response = requestService.recreateRequest(
            requestId = requestId,
            typeVehicle = requestRemoteInfo.typeVehicle,
            numberVehicle = requestRemoteInfo.numberVehicle,
            oldTypeVehicle = requestRemoteInfo.oldTypeVehicle,
            oldNumberVehicle = requestRemoteInfo.oldNumberVehicle,
            faultDescription = requestRemoteInfo.faultDescription,
            driverId = requestRemoteInfo.driverId
        )
        if (response != null) {
            call.respond(response)
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "Request is not recreate")
        }
    }
}