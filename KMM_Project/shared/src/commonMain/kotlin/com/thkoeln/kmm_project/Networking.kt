package com.thkoeln.kmm_project

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

suspend fun networking(url: String): String {
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
    //println("HTML OUTPUT: ${htmlContent})")
    httpClient.close()

    return htmlContent
}