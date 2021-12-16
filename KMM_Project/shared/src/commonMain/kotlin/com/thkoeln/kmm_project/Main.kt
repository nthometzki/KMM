package com.thkoeln.kmm_project

import com.thkoeln.kmm_project.networking
import kotlinx.coroutines.*

@DelicateCoroutinesApi
fun main() {
    GlobalScope.launch {
        networking("https://en.wikipedia.org/wiki/Main_Page")
    }
}