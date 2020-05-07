package com.fidisys.android.mvi.shared.controllers

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.fidisys.android.mvi.shared.view.PostView

interface PostController {

    fun onViewCreated(postView: PostView, viewLifecycle: Lifecycle)

    interface Dependencies {
        val storeFactory: StoreFactory
        val lifecycle: Lifecycle
    }
}