package com.fidisys.android.mvi.shared

sealed class BusEvent {
    data class PostDispatched(val id : String) : BusEvent()
}