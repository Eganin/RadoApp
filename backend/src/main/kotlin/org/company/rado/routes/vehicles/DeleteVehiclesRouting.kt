package org.company.rado.routes.vehicles

import org.company.rado.features.vehicles.VehicleController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureDeleteVehiclesRouting() {
    routing {
        delete(path = "/vehicles/delete") {
            val vehiclesController by closestDI().instance<VehicleController>()
            vehiclesController.deleteVehicle(call = call)
        }
    }
}