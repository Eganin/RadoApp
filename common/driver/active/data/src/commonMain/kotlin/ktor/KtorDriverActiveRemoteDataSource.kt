package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.path
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

    suspend fun fetchRequestsByDate(request: KtorActiveRequest): List<SmallActiveRequestForDriverResponse> {
        return httpClient.post {
            url {
                path("request/active/driver")
                setBody(request)
            }
        }.body()
    }

    suspend fun uploadResourceImage(image: Pair<String, ByteArray>) {
        httpClient.post {
            url {
                path("resources/create")
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                "image",
                                image.second,
                                Headers.build {
                                    append(HttpHeaders.ContentType, "image/png")
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=\"${image.first}\""
                                    )
                                })
                        },
                        boundary = "WebAppBoundary"
                    )
                )
            }
        }
    }
}