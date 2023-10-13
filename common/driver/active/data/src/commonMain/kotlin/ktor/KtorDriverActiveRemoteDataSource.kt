package ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ktor.models.KtorActiveRequest
import ktor.models.KtorCreateRequest
import models.CreateRequestIdResponse
import models.SmallActiveRequestForDriverResponse

class KtorDriverActiveRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun createRequest(request: KtorCreateRequest): CreateRequestIdResponse {
        return httpClient.post {
            url {
                path("request/create")
                setBody(request)
            }
        }.body()
    }

    suspend fun fetchRequestsByDate(request: KtorActiveRequest) : List<SmallActiveRequestForDriverResponse>{
        return httpClient.post {
            url {
                path("request/active/driver")
                setBody(request)
            }
        }.body()
    }
}