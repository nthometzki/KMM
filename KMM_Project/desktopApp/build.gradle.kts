import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.0.0-beta5"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":shared", "default"))
    implementation("com.arkivanov.mvikotlin:mvikotlin:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-logging:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-timetravel:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-reaktive:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:rx:3.0.0-alpha02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.0.0-alpha02")
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}