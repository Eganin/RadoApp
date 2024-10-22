package org.company.rado.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.company.rado.models.users.Position
import org.company.rado.models.users.UserInfoRemote
import org.company.rado.services.UsersService

class LoginController(
    private val usersService: UsersService
) {
    suspend fun loginUser(call: ApplicationCall) {
        val userInfoRemote = call.receive<UserInfoRemote>()
        val response = when (userInfoRemote.position) {
            Position.DRIVER.position -> usersService.loginDriver(fullName = userInfoRemote.fullName)
            Position.MECHANIC.position -> usersService.loginMechanic(fullName = userInfoRemote.fullName)
            Position.OBSERVER.position -> usersService.loginObserver(fullName = userInfoRemote.fullName)
            else -> null
        }
        if (response != null) {
            call.respond(response)
        } else {
            call.respond(HttpStatusCode.Unauthorized, message = "User Unauthorized")
        }
    }
}