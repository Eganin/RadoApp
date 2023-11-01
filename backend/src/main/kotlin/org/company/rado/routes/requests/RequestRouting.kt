package org.company.rado.routes.requests

import org.company.rado.features.request.RequestController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureRequestRouting() {
    routing {
        post(path = "/request/create") {
            val requestController by closestDI().instance<RequestController>()
            requestController.createRequest(call = call)
        }
        put(path = "/request/recreate/{requestId}") {
            val requestId = call.parameters["requestId"]!!.toInt()
            val requestController by closestDI().instance<RequestController>()
            requestController.recreateRequest(call = call,requestId=requestId)
        }

        delete(path = "/request/delete") { }
    }
}