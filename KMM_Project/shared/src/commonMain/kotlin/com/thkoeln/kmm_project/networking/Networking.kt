package com.thkoeln.kmm_project.networking

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Networking() {

    @Serializable
    data class Data(val id: String, val text: String, val account_id: String, val timestamp: String, val username: String)

    suspend fun networking(url: String): String {
        println("KTOR STARTS")
        val httpClient = HttpClient {
            expectSuccess = false
            io.ktor.client.features.observer.ResponseObserver { response ->
                println("HTTP status: ${response.status.value}")
            }
        }

        val htmlContent = httpClient.request<String> {
            url(url)
            method = HttpMethod.Get
        }

        httpClient.close()

        return htmlContent
    }

    suspend fun getPosts(): Array<Data> {
        val response = Networking().networking("http://thometzki.de/pp/?getPosts=true")

        // Serialization - Plugin also needed in build gradle
        val obj = Json.decodeFromString<Array<Data>>(response)
        return obj
    }

    suspend fun submitPost(googleid: String, text: String) {
        Networking().networking("http://thometzki.de/pp/?submitPost=true&googleid=$googleid&post=$text")
    }

    suspend fun submitComment(googleid: String, postid: Int, text: String) {
        Networking().networking("http://thometzki.de/pp/?submitComment=true&googleid=$googleid&postid=$postid&comment=$text")
    }

}