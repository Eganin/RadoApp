package dao.register.mechanic

import models.users.UserDTO
import org.jetbrains.exposed.sql.ResultRow
import utils.Mapper

interface MechanicDaoFacade {

    val mapper: Mapper<UserDTO, ResultRow>
    suspend fun createMechanic(mechanic: UserDTO): Int?
    suspend fun findByFullName(username: String): UserDTO?
    suspend fun findById(mechanicId: Int): UserDTO?
    suspend fun deleteMechanic(username: String): Boolean
}