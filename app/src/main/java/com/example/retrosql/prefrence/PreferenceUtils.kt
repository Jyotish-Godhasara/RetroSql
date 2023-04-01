package com.example.retrosql.prefrence

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.retrosql.R


class PreferenceUtils(val context: Context?) {
    private val preferences: SharedPreferences? = context?.getSharedPreferences(
        context.getString(
            R.string.app_name
        ), Context.MODE_PRIVATE
    )

    companion object {
        const val TOKEN = "token"
        const val USERID = "userId"
        const val USERIMAGE = "userImage"
    }

    fun getString(key: String): String? {
        return preferences?.getString(key, "")
    }

    fun setString(key: String, value: String?) {
        preferences?.edit()?.putString(key, value)?.apply()
    }

    fun getToken(): String? {
        return preferences?.getString(TOKEN, "")
    }

    fun setToken(token: String?) {
        preferences?.edit()?.putString(TOKEN, token)?.apply()
    }

    fun clear() {

        Log.d("TAG", "code :::::: 2")
        preferences?.edit()?.clear()?.apply()
    }
}