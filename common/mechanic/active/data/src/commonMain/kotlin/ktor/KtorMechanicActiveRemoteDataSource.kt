package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.path
import ktor.models.KtorActiveRequestForMechanic
import models.SmallActiveRequestForMechanic

internal class KtorMechanicActiveRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun fetchRequestsByDate(request: KtorActiveRequestForMechanic): List<SmallActiveRequestForMechanic> {
        return httpClient.post {
            url {
                path("request/active/mechanic")
                setBody(request)
            }
        }.body()
    }

    suspend fun archieveRequest(requestId: Int): HttpStatusCode {
        return httpClient.put {
            url {
                path("request/archieve/$requestId")
            }
        }.status
    }
}