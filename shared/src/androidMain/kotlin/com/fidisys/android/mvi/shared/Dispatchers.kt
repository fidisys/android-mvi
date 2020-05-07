package com.fidisys.android.mvi.shared

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val mainDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate

internal actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO