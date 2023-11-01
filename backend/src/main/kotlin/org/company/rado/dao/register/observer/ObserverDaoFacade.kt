package org.company.rado.dao.register.observer

import org.company.rado.models.users.UserDTO
import org.jetbrains.exposed.sql.ResultRow
import org.company.rado.utils.Mapper

interface ObserverDaoFacade {

    val mapper: Mapper<UserDTO, ResultRow>
    suspend fun createObserver(observer: UserDTO):Int?
    suspend fun findByFullName(username:String): UserDTO?
    suspend fun deleteObserver(username: String): Boolean
}