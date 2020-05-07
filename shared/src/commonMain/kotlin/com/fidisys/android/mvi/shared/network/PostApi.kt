package com.fidisys.android.mvi.shared.network

import Post

interface PostApi {
    suspend fun getPost(): Post
}