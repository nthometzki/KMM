package com.thkoeln.kmm_project

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.observer.*
import io.ktor.network.tls.*

suspend fun networking(url: String) {
    println("KTOR STARTS")
    val httpClient: HttpClient = HttpClient {
        expectSuccess = false
        io.ktor.client.features.observer.ResponseObserver { response ->
            println("HTTP status: ${response.status.value}")
        }
    }

    val htmlContent = httpClient.request<String> {
        url(url)
        method = HttpMethod.Get
    }
    println("HTML OUTPUT: ${htmlContent})")
    httpClient.close()
}