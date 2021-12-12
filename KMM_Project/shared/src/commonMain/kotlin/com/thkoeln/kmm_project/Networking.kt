package com.thkoeln.kmm_project

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.engine.cio.*
import io.ktor.network.tls.*

class Networking() {
    val client = HttpClient(CIO) {
        engine {
            // this: CIOEngineConfig
            maxConnectionsCount = 1000
            endpoint {
                // this: EndpointConfig
                maxConnectionsPerRoute = 100
                pipelineMaxSize = 20
                keepAliveTime = 5000
                connectTimeout = 5000
                connectAttempts = 5
            }
            https {
                // this: TLSConfigBuilder
                serverName = "api.ktor.io"
                /*cipherSuites = CIOCipherSuites.SupportedSuites
                trustManager = myCustomTrustManager
                random = mySecureRandom
                addKeyStore(myKeyStore, myKeyStorePassword)*/
            }
        }
    }
    suspend fun makeRequest(url: String): HttpResponse {
        val response: HttpResponse = client.get(url)
        println("Response${response}")
        return response
    }
}