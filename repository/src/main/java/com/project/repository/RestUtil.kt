package com.project.repository

import com.project.repository.security.authToken
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object RestUtil {
    val token = authToken

    const val BASE_URL: String = ""
    fun getClient(): HttpClient {
        return HttpClient() {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                    },
                )
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens("$token", "")
                    }
                }
            }
        }
    }
}
