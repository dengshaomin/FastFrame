package com.code.codeframlibrary.commons.retrofit;

import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.code.codeframlibrary.commons.model.ServerCommonModel;
import com.code.codeframlibrary.commons.retrofit.convert.FastJsonConverterFactory;
import com.code.codeframlibrary.commons.retrofit.http.AcgAcgHttpClientException;
import com.code.codeframlibrary.commons.retrofit.http.AcgHttpException;
import com.code.codeframlibrary.commons.retrofit.http.AcgHttpServerException;
import com.code.codeframlibrary.commons.retrofit.http.ApiException;
import com.code.codeframlibrary.commons.retrofit.http.ApiNoDataException;
import com.code.codeframlibrary.commons.retrofit.interceptor.RetryInterceptor;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dengshaomin on 2016/7/26.
 */
public class RetrofitHttpUtil {

    public static String TAG = RetrofitHttpUtil.class.getSimpleName();

    private static FastJsonConverterFactory mFastJsonConverterFactory;

    private static RetryInterceptor mRetryInterceptor;

    private static OkHttpClient mOkHttpClient;


    private static final int DEFAULT_TIMEOUT = 10000;

    private static final int RETRY_TIMES = 3;

    private static final int RESULT_API_OK = 200;

    private static final String RESPONSE_NORMAL = "200";

    public static <T> T createServerApi(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())
                .addConverterFactory(getConvertFactory())
                .build();
        return retrofit.create(clazz);
    }

    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(getRetryInterceptor())
                    .build();
        }
        return mOkHttpClient;
    }

    private static Interceptor getRetryInterceptor() {
        if (mRetryInterceptor == null) {
            mRetryInterceptor = new RetryInterceptor(RETRY_TIMES);
        }
        return mRetryInterceptor;
    }

    private static Converter.Factory getConvertFactory() {
        if (mFastJsonConverterFactory == null) {
            mFastJsonConverterFactory = new FastJsonConverterFactory();
        }
        return mFastJsonConverterFactory;
    }

    public static <T> Observable<T> call(final Call<ServerCommonModel<T>> requestCall) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) {
                try {
                    Response<ServerCommonModel<T>> response = requestCall.execute();
                    if (emitter.isDisposed()) {
                        return;
                    }
                    switch (response.code()) {
                        case RESULT_API_OK:
                            ServerCommonModel<T> responseBody = response.body();
                            if (responseBody == null) {
                                Exception exception = new Exception("response.body() is null");
                                Log.e(TAG, response.toString() + " ==> " + exception.toString());
                                throw exception;
                            }
                            if (RESPONSE_NORMAL.equals(responseBody.status)) {
                                if (responseBody.data == null) {
                                    /**
                                     * 返回data为空的时候，会抛出 ApiNoDataException，请根据业务场景处理
                                     */
                                    ApiNoDataException apiNoDataException = new ApiNoDataException("response.body().data is null");
                                    Log.e(TAG, response.toString() + " ==> " + apiNoDataException.toString());
                                    throw apiNoDataException;
                                } else {
                                    emitter.onNext(responseBody.data);
                                }
                                emitter.onComplete();
                            } else {
                                ApiException apiException = new ApiException(responseBody.status, responseBody.message,
                                        responseBody.data == null ? "" : responseBody.data.toString());
                                Log.e(TAG, response.toString() + " ==> " + apiException.toString());
                                throw apiException;
                            }
                            break;
                        default:
                            AcgHttpException httpException = getHttpException(response);
                            if (httpException != null) {
                                Log.e(TAG, response.toString() + " ==> " + httpException.toString());
                                throw httpException;
                            }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    if (!emitter.isDisposed()) {
                        emitter.onError(exception);
                    }
                }
            }
        });
    }

    private static AcgHttpException getHttpException(Response response) {
        String msg = response.message();
        int c = response.code() / 100;
        switch (c) {
            case 4:
                return new AcgAcgHttpClientException(msg);
            case 5:
                return new AcgHttpServerException(msg);
            default:
                return null;
        }

    }
}
