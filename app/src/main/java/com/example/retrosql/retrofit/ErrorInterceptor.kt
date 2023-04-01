package com.example.retrosql.retrofit

import com.example.retrosql.app_singleton.MyApplication
import com.example.retrosql.extension.showToast
import com.example.retrosql.prefrence.PreferenceUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.nio.charset.Charset


class ErrorInterceptor(val prefrenceUtils: PreferenceUtils) : Interceptor {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    val tag = "ErrorInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)

//        val source = response.body?.string()
//        source?.request(Long.MAX_VALUE)

//        console(tag, message = "--${response.isSuccessful}")
        response.isSuccessful

        when (response.code) {

            400 -> {
                //Show Bad Request Error Message
                val element: JsonErrorResponseModel = jsonErrorResponseModel(response)

                uiScope.launch {
                    withContext(Dispatchers.Main) {
                        MyApplication.context?.showToast("${response.code} - ${element.message?.trim()}")
                    }
                }
            }
            401 -> {
                //Show UnauthorizedError Message
                val element: JsonErrorResponseModel = jsonErrorResponseModel(response)

                uiScope.launch {
                    withContext(Dispatchers.Main) {
                        MyApplication.context?.showToast("${response.code} - ${element.message?.trim()}")
                        prefrenceUtils.clear()
//                        onBoardingPoint.startAfterAuth()
                    }
                }
            }
            403 -> {
                val element: JsonErrorResponseModel = jsonErrorResponseModel(response)

                //Show Forbidden Message
                uiScope.launch {
                    withContext(Dispatchers.Main) {
                        MyApplication.context?.showToast("${response.code} - ${element.message?.trim()}")
                    }
                }
            }
            404 -> {
                //Show FileNotFound Message
                val element: JsonErrorResponseModel = jsonErrorResponseModel(response)

                uiScope.launch {
                    withContext(Dispatchers.Main) {
                        MyApplication.context?.showToast("${response.code} - ${element.message?.trim()}")
                    }
                }
            }
            405 -> {
                //Show FileNotFound Message
                val element: JsonErrorResponseModel = jsonErrorResponseModel(response)

                uiScope.launch {
                    withContext(Dispatchers.Main) {
                        MyApplication.context?.showToast("${response.code} - ${element.message?.trim()}")
                    }
                }
            }
            500 -> {
                //Show Internal Server Message
                val element: JsonErrorResponseModel = jsonErrorResponseModel(response)

                uiScope.launch {
                    withContext(Dispatchers.Main) {
                        MyApplication.context?.showToast("${response.code} - ${element.message?.trim()}")
                    }
                }
            }
            // ... and so on
        }
        return response
    }

    private fun jsonErrorResponseModel(response: Response): JsonErrorResponseModel {
        val source = response.body?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer

        val responseBodyString = buffer?.clone()?.readString(Charset.forName("UTF-8"))

        val element: JsonErrorResponseModel =
            Gson().fromJson(responseBodyString, JsonErrorResponseModel::class.java)
        return element
    }
}


