package dao.register.observer

import models.users.UserDTO
import org.jetbrains.exposed.sql.ResultRow
import utils.Mapper

interface ObserverDaoFacade {

    val mapper: Mapper<UserDTO, ResultRow>
    suspend fun createObserver(observer: UserDTO):Int?
    suspend fun findByFullName(username:String): UserDTO?
    suspend fun deleteObserver(username: String): Boolean
}