package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import models.SmallArchiveRequestForMechanic

internal class KtorMechanicArchiveRemoteDataSource(
    private val httpClient: HttpClient
){

    suspend fun fetchArchiveRequests(mechanicId:Int):List<SmallArchiveRequestForMechanic>{
        return httpClient.get {
            url {
                path("request/archive/mechanic/$mechanicId")
            }
        }.body()
    }
}