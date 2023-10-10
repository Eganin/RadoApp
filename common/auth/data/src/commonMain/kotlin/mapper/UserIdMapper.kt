package mapper

import models.UserIdItem
import models.UserIdResponse
import org.company.rado.core.MainRes
import other.Mapper

class UserIdMapper: Mapper<UserIdResponse,UserIdItem> {
    override fun map(source: UserIdResponse): UserIdItem {
        return if(source.userId != Int.MIN_VALUE){
            UserIdItem.Success(userId = source.userId)
        }else UserIdItem.Error(message = MainRes.string.user_is_not_register)
    }
}