package org.company.rado

import org.company.rado.dao.di.bindRepositories
import org.company.rado.features.di.bindControllers
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import org.kodein.di.ktor.di
import org.company.rado.plugins.configureCompression
import org.company.rado.plugins.configureCors
import org.company.rado.plugins.configureException
import org.company.rado.plugins.configureRouting
import org.company.rado.plugins.configureSerialization
import org.company.rado.routes.requests.configureActiveRequestRouting
import org.company.rado.routes.requests.configureArchiveRequestRouting
import org.company.rado.routes.requests.configureDeleteRequestRouting
import org.company.rado.routes.requests.configureRejectedRequestRouting
import org.company.rado.routes.requests.configureRequestRouting
import org.company.rado.routes.requests.configureUnconfirmedRequestRouting
import org.company.rado.routes.resources.configureResourcesRouting
import org.company.rado.routes.users.configureDeleteUserRouting
import org.company.rado.routes.users.configureLoginRouting
import org.company.rado.routes.users.configureRegisterRouting
import org.company.rado.routes.vehicles.configureDeleteVehiclesRouting
import org.company.rado.services.di.bindServices
import org.company.rado.utils.configureTestRouting

fun main() {
    Database.connect(
        url = System.getenv("DATABASE_CONNECTION_STRING"),
        driver = "org.postgresql.Driver",
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD")
    )

    embeddedServer(
        Netty,
        port = System.getenv("SERVER_PORT").toInt(),
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureCompression()
    configureSerialization()
    configureException()
    configureCors()
    di {
        bindRepositories()
        bindServices()
        bindControllers()
    }
    configureRouting {
        configureTestRouting()
        configureRegisterRouting()
        configureLoginRouting()
        configureDeleteUserRouting()
        configureRequestRouting()
        configureUnconfirmedRequestRouting()
        configureActiveRequestRouting()
        configureArchiveRequestRouting()
        configureRejectedRequestRouting()
        configureDeleteVehiclesRouting()
        configureDeleteRequestRouting()
        configureResourcesRouting()
    }
}