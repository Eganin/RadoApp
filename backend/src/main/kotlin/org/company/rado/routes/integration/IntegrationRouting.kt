package org.company.rado.routes.integration

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.company.rado.features.request.ActiveRequestController
import org.company.rado.features.request.ArchiveRequestController
import org.company.rado.features.request.RejectRequestController
import org.company.rado.features.request.UnconfirmedRequestController
import org.company.rado.features.users.UserController
import org.company.rado.features.vehicles.VehicleController
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureIntegration1CRouting(){
    routing {
        get(path = "/integration/repair/requests/drivers/all") {
            val usersController by closestDI().instance<UserController>()
            usersController.allDrivers(call=call)
        }

        get(path = "/integration/repair/requests/observers/all") {
            val usersController by closestDI().instance<UserController>()
            usersController.allObservers(call=call)
        }

        get(path = "/integration/repair/requests/mechanics/all") {
            val usersController by closestDI().instance<UserController>()
            usersController.allMechanics(call=call)
        }

        get(path = "/integration/repair/requests/vehicles/all") {
            val vehiclesController by closestDI().instance<VehicleController>()
            vehiclesController.allVehicles(call=call)
        }

        get(path = "/integration/repair/requests/unconfirmed/all"){
            val requestController by closestDI().instance<UnconfirmedRequestController>()
            requestController.getAllUnconfirmedRequests(call=call)
        }

        get(path = "/integration/repair/requests/active/all"){
            val requestController by closestDI().instance<ActiveRequestController>()
            requestController.getAllActiveRequests(call=call)
        }

        get(path = "/integration/repair/requests/archive/all"){
            val requestController by closestDI().instance<ArchiveRequestController>()
            requestController.getAllArchiveRequests(call=call)
        }

        get(path = "/integration/repair/requests/reject/all"){
            val requestController by closestDI().instance<RejectRequestController>()
            requestController.getAllRejectRequests(call=call)
        }
    }
}