package dao.register.observer

import dao.DatabaseFactory.dbQuery
import models.users.Observers
import models.users.UserDTO
import models.users.UserDTOMapperForObserver
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import utils.Mapper

class ObserverDaoImpl(override val mapper: Mapper<UserDTO, ResultRow> = UserDTOMapperForObserver()) :
    ObserverDaoFacade {
    override suspend fun createObserver(observer: UserDTO): Int? = dbQuery {
        val insertStatement = Observers.insert {
            it[fullName] = observer.fullName
            it[phone] = observer.phone
        }

        insertStatement.resultedValues?.map { it[Observers.id] }?.singleOrNull()
    }

    override suspend fun findByFullName(username: String): UserDTO? = dbQuery {
        Observers
            .select { Observers.fullName eq username }.map { mapper.map(source = it) }.singleOrNull()
    }

    override suspend fun deleteObserver(username: String): Boolean = dbQuery {
        Observers.deleteWhere { fullName eq username } !=0
    }
}