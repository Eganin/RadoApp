package org.company.rado.dao.register.driver

import org.company.rado.models.users.UserDTO
import org.jetbrains.exposed.sql.ResultRow
import org.company.rado.utils.Mapper

interface DriverDaoFacade {

    val mapper: Mapper<UserDTO, ResultRow>
    suspend fun createDriver(driver: UserDTO):Int?
    suspend fun findByFullName(username:String): UserDTO?
    suspend fun fundById(driverId: Int): UserDTO?
    suspend fun deleteDriver(username: String):Boolean
}