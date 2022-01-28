import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.0.0-beta5"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":shared"))
    implementation("com.arkivanov.mvikotlin:mvikotlin:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-logging:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-timetravel:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-reaktive:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:rx:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.0.0-alpha02")
    implementation("com.arkivanov.decompose:decompose:0.5.0")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:0.3.1")
    implementation(project(mapOf("path" to ":shared")))

}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}