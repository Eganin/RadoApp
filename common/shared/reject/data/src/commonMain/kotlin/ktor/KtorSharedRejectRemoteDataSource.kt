package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import models.SmallRejectRequestResponse

internal class KtorSharedRejectRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getRejectRequestsForObserver():List<SmallRejectRequestResponse>{
        return httpClient.get {
            url {
                path("request/reject/observer")
            }
        }.body()
    }

    suspend fun getRejectRequestsForDriver(driverId:Int):List<SmallRejectRequestResponse>{
        return httpClient.get {
            url {
                path("request/reject/driver/$driverId")
            }
        }.body()
    }
}