package com.thkoeln.kmm_project

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}