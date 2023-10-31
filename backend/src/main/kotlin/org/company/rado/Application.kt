package ru.rado

import dao.di.bindRepositories
import features.di.bindControllers
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.kodein.di.ktor.di
import plugins.*
import routes.requests.*
import routes.resources.configureResourcesRouting
import routes.users.configureDeleteUserRouting
import routes.users.configureLoginRouting
import routes.users.configureRegisterRouting
import routes.vehicles.configureDeleteVehiclesRouting
import services.di.bindServices
import utils.configureTestRouting

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

//    Database.connect(
//        url = "jdbc:postgresql://db:5432/rado",
//        driver = "org.postgresql.Driver",
//        user = "postgres",
//        password = "Zaharin0479"
//    )
//
//    embeddedServer(
//        Netty,
//        port = 8080,
//        module = Application::module
//    ).start(wait = true)
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
