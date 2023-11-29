package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path
import ktor.models.KtorActiveRequestForObserver
import models.SmallActiveRequestForObserverResponse

internal class KtorObserverActiveRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun fetchActiveRequestsByDate(request: KtorActiveRequestForObserver): List<SmallActiveRequestForObserverResponse> {
        return httpClient.post {
            url {
                path("request/active/observer")
                setBody(request)
            }
        }.body()
    }
}