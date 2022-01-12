package com.thkoeln.kmm_project.networking

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Networking {

    @Serializable
    data class Data(val id: String, val text: String, val account_id: String, val timestamp: String)
    @Serializable
    data class Likes(val id: String, val account_id: String, val post_id: String)
    @Serializable
    data class Comment(val id: String, val post_id: String, val text: String, val account_id: String, val timestamp: String)

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
        return Json.decodeFromString(response)
    }

    suspend fun submitPost(googleid: String, text: String, id: String) {
        Networking().networking("http://thometzki.de/pp/?submitPost=true&googleid=$googleid&post=$text&id=$id")
    }

    suspend fun submitComment(googleid: String, postid: String, text: String) {
        Networking().networking("http://thometzki.de/pp/?submitComment=true&googleid=$googleid&postid=$postid&comment=$text")
    }

    suspend fun postLike(googleid: String, postid: String, liked: Boolean) {
        Networking().networking("http://thometzki.de/pp/?submitLike=true&googleid=$googleid&postid=$postid")
    }

    suspend fun getTweet(postid: String): Data {
        val response = Networking().networking("http://thometzki.de/pp/?getSinglePost=true&postid=$postid")

        // Serialization - Plugin also needed in build gradle
        val obj = Json.decodeFromString<Array<Data>>(response)
        return obj[0]
    }

    suspend fun getLikes(postid: String): Int {
        val response = Networking().networking("http://thometzki.de/pp/?getLikes=true&postid=$postid")

        // Serialization - Plugin also needed in build gradle
        val obj = Json.decodeFromString<Array<Likes>>(response)
        return obj.size
    }

    suspend fun getComments(postid: String): Array<Comment> {
        val response = Networking().networking("http://thometzki.de/pp/?getComments=true&postid=$postid")

        // Serialization - Plugin also needed in build gradle
        return Json.decodeFromString(response)
    }

}