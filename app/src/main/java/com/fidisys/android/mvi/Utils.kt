package com.fidisys.android.mvi

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory

val storeFactoryInstance =
    if (BuildConfig.DEBUG) {
        LoggingStoreFactory(delegate = TimeTravelStoreFactory(fallback = DefaultStoreFactory))
    } else {
        DefaultStoreFactory
    }

val Context.app: App get() = applicationContext as App

fun <T : Any> T?.requireNotNull(): T = requireNotNull(this)

fun <T : View> View.getViewById(@IdRes id: Int): T = findViewById<T>(id).requireNotNull()