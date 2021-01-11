package com.code.fastframe.retrofit.error

enum class ErrorType(
  val type: Int,
  val msg: String
) {
  NET(0, "网络异常"),
  SERVER(1, "服务器异常"),
  DEFAULT(2, "默认错误")
}