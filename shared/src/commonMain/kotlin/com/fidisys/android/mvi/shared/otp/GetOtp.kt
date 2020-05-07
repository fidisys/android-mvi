package com.fidisys.android.mvi.shared.otp

import kotlinx.serialization.Serializable

@Serializable
data class GetOtp(val mobileNo : String, val otp : String)