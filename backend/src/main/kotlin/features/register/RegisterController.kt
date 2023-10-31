package features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import models.users.Position
import models.users.UserIdResponse
import models.users.UserInfoRemote
import services.UsersService

class RegisterController(
    private val usersService: UsersService
) {

    suspend fun registerNewUser(call: ApplicationCall) {
        val userInfoRemote = call.receive<UserInfoRemote>()
        val response = when (userInfoRemote.position) {
            Position.DRIVER.position ->
                usersService.createDriver(
                    fullName = userInfoRemote.fullName,
                    phone = userInfoRemote.phone
                )

            Position.MECHANIC.position ->
                usersService.createMechanic(
                    fullName = userInfoRemote.fullName,
                    phone = userInfoRemote.phone
                )

            Position.OBSERVER.position ->
                usersService.createObserver(
                    fullName = userInfoRemote.fullName,
                    phone = userInfoRemote.phone
                )

            else -> UserIdResponse(userId = Int.MIN_VALUE)
        }
        if (response.userId != Int.MIN_VALUE) {
            call.respond(response)
        } else {
            call.respond(HttpStatusCode.Unauthorized, message = "User Unauthorized")
        }
    }
}