package com.code.fastframe.retrofit.headers

object CacheHeaders {
  const val HEADER_FILED = "cache"
  const val COLON = ":"

  // 无网、网络异常、接口失败优先展示缓存
  const val ERROR = HEADER_FILED + COLON + STATUS.ERROR

  //用于页面的秒刷,永远展示上次缓存，没有则请求网络数据
  const val NORMAL = HEADER_FILED + COLON + STATUS.NORMAL

  //不需要缓存
  const val NO = HEADER_FILED + COLON + STATUS.NO

  //app采取默认缓存模式
  const val DEFAULT = STATUS.NO

  // 客户端可以缓存
  private const val PRIVATE = "Cache-Control:private"

  // 客户端和代理服务器都可缓存（前端的同学，可以认为public和private是一样的）
  private const val MAX_AGE = "Cache-Control:max-age=xxx"

  // 缓存的内容将在 xxx 秒后失效
  private const val NO_CACHE = "Cache-Control:no-cache"

  // 需要使用对比缓存来验证缓存数据（后面介绍）
  private const val PUBLIC = "Cache-Control:public"

  // 所有内容都不会缓存，强制缓存，对比缓存都不会触发（对于前端开发来说，缓存越多越好，so...基本上和它说886）
  private const val NO_STORE = "Cache-Control:no-store"

  object STATUS {
    const val ERROR = "error"
    const val NORMAL = "normal"
    const val NO = "no"
  }
}