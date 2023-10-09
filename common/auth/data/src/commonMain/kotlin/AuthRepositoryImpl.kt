import ktor.KtorAuthRemoteDataSource
import ktor.models.KtorRegisterOrLoginRequest
import models.LoginInfoItem
import models.UserIdItem
import models.UserIdResponse
import other.Mapper
import settings.SettingsAuthDataSource

class AuthRepositoryImpl(
    private val remoteDataSource: KtorAuthRemoteDataSource,
    private val localDataSource: SettingsAuthDataSource,
    private val userIdMapper: Mapper<UserIdResponse, UserIdItem>,
) : AuthRepository {
    override suspend fun register(position: String, fullName: String, phone: String): UserIdItem {
        val userIdItem =try {
            val response = remoteDataSource.performRegister(
                request = KtorRegisterOrLoginRequest(
                    position = position,
                    fullName = fullName,
                    phone = phone
                )
            )

            localDataSource.saveLoginUserInfo(
                position = position,
                fullName = fullName,
                phone = phone
            )
            userIdMapper.map(source = response)
        }catch (e: Exception){
            UserIdItem.Error(message = "Произошла ошибка")
        }
        return userIdItem
    }

    override suspend fun isUserLoggedIn(): LoginInfoItem {
        TODO("Not yet implemented")
    }
}