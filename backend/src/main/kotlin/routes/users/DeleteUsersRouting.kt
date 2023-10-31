package routes.users

import features.users.UserController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureDeleteUserRouting(){
    routing {
        delete(path = "/users/delete") {
            val usersController by closestDI().instance<UserController>()
            usersController.deleteUser(call=call)
        }
    }
}