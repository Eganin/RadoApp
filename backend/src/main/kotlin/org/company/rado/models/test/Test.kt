package org.company.rado.models.test

import org.jetbrains.exposed.sql.Table

object Test : Table(name="test_table") {
    val id = integer(name = "id").autoIncrement()
    val testText = varchar(name = "test_text", length = 100)
}