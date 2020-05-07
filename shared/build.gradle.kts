import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin {
        android()
        // This is for iPhone emulator
        // Switch here to iosArm64 (or iosArm32) to build library for iPhone device
        // Select iOS target for real device or emulator.
        //select iOS target platform depending on the Xcode environment variables
        val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
            if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
                ::iosArm64
            else
                ::iosX64

        iOSTarget("ios") {
            binaries {
                framework {
                    baseName = "shared"
                }
            }
        }
        sourceSets["commonMain"].dependencies {
            implementation(Deps.Jetbrains.Kotlin.StdLib.Common)
            implementation(Deps.Jetbrains.Kotlinx.Coroutines.Core.Common)
            implementation(Deps.Arkivanov.MviKotlin.MviKotlin)
            implementation(Deps.Arkivanov.MviKotlin.MviKotlinMain)
            implementation(Deps.Arkivanov.MviKotlin.MviKotlinLogging)
            implementation(Deps.Arkivanov.MviKotlin.MviKotlinTimeTravel)
            implementation(Deps.Arkivanov.MviKotlin.MviKotlinExtCoroutines)
            implementation(Deps.Badoo.Reaktive.Utils)
            implementation(Deps.Jetbrains.Ktor.KtorClient.Core)
            implementation(Deps.Jetbrains.Ktor.KtorClient.Json)
            implementation(Deps.Jetbrains.Ktor.KtorClient.Serialization)
            implementation(Deps.Jetbrains.Ktor.KtorClient.Logging)
            implementation ("ch.qos.logback:logback-classic:1.2.3")
            implementation(Deps.Jetbrains.Kotlinx.Serialization.RuntimeCommon)
        }

        sourceSets["commonTest"].dependencies {
            implementation(Deps.Jetbrains.Kotlin.Test.Common)
            implementation(Deps.Jetbrains.Kotlin.TestAnnotations.Common)
        }
        sourceSets["androidMain"].dependencies {
            implementation(kotlin("stdlib", Deps.Jetbrains.Kotlin.version))
            implementation(Deps.Jetbrains.Kotlinx.Coroutines.Core)
            implementation(Deps.Jetbrains.Kotlinx.Coroutines.Android)
            implementation(Deps.Jetbrains.Ktor.KtorClient.Okhttp)
            implementation(Deps.Jetbrains.Ktor.KtorClient.JsonJvm)
            implementation(Deps.Jetbrains.Ktor.KtorClient.SerializationJvm)
            //implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")
            implementation(Deps.Jetbrains.Ktor.KtorClient.LoggingJvm)
            implementation(Deps.Jetbrains.Kotlinx.Serialization.Runtime)
        }
        sourceSets["androidTest"].dependencies {
            implementation(kotlin("test", Deps.Jetbrains.Kotlin.version))
            implementation(Deps.Jetbrains.Kotlin.Test.Junit)
        }
        sourceSets["iosMain"].dependencies {
            implementation(Deps.Jetbrains.Kotlinx.Coroutines.Core.Native)
            implementation(Deps.Jetbrains.Ktor.KtorClient.Ios)
            implementation(Deps.Jetbrains.Ktor.KtorClient.JsonNative)
            implementation(Deps.Jetbrains.Ktor.KtorClient.SerializationNative)
            implementation(Deps.Jetbrains.Ktor.KtorClient.LoggingNative)
            implementation(Deps.Jetbrains.Kotlinx.Serialization.RuntimeNative)
        }
        sourceSets["iosTest"].dependencies {
        }
    }
}

// This task attaches native framework built from ios module to Xcode project
// (see iosApp directory). Don't run this task directly,
// Xcode runs this task itself during its build process.
// Before opening the project from iosApp directory in Xcode,
// make sure all Gradle infrastructure exists (gradle.wrapper, gradlew).
// Task to generate iOS framework for xcode projects.
val packForXcode by tasks.creating(Sync::class) {
    group = "build"

    //selecting the right configuration for the iOS framework depending on the Xcode environment variables
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)

    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)

    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\nexport 'JAVA_HOME=${System.getProperty("java.home")}'\ncd '${rootProject.rootDir}'\n./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)

