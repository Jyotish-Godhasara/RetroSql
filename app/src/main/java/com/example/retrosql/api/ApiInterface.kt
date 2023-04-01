package com.example.retrosql.api

import com.example.retrosql.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/photos")
    fun getAllUsers(@Query("albumId") id: Int?): Call<ArrayList<ResponseModel>>
}
