package mapper

import models.UserIdItem
import models.UserIdResponse
import other.Mapper

class UserIdMapper: Mapper<UserIdResponse,UserIdItem> {
    override fun map(source: UserIdResponse): UserIdItem {
        return if(source.userId != Int.MIN_VALUE){
            UserIdItem.Success(userId = source.userId)
        }else UserIdItem.Error(message = "Пользователь не зарегистрирован")
    }
}