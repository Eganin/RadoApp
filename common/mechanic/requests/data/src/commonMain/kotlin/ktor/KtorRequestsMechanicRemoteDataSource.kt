package ktor

import io.ktor.client.HttpClient
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.path
import ktor.models.ConfirmationRequestRemote
import ktor.models.RejectRequestRemote

class KtorRequestsMechanicRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun confirmationRequest(request: ConfirmationRequestRemote):HttpStatusCode{
        return httpClient.put {
            url{
                path("request/confirmation")
                setBody(request)
            }
        }.status
    }

    suspend fun rejectRequest(request: RejectRequestRemote): HttpStatusCode{
        return httpClient.put{
            url{
                path("request/reject")
                setBody(request)
            }
        }.status
    }
}