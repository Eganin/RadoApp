package org.company.rado.plugins

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import java.sql.SQLException

data class ExceptionTransform(
    val message: String?,
    val code: Int
)

fun transformException(message: String?, code: Int): String {
    val gson = Gson()
    val exception = ExceptionTransform(message, code)
    return gson.toJson(exception)
}

fun Application.configureException() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->

            val log = call.application.environment.log
            val applicationJson = ContentType("application", "json")

            when (cause) {
                is AssertionError -> {
                    log.info(cause.message)
                    call.respondText(
                        text = transformException(cause.message, HttpStatusCode.BadRequest.value),
                        status = HttpStatusCode.BadRequest,
                        contentType = applicationJson
                    )
                }

                is SQLException -> {
                    log.info(cause.message)
                    call.respondText(
                        text = transformException(
                            message = "duplicate key value violates unique constraint",
                            code = HttpStatusCode.BadRequest.value
                        ),
                        status = HttpStatusCode.Forbidden,
                        contentType = applicationJson
                    )
                }

                is NotFoundException -> {
                    log.info(cause.message)
                    call.respondText(
                        text = transformException(cause.message, HttpStatusCode.NotFound.value),
                        status = HttpStatusCode.NotFound,
                        contentType = applicationJson
                    )
                }

                is IllegalArgumentException ->{
                    log.info(cause.message)
                    call.respondText(
                        text = transformException(cause.message, HttpStatusCode.InternalServerError.value),
                        status = HttpStatusCode.InternalServerError,
                        contentType = applicationJson
                    )
                }

                is Exception -> {
                    log.error(cause.message)
                    call.respondText(
                        text = transformException(cause.message, HttpStatusCode.InternalServerError.value),
                        status = HttpStatusCode.InternalServerError,
                        contentType = applicationJson
                    )
                }
            }
        }
    }
}