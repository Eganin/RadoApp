import models.LoginInfoItem
import models.UserIdItem
import models.UserIdResponse

interface AuthRepository {

    suspend fun register(position: String,fullName:String,phone:String): UserIdItem

    suspend fun isUserLoggedIn(): LoginInfoItem
}