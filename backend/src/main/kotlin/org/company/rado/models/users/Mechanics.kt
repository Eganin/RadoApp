package org.company.rado.models.users

import org.jetbrains.exposed.sql.Table

object Mechanics : Table(name = "mechanics") {
    val id = integer(name = "id").autoIncrement()
    val fullName = varchar(name = "full_name", length = 100).uniqueIndex()
    val phone = varchar(name = "phone", length = 12).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}