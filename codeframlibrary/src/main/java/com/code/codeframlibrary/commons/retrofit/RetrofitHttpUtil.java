package com.code.codeframlibrary.commons.retrofit;

import java.util.concurrent.TimeUnit;

import com.code.codeframlibrary.commons.retrofit.convert.FastJsonConverterFactory;
import com.code.codeframlibrary.commons.retrofit.interceptor.RetryInterceptor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by dengshaomin on 2016/7/26.
 */
public class RetrofitHttpUtil {

    private String SERVER_URL;

    private static RetrofitHttpUtil retrofitHttpUtil;

    private static final int DEFAULT_TIMEOUT = 10000;

    private static final int RETRY_TIMES = 3;


    Retrofit mRetrofit;

    public static RetrofitHttpUtil getInstance() {
        if (retrofitHttpUtil == null) {
            synchronized (RetrofitHttpUtil.class) {
                if (retrofitHttpUtil == null) {
                    retrofitHttpUtil = new RetrofitHttpUtil();
                }
            }
        }
        return retrofitHttpUtil;
    }

    public void init(String server_url) {
        this.SERVER_URL = server_url;
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getClient())
                    .addConverterFactory(getConvertFactory())
                    .build();
        }
    }

    public RetrofitHttpUtil() {

    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(getRetryInterceptor())
                .build();
    }

    Interceptor getRetryInterceptor() {
        return new RetryInterceptor(RETRY_TIMES);
    }

    Converter.Factory getConvertFactory() {
        return new FastJsonConverterFactory();
    }
}
