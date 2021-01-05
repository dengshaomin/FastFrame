package com.iqiyi.extension

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

inline fun <reified T : Fragment> FragmentManager.commitNowOnce(
  creator: () -> T,
  tag: String
) {
  if (findFragmentByTag(tag) is T) {
    Log.w(TAG, "There is already a fragment instance.")
    return
  }

  beginTransaction()
    .add(creator(), tag)
    .commitNow()
}

inline fun <reified T : Fragment> FragmentManager.commitOnce(
  creator: () -> T,
  tag: String = T::class.java.name
) {
  if (findFragmentByTag(tag) is T) {
    Log.w(TAG, "There is already a fragment instance.")
    return
  }

  beginTransaction()
    .add(creator(), tag)
    .commit()
  executePendingTransactions()
}

inline fun <reified T : Fragment> FragmentManager.commitDialogAllowingStateLossOnce(
  creator: () -> T,
  tag: String? = null
) {

  val fragmentTag = tag ?: T::class.java.name
  if (findFragmentByTag(fragmentTag) is T) {
    Log.w(TAG, "There is already a fragment instance.")
    return
  }

  beginTransaction()
    .add(creator(), fragmentTag)
    .commitAllowingStateLoss()
  executePendingTransactions()
}