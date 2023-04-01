package com.example.retrosql.extension

import android.content.Context
import android.widget.Toast

fun Context?.showToast(s: String?): Toast {
    return Toast.makeText(this, s, Toast.LENGTH_SHORT).apply { show() }
}