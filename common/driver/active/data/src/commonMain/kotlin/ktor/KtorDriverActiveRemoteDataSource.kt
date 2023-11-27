package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.path
import ktor.models.KtorActiveRequest
import ktor.models.KtorCreateRequest
import models.CreateRequestIdResponse
import models.SmallActiveRequestForDriverResponse

internal class KtorDriverActiveRemoteDataSource(
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

    suspend fun deleteRequest(requestId: Int): HttpStatusCode {
        return httpClient.delete {
            url {
                path("requests/delete/${requestId}")
            }
        }.status
    }

    suspend fun fetchRequestsByDate(request: KtorActiveRequest): List<SmallActiveRequestForDriverResponse> {
        return httpClient.post {
            url {
                path("request/active/driver")
                setBody(request)
            }
        }.body()
    }

    suspend fun uploadImageOrVideoForRequest(
        requestId: Int,
        isImage: Boolean,
        resourcePath: String,
        resourceData: ByteArray
    ): HttpStatusCode {
        return httpClient.post {
            url {
                if (isImage) {
                    path("images/create")
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                append("description", requestId)
                                append(
                                    "image",
                                    resourceData,
                                    Headers.build {
                                        append(HttpHeaders.ContentType, "image/png")
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=\"${resourcePath}\""
                                        )
                                    })
                            },
                            boundary = "WebAppBoundary"
                        )
                    )
                } else {
                    path("videos/create")
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                append("description", requestId)
                                append(
                                    "video",
                                    resourceData,
                                    Headers.build {
                                        append(HttpHeaders.ContentType, "video/mp4")
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=\"${resourcePath}\""
                                        )
                                    })
                            },
                            boundary = "WebAppBoundary"
                        )
                    )
                }
            }
        }.status
    }

    suspend fun uploadImageOrVideoForCache(
        isImage: Boolean,
        resourcePath: String,
        resourceData: ByteArray
    ): HttpStatusCode {
        return httpClient.post {
            url {
                path("resources/create")
                if (isImage) {
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                append(
                                    "image",
                                    resourceData,
                                    Headers.build {
                                        append(HttpHeaders.ContentType, "image/png")
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=\"${resourcePath}\""
                                        )
                                    })
                            },
                            boundary = "WebAppBoundary"
                        )
                    )
                } else {
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                append(
                                    "video",
                                    resourceData,
                                    Headers.build {
                                        append(HttpHeaders.ContentType, "video/mp4")
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=\"${resourcePath}\""
                                        )
                                    })
                            },
                            boundary = "WebAppBoundary"
                        )
                    )
                }
            }
        }.status
    }

    suspend fun deleteResourceCache(resourceName: String): HttpStatusCode {
        return httpClient.delete {
            url {
                path("resources/delete/${resourceName}")
            }
        }.status
    }

    suspend fun deleteImagesFromRequest(requestId: Int): HttpStatusCode{
        return httpClient.delete {
            url{
                path("images/delete/$requestId")
            }
        }.status
    }

    suspend fun deleteVideosFromRequest(requestId: Int): HttpStatusCode{
        return httpClient.delete {
            url{
                path("videos/delete/$requestId")
            }
        }.status
    }

    suspend fun deleteImageAndVideoByPath(isImage:Boolean,resourcePath:String):HttpStatusCode{
        return httpClient.delete {
            url {
                if (isImage){
                    path("images/bypath/$resourcePath")
                }else{
                    path("videos/bypath/$resourcePath")
                }
            }
        }.status
    }
}