import io.github.aakira.napier.log
import ktor.KtorAuthRemoteDataSource
import ktor.models.KtorRegisterOrLoginRequest
import models.LoginInfoItem
import models.LoginInfoResponse
import models.UserIdItem
import models.UserIdResponse
import org.company.rado.core.MainRes
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
                    userId = userIdItem.userId,
                    position = position,
                    fullName = fullName,
                    phone = phone
                )
                localDataSource.saveUserId(userId = userIdItem.userId)
            }
            userIdItem
        } catch (e: Exception) {
            UserIdItem.Error(message = MainRes.string.sign_in_error)
        }
        return userIdItem
    }

    override suspend fun login(position: String, fullName: String, phone: String): LoginInfoItem {
        val loginInfoItem = try {
            val response = remoteDataSource.performLogin(
                request = KtorRegisterOrLoginRequest(
                    position = position,
                    fullName = fullName,
                    phone = phone
                )
            )
            localDataSource.saveLoginUserInfo(
                userId = response.userId,
                position = response.position,
                fullName = response.fullName,
                phone = response.phone
            )
            loginInfoMapper.map(source = response)
        } catch (e: Exception) {
            LoginInfoItem.Error(message = MainRes.string.sign_in_error)
        }

        return loginInfoItem
    }

    override suspend fun isUserLoggedIn(): LoginInfoItem {
        val loginInfoItem = try {
            val userInfoFromLocal = localDataSource.fetchLoginUserInfo()
            log(tag = TAG) { userInfoFromLocal.toString() }
            val response = remoteDataSource.performLogin(
                request = KtorRegisterOrLoginRequest(
                    position = userInfoFromLocal.position,
                    fullName = userInfoFromLocal.fullName,
                    phone = userInfoFromLocal.phone
                )
            )
            loginInfoMapper.map(source = response)
        } catch (e: Exception) {
            LoginInfoItem.Error(message = MainRes.string.base_error_message)
        }
        return loginInfoItem
    }

    private companion object {
        const val TAG = "AuthRepository"
    }
}