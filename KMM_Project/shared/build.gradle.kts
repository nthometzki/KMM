import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.4.10"
    id("kotlin-parcelize") // Apply the plugin for Android
}

kotlin {
    android()

    jvm()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.arkivanov.mvikotlin:mvikotlin:3.0.0-alpha02")
                implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.0.0-alpha02")
                implementation("com.arkivanov.mvikotlin:mvikotlin-logging:3.0.0-alpha02")
                implementation("com.arkivanov.mvikotlin:mvikotlin-timetravel:3.0.0-alpha02")
                implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-reaktive:3.0.0-alpha02")
                implementation("com.arkivanov.mvikotlin:rx:3.0.0-alpha02")
                implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.0.0-alpha02")
                implementation("com.badoo.reaktive:reaktive:1.2.1")
                implementation("com.arkivanov.essenty:lifecycle:0.2.2")
                implementation("com.arkivanov.essenty:state-keeper:0.2.2")
                implementation("com.arkivanov.essenty:instance-keeper:0.2.2")
                implementation("io.ktor:ktor-client-core:1.6.7")
                implementation("io.ktor:ktor-client-cio:1.6.7")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
            }
        }
        val androidMain by getting {
        }




    }
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(31)
    }
}