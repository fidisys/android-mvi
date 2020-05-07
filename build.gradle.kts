// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    `kotlin-dsl`
}

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Deps.Jetbrains.Kotlin.Plugin)
        classpath(Deps.Android.Tools.Build.Gradle)
        classpath(Deps.Jetbrains.Kotlin.SerializationPlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        mavenLocal()
        google()
        jcenter()
        maven (url ="https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/arkivanov/maven")
        maven(url = "https://dl.bintray.com/badoo/maven")
    }
}
