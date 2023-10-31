package dao.register.driver

import dao.DatabaseFactory.dbQuery
import models.users.Drivers
import models.users.UserDTO
import models.users.UserDTOMapperForDriver
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import utils.Mapper

class DriverDaoImpl(override val mapper: Mapper<UserDTO, ResultRow> = UserDTOMapperForDriver()) :
    DriverDaoFacade {
    override suspend fun createDriver(driver: UserDTO): Int? = dbQuery {
        val insertStatement = Drivers.insert {
            it[fullName] = driver.fullName
            it[phone] = driver.phone
        }
        insertStatement.resultedValues?.map { it[Drivers.id] }?.singleOrNull()
    }

    override suspend fun findByFullName(username: String): UserDTO? = dbQuery {
        Drivers
            .select { Drivers.fullName eq username }.map { mapper.map(source = it) }.singleOrNull()
    }

    override suspend fun fundById(driverId: Int): UserDTO? = dbQuery {
        Drivers
            .select { Drivers.id eq driverId }.map { mapper.map(source = it) }.singleOrNull()
    }

    override suspend fun deleteDriver(username: String): Boolean = dbQuery {
        Drivers.deleteWhere { fullName eq username } !=0
    }
}
