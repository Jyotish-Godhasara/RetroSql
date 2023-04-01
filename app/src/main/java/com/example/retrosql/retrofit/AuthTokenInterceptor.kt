package com.example.retrosql.retrofit

import com.example.retrosql.prefrence.PreferenceUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(val prefrenceUtils: PreferenceUtils) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = prefrenceUtils.getToken()

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        requestBuilder.removeHeader("User-Agent")
        requestBuilder.removeHeader("Content-Type")
        requestBuilder.removeHeader("Accept")
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.addHeader("Accept", "application/json")
        requestBuilder.addHeader(
            "User-Agent",
            "Android"
        )

        /*MyApplication.context?.let {
            console(
                tag = "AuthTokenInterceptor",
                message = WebSettings.getDefaultUserAgent(MyApplication.context)
            )
        }*/

        if (token?.isNotEmpty() == true) {
            requestBuilder.addHeader("authorization", "Bearer $token")
        }


//            .header("Authorization", "AuthToken")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}