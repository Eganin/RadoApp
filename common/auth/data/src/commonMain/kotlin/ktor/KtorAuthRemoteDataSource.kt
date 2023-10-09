package ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import ktor.models.KtorRegisterOrLoginRequest
import models.LoginResponse
import models.UserIdResponse

class KtorAuthRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun performRegister(request: KtorRegisterOrLoginRequest): UserIdResponse {
        return httpClient.post {
            url {
                path("register")
                setBody(request)
            }
        }.body()
    }

    suspend fun performLogin(request: KtorRegisterOrLoginRequest): LoginResponse {
        return httpClient.post {
            url {
                path("login")
                setBody(request)
            }
        }.body()
    }
}