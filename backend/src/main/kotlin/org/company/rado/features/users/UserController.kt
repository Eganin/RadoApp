package org.company.rado.features.users

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.company.rado.models.users.Position
import org.company.rado.models.users.UserInfoRemote
import org.company.rado.services.UsersService

class UserController(
    private val usersService: UsersService
) {
    suspend fun deleteUser(call: ApplicationCall) {
        val userInfoRemote = call.receive<UserInfoRemote>()
        val response = when (userInfoRemote.position) {
            Position.DRIVER.position -> usersService.removeDriver(fullName = userInfoRemote.fullName)
            Position.MECHANIC.position -> usersService.removeMechanic(fullName = userInfoRemote.fullName)
            Position.OBSERVER.position -> usersService.removeObserver(fullName = userInfoRemote.fullName)
            else->false
        }
        if (response){
            call.respond(HttpStatusCode.OK, message = "The user has been deleted")
        }else{
            call.respond(HttpStatusCode.BadRequest, message = "The user has not been deleted")
        }
    }
}