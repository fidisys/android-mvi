package com.fidisys.android.mvi.shared

import kotlinx.coroutines.CoroutineDispatcher

internal expect val mainDispatcher : CoroutineDispatcher

internal expect val ioDispatcher : CoroutineDispatcher