package com.code.codeframlibrary.commons.retrofit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.code.codeframlibrary.commons.CodeFram;
import com.code.codeframlibrary.commons.retrofit.convert.StringConverterFactory;
import com.code.codeframlibrary.commons.retrofit.services.CommonService;
import com.code.codeframlibrary.commons.retrofit.services.QuestionService;
import com.github.lazylibrary.util.MiscUtils;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by dengshaomin on 2016/7/26.
 */
public class RetrofitHttpUtil {

    /**
     * 服务器地址
     */
    public static String BASE_URL = "";

    private CommonService commonService;

    private QuestionService questionService;

    private Retrofit retrofit = null;

    private static OkHttpClient okHttpClient = null;

    //    private static Context mContext;
    private int maxCacheTime = 0; //s

    private static RetrofitHttpUtil retrofitHttpUtil;

    private Map<String, String> headerParams = new HashMap<>();

    private boolean doubleRequest = false;

    public static final String HOME_CACHE_DATA = "HOME_CACHE_DATA";

    private static final boolean UseHttps = false;

    public RetrofitHttpUtil setDoubleRequest(boolean doubleRequest) {
        this.doubleRequest = doubleRequest;
        return this;
    }

    public static RetrofitHttpUtil getInstance() {
//        mContext = context.getApplicationContext();
        if (retrofitHttpUtil == null) {
            synchronized (RetrofitHttpUtil.class) {
                if (retrofitHttpUtil == null) {
                    retrofitHttpUtil = new RetrofitHttpUtil();
                }
                retrofitHttpUtil.resetParam();
            }
        }
        return retrofitHttpUtil;
    }

    private void resetParam() {
        doubleRequest = false;
        maxCacheTime = 0;
    }

    private Map<String, String> getParamFromUrl(String url, Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
//        if (params.containsKey("plugin_ver")) {
//            if (TextUtils.isEmpty(params.get("plugin_ver"))) {
//                params.put("plugin_ver", GameCenterContants.SDK_VERSION);
//            }
//        } else {
//            params.put("plugin_ver", GameCenterContants.SDK_VERSION);
//        }
//        if (UserManager.getInstance().isLogin()) {
//            if (params.containsKey("user_id")) {
//                if (TextUtils.isEmpty(params.get("user_id"))) {
//                    params.put("user_id", UserManager.getInstance().getUID());
//                }
//            } else {
//                params.put("user_id", UserManager.getInstance().getUID());
//            }
//        }
        if (null == url || url.equals("")) {
            return params;
        }
        String[] strs = url.split("\\?");
        if (strs != null && strs.length > 1) {
            String p = strs[1];
            if (null != p && !p.equals("")) {
                String[] ps = p.split("&");
                if (ps != null && ps.length > 0) {
                    for (int i = 0; i < ps.length; i++) {
                        String t = ps[i];
                        if (t != null && !t.equals("")) {
                            String[] ts = t.split("=");
                            if (ts != null && ts.length > 1) {
                                if (params == null) {
                                    params = new HashMap<>();
                                }
                                params.put(ts[0], ts[1]);
                            }
                        }
                    }
                }
            }
        }
        return params;
    }

    public void lunchTime(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.lunchTime(params);
        call.enqueue(callBack);
    }

    public void get(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initInner((url == null || "".equals(url)) ? BASE_URL : url, null);
        Call<String> call = commonService.get((url == null || "".equals(url)) ? BASE_URL : url, params);
        call.enqueue(callBack);
    }

    public void getUrl(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkOutUrl(url);
        checkParam(params);
        initInner((url == null || "".equals(url)) ? BASE_URL : url, null);
        Call<String> call = commonService.get((url == null || "".equals(url)) ? BASE_URL : url, params);
        call.enqueue(callBack);
    }


    public void postJson(@Url String url, @FieldMap Map<String, String> params, GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initInner((url == null || "".equals(url)) ? BASE_URL : url, null);
        //Call<String> call = commonService.post((url == null || "".equals(url)) ? BASE_URL : url, params);
        String str = JSON.toJSONString(params);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        Call<String> call = commonService.post((url == null || "".equals(url)) ? BASE_URL : url, requestBody);
        call.enqueue(callBack);
    }

    public void postJson(@Url String url, Object params, GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, null);
        url = checkUrl(url);
        checkParam(null);
        initInner((url == null || "".equals(url)) ? BASE_URL : url, null);
        //Call<String> call = commonService.post((url == null || "".equals(url)) ? BASE_URL : url, params);
        String str = JSON.toJSONString(params);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        Call<String> call = commonService.post((url == null || "".equals(url)) ? BASE_URL : url, requestBody);
        call.enqueue(callBack);
    }

    public void post(@Url String url, @FieldMap Map<String, String> params, GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initInner((url == null || "".equals(url)) ? BASE_URL : url, null);
        Call<String> call = commonService.post((url == null || "".equals(url)) ? BASE_URL : url, params);
        call.enqueue(callBack);
    }


    public void getHome(@Url String url, @QueryMap Map<String, String> params,
            final GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, callBack);
        questionService = retrofit.create(QuestionService.class);
        callBack.setFromCache(false);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.getGameCenterHome(params);
        call.enqueue(callBack);
    }

    //使用外部地址，适用于服务器地址不一致时使用
    public void getOutUrl(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkOutUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, callBack);
        questionService = retrofit.create(QuestionService.class);
        callBack.setFromCache(false);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.getWxToken(params);
        call.enqueue(callBack);
    }

    public void getWxToken(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkOutUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, callBack);
        questionService = retrofit.create(QuestionService.class);
        callBack.setFromCache(false);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.getWxToken(params);
        call.enqueue(callBack);
    }

    public void getWxUserinfo(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkOutUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, callBack);
        questionService = retrofit.create(QuestionService.class);
        callBack.setFromCache(false);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.getWxUserInfo(params);
        call.enqueue(callBack);
    }

    public void userGameBooked(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.userGameBooked(params);
        call.enqueue(callBack);
    }

    public void markDownload(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.markDownload(params);
        call.enqueue(callBack);
    }

    public void getGiftCenterData(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.getGiftHome(params);
        call.enqueue(callBack);
    }

    public void login(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        checkParam(params);
        url = checkUrl(url);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.login(params);
        call.enqueue(callBack);
    }

    public void yalogin(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        checkParam(params);
        url = checkUrl(url);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.yalogin(params);
        call.enqueue(callBack);
    }

    public void queryMyPrize(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.queryMyPrize(params);
        call.enqueue(callBack);
    }

    public void savePrizeAdress(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.savePrizeAdress(params);
        call.enqueue(callBack);
    }


    public void sign(@Url String url, @QueryMap Map<String, String> params,
            GCNetCallBack<String> callBack) {
        params = getParamFromUrl(url, params);
        url = checkUrl(url);
        checkParam(params);
        initRetrofit((url == null || "".equals(url)) ? BASE_URL : url, null);
        questionService = retrofit.create(QuestionService.class);
        Call<String> call = questionService.sign(params);
        call.enqueue(callBack);
    }


    public RetrofitHttpUtil setMaxCacheTime(int maxCacheTime) {
        this.maxCacheTime = maxCacheTime * 60 * 60 * 24;
        return this;
    }

    public RetrofitHttpUtil setHeaderParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        this.headerParams = params;
        return this;
    }

    private void initInner(String url, GCNetCallBack callBack) {
        initRetrofit(url, callBack);
        initService(url);
    }

    private void initService(String url) {
        if (commonService == null) {
            commonService = retrofit.create(CommonService.class);
        }
    }

    private String checkUrl(String url) {
        if (null == url || url.equals("")) {
            return "";
        }
        url = BASE_URL + File.separator + url;
        String[] strs = url.split("\\?");
        if (strs != null && strs.length > 0) {
            url = strs[0];
        }
        if (!url.endsWith(File.separator)) {
            url += File.separator;
        }
//        if (strs != null && strs.length > 1) url += "?" + strs[1];
        if (UseHttps && !url.startsWith("https")) {
            url = url.replace("http", "https");
        }
        return url;
    }

    private String checkOutUrl(String url) {
        if (null == url || url.equals("")) {
            return "";
        }
        String[] strs = url.split("\\?");
        if (strs != null && strs.length > 0) {
            url = strs[0];
        }
        if (!url.endsWith(File.separator)) {
            url += File.separator;
        }
//        if (strs != null && strs.length > 1) url += "?" + strs[1];
        if (UseHttps && !url.startsWith("https")) {
            url = url.replace("http", "https");
        }
        return url;
    }

    private void checkParam(Map<String, String> param) {
        if (param == null) {
            param = new HashMap<>();
            return;
        }
        //可以在此处添加公共参数
//        if (!param.keySet().contains("imie")) {
//            param.put("imie", MiscUtils.getIMEI(CodeFram.mContext));
//        }
        for (String pa : param.keySet()) {
            if (param.get(pa) == null) {
                param.put(pa, "");
            }
        }
    }

    private HttpLoggingInterceptor loggingInterceptor;

    private void initOkHttp(final GCNetCallBack callBack) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //打印请求log日志
//        if (BuildConfig.DEBUG) {
//            if (loggingInterceptor == null) {
//                loggingInterceptor = new HttpLoggingInterceptor();
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            }
//            builder.addInterceptor(loggingInterceptor);
//        }

        // 缓存 http://www.jianshu.com/p/93153b34310e
//        File cacheFile = new File(AppUtil.getCacheDir(mContext), "httpCache");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!AppUtil.isNetworkConnected(CodeFram.mContext)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                } else if (AppUtil.isNetworkConnected(CodeFram.mContext)) {//网络可用
                    Request.Builder builder1 = request.newBuilder();
                    for (String key : headerParams.keySet()) {
                        builder1.header(key, headerParams.get(key) == null ? "" : headerParams.get(key));
                    }
                    request = builder1
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .build();
//                    Log.d("OkHttp", "网络可用请求拦截");
                }
                Response response = chain.proceed(request);
                if (AppUtil.isNetworkConnected(CodeFram.mContext)) {//如果网络可用
//                    Log.d("OkHttp", "网络可用响应拦截");
                    response = response.newBuilder()
                            //覆盖服务器响应头的Cache-Control,用我们自己的,因为服务器响应回来的可能不支持缓存
                            .header("Cache-Control", "public,max-age=" + 0)
                            .removeHeader("Pragma")
                            .build();
                } else {
//                    Log.d("OkHttp","网络不可用响应拦截");
                    response = response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxCacheTime)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;

            }
        };
//        builder.cache(cache);
//        builder.interceptors().add(cacheInterceptor);//添加本地缓存拦截器，用来拦截本地缓存
        builder.networkInterceptors().add(cacheInterceptor);//添加网络拦截器，用来拦截网络数据
        //设置头部
//        Interceptor headerInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request originalRequest = chain.request();
//                Request.Builder requestBuilder = originalRequest.newBuilder()
//                        .header("myhead", "myhead")
//                        .header("Content-Type", "application/json")
//                        .header("Accept", "application/json")
//                        .method(originalRequest.method(), originalRequest.body());
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        };
//        builder.addInterceptor(headerInterceptor );
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private void initRetrofit(String url, GCNetCallBack callBack) {
        if (okHttpClient == null) {
            initOkHttp(callBack);
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(StringConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

//    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
//        o.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s);
//    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
//    public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
//        @Override
//        public T call(HttpResult<T> httpResult) {
//            if (!httpResult.isSuccess()) {
//                throw new APIException(httpResult.code, httpResult.desc);
//            }
//            return httpResult.content;
//        }
//    }
}
