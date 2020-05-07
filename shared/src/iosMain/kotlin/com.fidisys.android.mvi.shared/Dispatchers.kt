package com.fidisys.android.mvi.shared

import kotlinx.coroutines.CoroutineDispatcher

internal actual val mainDispatcher: CoroutineDispatcher get() = throw NotImplementedError()

internal actual val ioDispatcher: CoroutineDispatcher get() = throw NotImplementedError()