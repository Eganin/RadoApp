package ru.rado

import dao.di.bindRepositories
import features.di.bindControllers
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.Database
import org.kodein.di.ktor.di
import plugins.configureRouting
import plugins.configureSerialization
import routes.images.configureImageRouting
import services.di.bindServices
import java.io.File
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testImage() = testApplication {

        application {
            Database.connect(
                url = "jdbc:postgresql://localhost:5432/rado",
                driver = "org.postgresql.Driver",
                password = "Zaharin0479",
                user = "postgres"
            )
            configureSerialization()
            di {
                bindRepositories()
                bindServices()
                bindControllers()
            }
            configureRouting{
                configureImageRouting()
            }
        }

        val response: HttpResponse = client.post("/images/create") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("description", 9)
                        append(
                            "image",
                            File("test.png").readBytes(),
                            Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=\"test.png\"")
                            })
                        append(
                            "image",
                            File("test2.png").readBytes(),
                            Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=\"test2.png\"")
                            })
                    },
                    boundary = "WebAppBoundary"
                )
            )
//            onUpload { bytesSentTotal, contentLength ->
//                println("Sent $bytesSentTotal bytes from $contentLength")
//            }
        }
        assertEquals(HttpStatusCode.OK,response.status)
    }
}
