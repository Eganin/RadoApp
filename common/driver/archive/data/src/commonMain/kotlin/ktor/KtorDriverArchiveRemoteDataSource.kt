package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import models.SmallArchiveRequestForDriverResponse

internal class KtorDriverArchiveRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun fetchArchiveRequests(driverId:Int):List<SmallArchiveRequestForDriverResponse>{
        return httpClient.get {
            url {
                path("request/archive/driver/$driverId")
            }
        }.body()
    }
}