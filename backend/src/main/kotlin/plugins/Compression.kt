package plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*

fun Application.configureCompression() {
    install(Compression) {
        gzip {
            matchContentType(
                ContentType.Video.Any,
                ContentType.Image.Any
            )
        }

    }
}