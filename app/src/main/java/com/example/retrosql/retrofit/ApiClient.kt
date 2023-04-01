package com.example.retrosql.retrofit

import com.example.retrosql.app_singleton.MyApplication
import com.example.retrosql.prefrence.PreferenceUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSession
import javax.net.ssl.X509TrustManager

class ApiClient {
    companion object {
        var retrofitService: ApiService? = null

        val prefrenceUtils by lazy { PreferenceUtils(MyApplication.context) }

        fun getInstance(): ApiService? {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(ApiConst.BASE_URL)
                    .client(httpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService
        }

        private fun httpClient(): OkHttpClient {
            val httpClientBuilder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

            SSLSocketFactory.getFactory()?.let {
                httpClientBuilder.sslSocketFactory(
                    it,
                    SSLSocketFactory.certificat()[0] as X509TrustManager
                )
            }
            httpClientBuilder.addInterceptor(printApiLog())
            httpClientBuilder.hostnameVerifier { hostname: String?, session: SSLSession? -> true }
            httpClientBuilder.addInterceptor(AuthTokenInterceptor(prefrenceUtils))
            httpClientBuilder.addInterceptor(ErrorInterceptor(prefrenceUtils))
            return httpClientBuilder.build()
        }

        private fun printApiLog(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }
    }
}