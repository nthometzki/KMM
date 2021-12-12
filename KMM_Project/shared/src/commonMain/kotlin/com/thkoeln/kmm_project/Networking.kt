package com.thkoeln.kmm_project

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Networking() {
    val client = HttpClient()

    suspend fun makeRequest(url: String): HttpResponse {
        val response: HttpResponse = client.get(url)
        println("Response${response}")
        return response
    }
}