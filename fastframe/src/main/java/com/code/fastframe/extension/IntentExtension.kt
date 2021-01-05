package com.iqiyi.extension

import android.content.Context
import android.content.Intent
import android.os.Bundle


fun Intent.getStringOrNull(key: String): String? =
  try {
    getStringExtra(key)
  } catch (e: Exception) {
    null
  }

fun Intent.getStringOrDefault(key: String, defaultVal: String): String =
  try {
    getStringExtra(key) ?: defaultVal
  } catch (e: Exception) {
    defaultVal
  }

fun Intent.getBooleanOrDefault(key: String, defaultVal: Boolean): Boolean =
  try {
    getBooleanExtra(key, defaultVal)
  } catch (e: Exception) {
    defaultVal
  }

fun Intent.getIntOrDefault(key: String, defaultVal: Int): Int =
  try {
    getIntExtra(key, defaultVal)
  } catch (e: Exception) {
    defaultVal
  }

fun Intent.getLongOrDefault(key: String, defaultVal: Long): Long =
  try {
    getLongExtra(key, defaultVal)
  } catch (e: Exception) {
    defaultVal
  }

fun Intent.getShortOrDefault(key: String, defaultVal: Short): Short =
  try {
    getShortExtra(key, defaultVal)
  } catch (e: Exception) {
    defaultVal
  }

fun Bundle.getStringOrNull(key: String): String? =
  try {
    getString(key)
  } catch (e: Exception) {
    null
  }

fun Bundle.getStringOrDefault(key: String, defaultVal: String): String =
  try {
    getString(key, defaultVal) ?: defaultVal
  } catch (e: Exception) {
    defaultVal
  }

fun Bundle.getBooleanOrDefault(key: String, defaultVal: Boolean): Boolean =
  try {
    getBoolean(key, defaultVal)
  } catch (e: Exception) {
    defaultVal
  }

fun Bundle.getIntOrDefault(key: String, defaultVal: Int): Int =
  try {
    getInt(key, defaultVal)
  } catch (e: Exception) {
    defaultVal
  }

fun Bundle.getLongOrDefault(key: String, defaultVal: Long): Long =
  try {
    getLong(key, defaultVal)
  } catch (e: Exception) {
    defaultVal
  }

fun Bundle.getShortOrDefault(key: String, defaultVal: Short): Short =
  try {
    getShort(key, defaultVal)
  } catch (e: Exception) {
    defaultVal
  }

fun Bundle.getStringArrayOrNull(key: String): Array<String>? =
  try {
    getStringArray(key)
  } catch (e: java.lang.Exception) {
    null
  }

fun Bundle.getStringArrayListOrNull(key: String) =
  try {
    getStringArrayList(key)
  } catch (e: java.lang.Exception) {
    null
  }

fun Bundle.getStringArrayOrDefault(key: String, defaultVal: Array<String>): Array<String> =
  try {
    getStringArray(key) ?: defaultVal
  } catch (e: java.lang.Exception) {
    defaultVal
  }

fun Bundle.getStringArrayListOrDefault(
  key: String,
  defaultVal: ArrayList<String>
): ArrayList<String> =
  try {
    getStringArrayList(key) ?: defaultVal
  } catch (e: java.lang.Exception) {
    defaultVal
  }

fun Intent.start(context: Context?, options: Bundle? = null) {
  context?.startActivity(this, options)
}

fun Array<Intent>.start(context: Context?, options: Bundle? = null) {
  context?.startActivities(this)
}

fun intent(context: Context?, body: Intent.() -> Intent) {
  Intent().body().start(context)
}