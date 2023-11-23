package org.company.rado.plugins

import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.excludeContentType

fun Application.configureCompression() {
    install(Compression) {
        default()
        excludeContentType(ContentType.Video.Any)
        excludeContentType(ContentType.Image.Any)
    }
}