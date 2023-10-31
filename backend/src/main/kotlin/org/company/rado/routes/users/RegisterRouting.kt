package org.company.rado.routes.users

import org.company.rado.features.register.RegisterController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureRegisterRouting() {
    routing {
        post(path = "/register") {
            val registerController by closestDI().instance<RegisterController>()
            registerController.registerNewUser(call=call)
        }
    }
}