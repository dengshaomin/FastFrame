package com.code.fastframe.retrofit.interceptors

import com.code.fastframe.retrofit.Config.OkhttpRetry
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InterruptedIOException

class OkhttpRetryInterceptor(builder: Builder) : Interceptor {
  //最大重试次数
  private var executionCount : Int = 3

  //重试的间隔
  var retryInterval : Long = 3000

  @Throws(IOException::class) override fun intercept(chain: Chain): Response {
    val request = chain.request()
    var response = doRequest(chain, request)!!
    var retryNum = 0
    while ((response == null || !response.isSuccessful) && retryNum <= executionCount) {
      val nextInterval = retryInterval
      try {
        Thread.sleep(nextInterval)
      } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
        throw InterruptedIOException()
      }
      retryNum++
      // retry the request
      response = doRequest(chain, request)!!
    }
    return response
  }

  private fun doRequest(
    chain: Chain,
    request: Request
  ): Response? {
    var response: Response? = null
    try {
      response = chain.proceed(request)
    } catch (e: Exception) {
    }
    return response
  }

  class Builder {
    var executionCount: Int
    var retryInterval: Long
    fun executionCount(executionCount: Int): Builder {
      this.executionCount = executionCount
      return this
    }

    fun retryInterval(retryInterval: Long): Builder {
      this.retryInterval = retryInterval
      return this
    }

    fun build(): OkhttpRetryInterceptor {
      return OkhttpRetryInterceptor(this)
    }

    init {
      executionCount = OkhttpRetry.executionCount
      retryInterval = OkhttpRetry.retryInterval.toLong()
    }
  }

  init {
    executionCount = builder.executionCount
    retryInterval = builder.retryInterval
  }
}