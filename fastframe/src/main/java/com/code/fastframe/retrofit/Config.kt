package com.code.fastframe.retrofit

class Config {
  object OkHttpClient {
    //连接超时时间
    var connectTimeout = 10000

    //写超时时间
    var writeTimeout = 10000

    //读超时时间
    var readTimeout = 10000
  }

  //重试相关
  object OkhttpRetry {
    //重试次数
    @JvmField var executionCount = 3

    //重试间隔
    @JvmField var retryInterval = 1000
  }

  //缓存
  object CacheManager {
    //磁盘缓存容量
    @JvmField var diskCacheSize = (20 * 1024 * 1024).toLong()
  }
}