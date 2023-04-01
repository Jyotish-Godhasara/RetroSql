package com.example.retrosql.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String?,
        @Field("user_device_id") user_device_id: String = "1",
        @Field("fcm_token") fcm_token: String = "1" // Add '[]' here.
    ): Call<Any>

    @FormUrlEncoded
    @POST("event-list")
    fun evetList(
        @Field("user_id") user_id: String?,
        @Field("user_device_id") user_device_id: String?,
        @Field("access_token") access_token: String?,
    ): Call<Any>


}


