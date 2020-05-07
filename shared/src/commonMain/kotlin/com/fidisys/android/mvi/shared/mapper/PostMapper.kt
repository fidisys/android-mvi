package com.fidisys.android.mvi.shared.mapper

import com.fidisys.android.mvi.shared.BusEvent
import com.fidisys.android.mvi.shared.store.PostStore
import com.fidisys.android.mvi.shared.view.PostView

fun PostStore.State.toViewModel(): PostView.Model = PostView.Model(data = data)

fun PostView.Event.toIntent(): PostStore.Intent =
    when (this) {
        is PostView.Event.GetPost -> PostStore.Intent.GetPost
    }

fun PostStore.Label.toBusEvent(): BusEvent =
    when (this) {
        is PostStore.Label.Dispatched -> BusEvent.PostDispatched(id = id)
    }