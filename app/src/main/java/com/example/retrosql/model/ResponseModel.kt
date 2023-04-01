package com.example.retrosql.model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseModel(
    @SerializedName("albumId")
    var albumId: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("url")
    var url: String? = null
) : Parcelable