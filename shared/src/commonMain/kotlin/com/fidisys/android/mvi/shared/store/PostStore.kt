package com.fidisys.android.mvi.shared.store

import Post
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.utils.JvmSerializable

interface PostStore : Store<PostStore.Intent, PostStore.State, PostStore.Label> {

    sealed class Intent : JvmSerializable {
        object GetPost : Intent()
    }

    data class State(
        val data: Post?,
        val isFinished : Boolean = false
    ) : JvmSerializable

    sealed class Label : JvmSerializable {
        data class Dispatched(val id: String) : Label()
    }
}