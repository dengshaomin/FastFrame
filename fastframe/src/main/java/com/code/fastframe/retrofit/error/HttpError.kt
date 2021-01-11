package com.code.fastframe.retrofit.error

class HttpError(
  val errorType: ErrorType,
  val code: String?,
  val msg: String?,
  val exception: Exception?
)