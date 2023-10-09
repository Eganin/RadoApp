import models.UserIdResponse

interface AuthRepository {

    suspend fun register(position: String,fullName:String,phone:String): UserIdResponse

    suspend fun isUserLoggedIn()
}