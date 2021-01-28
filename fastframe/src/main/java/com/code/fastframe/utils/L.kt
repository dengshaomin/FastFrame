package com.code.fastframe.utils

import android.text.TextUtils
import android.util.Log
import com.code.fastframe.BuildConfig

object L {
    private const val TAG = "FastFrame"
    private val isDebug = BuildConfig.DEBUG

    @JvmStatic
    fun d(tag: String = TAG, msg: String? = null) {
        if (!isDebug) {
            return
        }
        Log.d(if (TextUtils.isEmpty(msg)) TAG else tag, if (TextUtils.isEmpty(msg)) tag else msg!!)
    }

    @JvmStatic
    fun e(tag: String = TAG, msg: String? = null) {
        if (!isDebug) {
            return
        }
        Log.e(if (TextUtils.isEmpty(msg)) TAG else tag, if (TextUtils.isEmpty(msg)) tag else msg!!)
    }

    @JvmStatic
    fun i(tag: String = TAG, msg: String? = null) {
        if (!isDebug) {
            return
        }
        Log.i(if (TextUtils.isEmpty(msg)) TAG else tag, if (TextUtils.isEmpty(msg)) tag else msg!!)
    }

    @JvmStatic
    fun w(tag: String = TAG, msg: String? = null) {
        if (!isDebug) {
            return
        }
        Log.w(if (TextUtils.isEmpty(msg)) TAG else tag, if (TextUtils.isEmpty(msg)) tag else msg!!)
    }
}