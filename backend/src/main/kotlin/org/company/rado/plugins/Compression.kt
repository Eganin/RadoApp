package org.company.rado.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*

fun Application.configureCompression() {
    install(Compression) {
        default()
        excludeContentType(ContentType.Video.Any)
        excludeContentType(ContentType.Image.Any)
    }
}