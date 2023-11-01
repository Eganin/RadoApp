package org.company.rado.utils

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.company.rado.dao.DatabaseFactory.dbQuery
import org.company.rado.models.test.Test
import org.jetbrains.exposed.sql.insert

fun Application.configureTestRouting() {
    routing {
        get(path = "/") {
            call.respondText("Test Routing Alpha Update")
        }

        get(path = "/test") {
            val answer =dbQuery{
                val insertStatement = Test.insert {
                    it[Test.testText] = "ZA WARUDO"
                }
                insertStatement.resultedValues?.map { it[Test.testText] }?.singleOrNull()
            }
            call.respondText(text = answer ?: "NO ZA WARUDO")
        }
    }
}