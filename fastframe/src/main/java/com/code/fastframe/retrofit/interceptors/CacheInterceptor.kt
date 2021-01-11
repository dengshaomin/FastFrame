package com.code.fastframe.retrofit.interceptors

import android.content.Context
import android.text.TextUtils
import com.code.fastframe.extension.tryOrNull
import com.code.fastframe.retrofit.caches.CacheManager
import com.code.fastframe.retrofit.caches.CacheType
import com.code.fastframe.retrofit.headers.CacheHeaders
import com.code.fastframe.retrofit.headers.CacheHeaders.STATUS
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Protocol.HTTP_1_1
import okhttp3.Request
import okhttp3.Response
import okhttp3.Response.Builder
import okhttp3.ResponseBody
import java.io.IOException

class CacheInterceptor(context: Context) : Interceptor {
  private val context: Context
  @Throws(IOException::class) override fun intercept(chain: Chain): Response {
    val request = chain.request()
    var cacheHead = request.header(CacheHeaders.HEADER_FILED)
    if (TextUtils.isEmpty(cacheHead)) {
      cacheHead = CacheHeaders.DEFAULT
    }
    return if (!TextUtils.equals(cacheHead, STATUS.NO)) {
      val url = request.url().url().toString()
      var responStr: String? = null
      //            String reqBodyStr = getPostParams(request);
      if (TextUtils.equals(cacheHead, STATUS.NORMAL)) {
        val response = getCacheResponse(request)
        if (response != null) {
          asyncRequestNet(request, chain)
          return response
        }
      }
      try {
        val response = chain.proceed(request)
        // 只有在网络请求返回成功之后，才进行缓存处理
        if (response.isSuccessful) {
          val responseBody = response.body()
          if (responseBody != null) {
            responStr = responseBody.string()
            if (!TextUtils.isEmpty(responStr)) {
              //存缓存，以链接+参数进行MD5编码为KEY存
              CacheManager.getInstance(context).setCache(CacheManager.encryptMD5(url), responStr)
            }
            //                        CacheManager.getInstance(context).setCache(CacheManager.encryptMD5(url + reqBodyStr), responStr);//存缓存，以链接+参数进行MD5编码为KEY存
          }
          getOnlineResponse(response, responStr)
        } else {
          chain.proceed(request)
        }
      } catch (e: Exception) {
        e.printStackTrace()
        // 发生异常了，这里就开始去缓存，但是有可能没有缓存，那么就需要丢给下一轮处理了
        var response: Response? = null
        if (TextUtils.equals(cacheHead, STATUS.ERROR) || TextUtils.equals(
                cacheHead,
                STATUS.NORMAL
            )
        ) {
          response = getCacheResponse(request)
        }
        response ?: //丢给下一轮处理
        chain.proceed(request)
      }
    } else {
      chain.proceed(request)
    }
  }

  private fun asyncRequestNet(
    request: Request,
    chain: Chain
  ) {
    GlobalScope.launch {
      tryOrNull {
        val response = chain.proceed(request)
        if (response.isSuccessful) {
          val responseBody = response.body()
          if (responseBody != null) {
            val responStr = responseBody.string()
            if (!TextUtils.isEmpty(responStr)) {
              CacheManager.getInstance(context)
                  .setCache(
                      CacheManager.encryptMD5(request.url().toString()),
                      responStr
                  ) //存缓存，以链接+参数进行MD5编码为KEY存
            }
          }
        }
      }
    }
  }

  private fun getCacheResponse(request: Request): Response? {
    val url = request.url().url().toString()
    //        String params = getPostParams(request);
    //        String cacheStr = CacheManager.getInstance(context).getCache(CacheManager.encryptMD5(url + params));//取缓存，以链接+参数进行MD5编码为KEY取
    val cacheStr = CacheManager.getInstance(context)
        .getCache(CacheManager.encryptMD5(url))
        ?: return null //取缓存，以链接+参数进行MD5编码为KEY取
    return Builder()
        .code(200)
        .body(ResponseBody.create(null, cacheStr))
        .request(request)
        .message(CacheType.DISK_CACHE)
        .protocol(HTTP_1_1)
        .build()
  }

  private fun getOnlineResponse(
    response: Response,
    body: String?
  ): Response {
    val responseBody = response.body()
    return Builder()
        .code(response.code())
        .body(ResponseBody.create(responseBody?.contentType(), body))
        .request(response.request())
        .message(response.message())
        .protocol(response.protocol())
        .build()
  }

  /**
   * 获取在Post方式下。向服务器发送的参数
   */
  private fun getPostParams(request: Request): String {
    var reqBodyStr = ""
    val method = request.method()
    if ("POST" == method) // 如果是Post，则尽可能解析每个参数
    {
      val sb = StringBuilder()
      if (request.body() is FormBody) {
        val body = request.body() as FormBody?
        if (body != null) {
          for (i in 0 until body.size()) {
            sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",")
          }
          sb.delete(sb.length - 1, sb.length)
        }
        reqBodyStr = sb.toString()
        sb.delete(0, sb.length)
      }
    }
    return reqBodyStr
  }

  init {
    this.context = if (context.applicationContext == null) context else context.applicationContext
  }
}