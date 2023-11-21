package org.company.rado.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.partialcontent.PartialContent

fun Application.configurePartialContent(){
    install(PartialContent)
}