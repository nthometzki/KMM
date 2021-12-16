package com.thkoeln.kmm_project

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class Data(val one: String, val key: String)

@DelicateCoroutinesApi
fun main() {
    GlobalScope.launch {
        val response = networking("http://echo.jsontest.com/key/value/one/two")

        // Serialization - Plugin also needed in build gradle
        val obj = Json.decodeFromString<Data>(response)
        println(obj.toString())
    }
}