package features.vehicles

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import models.vehicles.VehicleDTO
import services.VehicleService

class VehicleController(
    private val vehicleService: VehicleService
) {

    suspend fun deleteVehicle(call: ApplicationCall) {
        val vehicleDTO = call.receive<VehicleDTO>()
        val isDeleteVehicle =
            vehicleService.deleteVehicle(numberVehicle = vehicleDTO.numberVehicle, typeVehicle = vehicleDTO.typeVehicle)
        if (isDeleteVehicle) {
            call.respond(HttpStatusCode.OK, message = "The vehicle has been deleted")
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "The vehicle has not been deleted")
        }
    }
}