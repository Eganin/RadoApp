import models.LoginInfoItem
import models.UserIdItem

interface AuthRepository {

    suspend fun register(position: String, fullName: String, phone: String): UserIdItem

    suspend fun login(position: String, fullName: String, phone: String): LoginInfoItem

    suspend fun isUserLoggedIn(): LoginInfoItem
}