package com.example.retrosql.app_singleton

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {

        var instance: MyApplication? = null
            private set

        val context: Context?
            get() = instance
    }
}