package models.users

import org.jetbrains.exposed.sql.Table

object Observers : Table(name = "observers") {
    val id = integer(name = "id").autoIncrement()
    val fullName = varchar(name = "full_name", length = 100).uniqueIndex()
    val phone = varchar(name = "phone", length = 12).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}