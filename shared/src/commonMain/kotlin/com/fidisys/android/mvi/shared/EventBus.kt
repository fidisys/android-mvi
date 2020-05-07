package com.fidisys.android.mvi.shared

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlin.native.concurrent.SharedImmutable

@ExperimentalCoroutinesApi
@SharedImmutable
internal val eventBus = BroadcastChannel<BusEvent>(Channel.BUFFERED)