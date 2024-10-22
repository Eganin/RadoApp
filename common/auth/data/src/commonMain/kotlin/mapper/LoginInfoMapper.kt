package mapper

import models.LoginInfoItem
import models.LoginInfoResponse
import org.company.rado.core.MainRes
import other.Mapper

class LoginInfoMapper : Mapper<LoginInfoResponse, LoginInfoItem> {
    override fun map(source: LoginInfoResponse): LoginInfoItem {
        return if (!listOf(source.fullName, source.phone, source.position).any { it.isEmpty() }) {
            LoginInfoItem.Success(
                position = source.position,
                fullName = source.fullName,
                phone = source.phone,
                userId = source.userId
            )
        } else LoginInfoItem.Error(message = MainRes.string.user_is_not_login)
    }
}