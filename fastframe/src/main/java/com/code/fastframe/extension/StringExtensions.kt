package com.iqiyi.extension

import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
  val md = MessageDigest.getInstance("MD5")
  return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun String.startWithA2Z(): Boolean {
  if (this.isEmpty()) {
    return false
  }
  val head = this[0]
  return head in 'a'..'z' || head in 'A'..'Z'
}