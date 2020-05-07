# MVI architecture for Kotlin mutliplatform project
Currently sample is only for android app. Feel free to fork and implement for the iOS app.

Follow these articles for android and iOS project setup for kotlin multiplatorm:

Android https://medium.com/@kalaiselvan369/kotlin-mobile-cross-platform-7647d25e3663

iOS https://medium.com/@kalaiselvan369/kotlin-multiplatform-4bcff5b31c2e


### To publish shared library to local maven
If you wish to publish the shared module as library to local maven then add the maven-publish plugin in the build gradle.

Follow this article for publishing https://developer.android.com/studio/build/maven-publish-plugin

Run the below command in terminal to push it to local maven
 ```
 ./gradlew clean build publishReleasePublicationToMavenLocal
 ```
For building the framework for iOS app run this command
```
 ./gradlew :shared:packForXcode
```
