package com.example.retrosql.retrofit


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class JsonErrorResponseModel(
    @SerializedName("error")
    var error: Error? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("stack")
    var stack: String? = null,
    @SerializedName("status")
    var status: String? = null
) : Parcelable