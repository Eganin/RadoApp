package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import models.SmallArchiveRequestForObserverResponse

internal class KtorObserverArchiveRemoteDataSource(
    private val httpClient: HttpClient
){

    suspend fun fetchArchiveRequests():List<SmallArchiveRequestForObserverResponse>{
        return httpClient.get {
            url {
                path("request/archive/observer")
            }
        }.body()
    }
}