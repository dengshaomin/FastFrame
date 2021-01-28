package com.code.fastframe.retrofit

import android.content.Context
import android.text.TextUtils
import com.code.fastframe.retrofit.convertfactory.json.FastJsonConverterFactory
import com.code.fastframe.retrofit.interceptors.CacheInterceptor
import com.code.fastframe.retrofit.interceptors.OkhttpRetryInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import java.util.HashMap
import java.util.concurrent.TimeUnit.MILLISECONDS

class ApiFactory {
  private var apiCachePool = mutableMapOf<String, Any>()
  private var mainHostUrl: String? = null
  private var mOkHttpClient: OkHttpClient? = null
  private var mApplicationContext: Context? = null
  private fun initInternal(
    context: Context?,
    mainHost: String?
  ) {
    if (context == null) {
      throw RuntimeException("context must not be null")
    }
    mApplicationContext =
      if (context.applicationContext == null) context else context.applicationContext
    mainHostUrl = mainHost
  }

  private fun <T> createServerApiInternal(
    clazz: Class<T>,
    url: String?
  ): T {
//    requireNotNull(clazz) { "class must not be null" }
    if (TextUtils.isEmpty(url) && TextUtils.isEmpty(mainHostUrl)) {
      throw java.lang.RuntimeException("you must set host")
    }
    if (apiCachePool == null) {
      apiCachePool = HashMap()
    }
    for (key in apiCachePool.keys) {
      if (TextUtils.equals(clazz.name, key)) {
        return apiCachePool.get(key) as T
      }
    }
    val retrofit = Builder()
        .baseUrl(if (TextUtils.isEmpty(url)) mainHostUrl else url)
        .client(okHttpClient)
        .addConverterFactory(
            FastJsonConverterFactory.create()
        ) //                .addConverterFactory(StringConverterFactory.create())
        //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    val t = retrofit.create(clazz)
    t?.let {
      apiCachePool[clazz.name] = t
    }
    return t
  }//                    .addNetworkInterceptor(NetCacheInterceptor)

  //                    .addInterceptor(OfflineCacheInterceptor)
  //                    .cache(cache)
  //            File httpCacheDirectory = new File(MineApplication.applicationContext.getCacheDir(), "okhttpCache");
//            int cacheSize = 10 * 1024 * 1024; // 10 MiB
//            Cache cache = new Cache(httpCacheDirectory, cacheSize);
  private val okHttpClient: OkHttpClient?
    private get() {
      if (mOkHttpClient == null) {
//            File httpCacheDirectory = new File(MineApplication.applicationContext.getCacheDir(), "okhttpCache");
//            int cacheSize = 10 * 1024 * 1024; // 10 MiB
//            Cache cache = new Cache(httpCacheDirectory, cacheSize);
        mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(Config.OkHttpClient.connectTimeout.toLong(), MILLISECONDS)
            .writeTimeout(Config.OkHttpClient.writeTimeout.toLong(), MILLISECONDS)
            .readTimeout(Config.OkHttpClient.readTimeout.toLong(), MILLISECONDS)
            .addInterceptor(OkhttpRetryInterceptor.Builder().build())
            .addInterceptor(
                CacheInterceptor(
                    mApplicationContext!!
                )
            )
            .retryOnConnectionFailure(
                true
            ) //                    .addNetworkInterceptor(NetCacheInterceptor)
            //                    .addInterceptor(OfflineCacheInterceptor)
            //                    .cache(cache)
            .build()
      }
      return mOkHttpClient
    }

  /**
   * 有网时候的缓存
   */
  //    static final Interceptor NetCacheInterceptor = new Interceptor() {
  //        @Override
  //        public Response intercept(Chain chain) throws IOException {
  //            Request request = chain.request();
  //            Response response = chain.proceed(request);
  //            int onlineCacheTime = 20;//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
  //            return response.newBuilder()
  //                    .header("Cache-Control", "public, max-age=" + onlineCacheTime)
  //                    .removeHeader("Pragma")
  //                    .build();
  //        }
  //    };
  //
  //    /**
  //     * 没有网时候的缓存
  //     */
  //    static final Interceptor OfflineCacheInterceptor = new Interceptor() {
  //        @Override
  //        public Response intercept(Chain chain) throws IOException {
  //            Request request = chain.request();
  //            if (NetWorkUtils.off(mApplicationContext)) {
  //                int offlineCacheTime = 20;//离线的时候的缓存的过期时间
  //                request = request.newBuilder()
  ////                        .cacheControl(FORCE_NETWORK) //强制使用缓存,如果没有缓存数据,则抛出504(only-if-cached)
  ////                        .cacheControl(FORCE_CACHE)    //强制使用网络,不使用任何缓存.
  ////                        .cacheControl(new CacheControl
  ////                                .Builder()
  ////                                .maxStale(60,TimeUnit.SECONDS)
  ////                                .onlyIfCached()
  ////                                .build()
  ////                        ) 两种方式结果是一样的，写法不同
  //                        .header("Cache-Control", "public, only-if-cached, max-stale=" + offlineCacheTime)
  //                        .build();
  //            }
  //            return chain.proceed(request);
  //        }
  //    };
  companion object {
    @Volatile
    private var mApiFactory: ApiFactory? = null
    private val instance: ApiFactory?
      private get() {
        if (mApiFactory == null) {
          synchronized(ApiFactory::class.java) {
            if (mApiFactory == null) {
              mApiFactory = ApiFactory()
            }
          }
        }
        return mApiFactory
      }

    @JvmStatic @JvmOverloads fun init(
      context: Context?,
      mainHost: String? = null
    ) {
      instance!!.initInternal(context, mainHost)
    }

    //    private <T> T createServerApi(Class<T> clazz) {
    //        return createServerApi(clazz, null);
    //    }
    fun <T> createServerApi(
      clazz: Class<T>,
      url: String? = null
    ): T {
      return instance!!.createServerApiInternal(clazz, url)
    }
  }
}