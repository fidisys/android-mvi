package com.fidisys.android.mvi.shared.network

import Post
import com.badoo.reaktive.utils.ensureNeverFrozen
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.defaultSerializer
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.takeFrom

class PostApiImpl : PostApi {

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    private val json = defaultSerializer()

    init {
        ensureNeverFrozen()
    }

    override suspend fun getPost(): Post = client.get {
        getPostUrl()
    }

    private fun HttpRequestBuilder.getPostUrl() {
        url {
            takeFrom("https://jsonplaceholder.typicode.com/posts/1")
        }
    }
}