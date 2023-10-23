package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import models.FullUnconfirmedRequestResponse

class KtorSharedRequestsRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun fetchUnconfirmedRequestInfo(requestId:Int):FullUnconfirmedRequestResponse{
        return httpClient.get {
            url {
                path("/request/unconfirmed/info/${requestId}")
            }
        }.body()
    }
}