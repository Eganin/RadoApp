package ktor

import di.Inject
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

internal val ktorModule = DI.Module(name = "ktorModule") {
    bind<HttpClient>() with singleton {
        HttpClient(engineFactory = HttpEngineFactory().createEngine()) {
            install(Logging){
                logger=Logger.SIMPLE
                level=LogLevel.ALL
            }

            install(DefaultRequest)

            install(ContentNegotiation){
                json(Inject.instance())
            }

            install(HttpTimeout){
                connectTimeoutMillis = 15000
                requestTimeoutMillis = 30000
            }

            defaultRequest {
                url("https://eganinrado.serveo.net")
                header("Content-Type", "application/json; charset=UTF-8")
            }
        }
    }
}