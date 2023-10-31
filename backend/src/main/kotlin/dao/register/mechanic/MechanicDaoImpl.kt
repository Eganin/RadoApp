package dao.register.mechanic

import dao.DatabaseFactory.dbQuery
import models.users.Mechanics
import models.users.UserDTO
import models.users.UserDTOMapperForMechanic
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import utils.Mapper

class MechanicDaoImpl(override val mapper: Mapper<UserDTO, ResultRow> = UserDTOMapperForMechanic()) :
    MechanicDaoFacade {
    override suspend fun createMechanic(mechanic: UserDTO): Int? = dbQuery {
        val insertStatement = Mechanics.insert {
            it[fullName] = mechanic.fullName
            it[phone] = mechanic.phone
        }

        insertStatement.resultedValues?.map { it[Mechanics.id] }?.singleOrNull()
    }

    override suspend fun findByFullName(username: String): UserDTO? = dbQuery {
        Mechanics
            .select { Mechanics.fullName eq username }.map { mapper.map(source = it) }.singleOrNull()
    }

    override suspend fun findById(mechanicId: Int): UserDTO? = dbQuery {
        Mechanics
            .select { Mechanics.id eq mechanicId }.map { mapper.map(source = it) }.singleOrNull()
    }

    override suspend fun deleteMechanic(username: String): Boolean = dbQuery {
        Mechanics.deleteWhere { fullName eq username } != 0
    }
}