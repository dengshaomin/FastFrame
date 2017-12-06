package com.code.codeframlibrary.commons.retrofit.services;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by hjzhang on 2016/7/26.
 */
public interface CommonService<T> {

    @GET()
    Call<String> get(@Url String url, @QueryMap Map<String, String> param);

    @FormUrlEncoded
    @POST()
    Call<String> post(@Url String url, @FieldMap Map<String, String> params);

    @POST
    Call<String> post(@Url String url, @Body RequestBody body);
}
