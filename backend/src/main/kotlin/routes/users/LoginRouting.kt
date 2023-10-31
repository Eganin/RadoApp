package routes.users

import features.login.LoginController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureLoginRouting() {
    routing {
        post(path = "/login") {
            val loginController by closestDI().instance<LoginController>()
            loginController.loginUser(call = call)
        }
    }
}