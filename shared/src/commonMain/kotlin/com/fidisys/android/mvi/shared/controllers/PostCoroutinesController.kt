package com.fidisys.android.mvi.shared.controllers

import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.core.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.fidisys.android.mvi.shared.eventBus
import com.fidisys.android.mvi.shared.ioDispatcher
import com.fidisys.android.mvi.shared.mainDispatcher
import com.fidisys.android.mvi.shared.mapper.toBusEvent
import com.fidisys.android.mvi.shared.mapper.toIntent
import com.fidisys.android.mvi.shared.mapper.toViewModel
import com.fidisys.android.mvi.shared.network.PostApiImpl
import com.fidisys.android.mvi.shared.store.PostStoreFactory
import com.fidisys.android.mvi.shared.view.PostView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class PostCoroutinesController(dependencies: PostController.Dependencies) : PostController {

    private val postStore = PostStoreFactory(
        storeFactory = dependencies.storeFactory,
        mainContext = mainDispatcher,
        ioContext = ioDispatcher,
        api = PostApiImpl()
    ).create()

    init {
        bind(dependencies.lifecycle, BinderLifecycleMode.CREATE_DESTROY, mainDispatcher) {
            postStore.labels.map { it.toBusEvent() } bindTo { eventBus.send(it)}
        }
        dependencies.lifecycle.doOnDestroy(postStore::dispose)
    }

    override fun onViewCreated(postView: PostView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY,
            mainDispatcher
        ) {
            postView.events.map { it.toIntent() } bindTo postStore
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP,
            mainDispatcher
        ) {
            postStore.states.map { it.toViewModel() } bindTo postView
        }
    }
}