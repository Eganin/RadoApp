import ktor.KtorAuthRemoteDataSource
import ktor.models.KtorRegisterOrLoginRequest
import models.LoginInfoItem
import models.LoginInfoResponse
import models.UserIdItem
import models.UserIdResponse
import other.Mapper
import settings.SettingsAuthDataSource

class AuthRepositoryImpl(
    private val remoteDataSource: KtorAuthRemoteDataSource,
    private val localDataSource: SettingsAuthDataSource,
    private val userIdMapper: Mapper<UserIdResponse, UserIdItem>,
    private val loginInfoMapper: Mapper<LoginInfoResponse, LoginInfoItem>
) : AuthRepository {
    override suspend fun register(position: String, fullName: String, phone: String): UserIdItem {
        val userIdItem = try {
            val response = remoteDataSource.performRegister(
                request = KtorRegisterOrLoginRequest(
                    position = position,
                    fullName = fullName,
                    phone = phone
                )
            )
            val userIdItem = userIdMapper.map(source = response)
            if (userIdItem is UserIdItem.Success) {
                localDataSource.saveLoginUserInfo(
                    position = position,
                    fullName = fullName,
                    phone = phone
                )
                localDataSource.saveUserId(userId = userIdItem.userId)
            }
            userIdItem
        } catch (e: Exception) {
            UserIdItem.Error(message = "Произошла ошибка")
        }
        return userIdItem
    }

    override suspend fun isUserLoggedIn(): LoginInfoItem {
        val loginInfoItem = try {
            val userInfoFromLocal = localDataSource.fetchLoginUserInfo()
            val response = remoteDataSource.performLogin(
                request = KtorRegisterOrLoginRequest(
                    position = userInfoFromLocal.position,
                    fullName = userInfoFromLocal.fullName,
                    phone = userInfoFromLocal.phone
                )
            )
            loginInfoMapper.map(source = response)
        } catch (e: Exception) {
            LoginInfoItem.Error(message = "Произошла ошибка")
        }
        return loginInfoItem
    }
}