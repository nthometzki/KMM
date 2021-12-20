package com.thkoeln.kmm_project

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class Data(val id: String, val text: String, val account_id: String, val timestamp: String, val username: String)


@DelicateCoroutinesApi
fun main() {
    GlobalScope.launch {
        val response = networking("http://thometzki.de/pp/?getPosts=true")

        // Serialization - Plugin also needed in build gradle
        val obj = Json.decodeFromString<Array<Data>>(response)
        println(obj[0].toString())
    }
}