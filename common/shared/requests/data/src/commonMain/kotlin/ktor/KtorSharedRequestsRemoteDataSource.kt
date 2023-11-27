package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.path
import ktor.models.RecreateRequest
import models.FullRequestResponse
import models.FullUnconfirmedRequestResponse
import models.RecreateRequestResponse
import models.SmallUnconfirmedRequestResponse

class KtorSharedRequestsRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun fetchUnconfirmedRequestInfo(requestId: Int): FullUnconfirmedRequestResponse {
        return httpClient.get {
            url {
                path("request/unconfirmed/info/${requestId}")
            }
        }.body()
    }

    suspend fun fetchUnconfirmedRequestsForDriver(driverId: Int): List<SmallUnconfirmedRequestResponse> {
        return httpClient.get {
            url {
                path("request/unconfirmed/driver/${driverId}")
            }
        }.body()
    }

    suspend fun fetchUnconfirmedRequestsForMechanic(): List<SmallUnconfirmedRequestResponse> {
        return httpClient.get {
            url {
                path("request/unconfirmed/mechanic")
            }
        }.body()
    }

    suspend fun fetchActiveRequestInfo(requestId: Int): FullRequestResponse {
        return httpClient.get {
            url {
                path("request/active/info/$requestId")
            }
        }.body()
    }

    suspend fun updateRecreateRequest(
        requestId: Int,
        driverId: Int,
        typeVehicle: String,
        numberVehicle: String,
        oldTypeVehicle: String,
        oldNumberVehicle: String,
        faultDescription: String,
        arrivalDate:String
    ): RecreateRequestResponse {
        val request = RecreateRequest(
            driverId = driverId,
            typeVehicle = typeVehicle,
            numberVehicle = numberVehicle,
            oldTypeVehicle = oldTypeVehicle,
            oldNumberVehicle = oldNumberVehicle,
            faultDescription = faultDescription,
            arrivalDate=arrivalDate
        )
        return httpClient.put {
            url {
                path("request/recreate/$requestId")
                setBody(request)
            }
        }.body()
    }
}