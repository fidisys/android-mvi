package com.fidisys.android.mvi.shared.view

import Post
import com.arkivanov.mvikotlin.core.view.MviView

interface PostView : MviView<PostView.Model, PostView.Event> {

    data class Model(
        val data: Post?
    )

    sealed class Event {
        data class GetPost(val id: Int) : Event()
    }
}