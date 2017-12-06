package com.code.codeframlibrary.commons.retrofit.services;


import java.util.Map;


import com.code.codeframlibrary.commons.retrofit.QuestionApi;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by dengshaomin on 2017/1/18.
 */
public interface QuestionService<T> {

    @GET("oauth2/access_token")
    Call<String> getWxToken(@QueryMap Map<String, String> param);

    @GET("userinfo")
    Call<String> getWxUserInfo(@QueryMap Map<String, String> param);



    @GET(QuestionApi.GiftHome)
    Call<String> getGiftHome(@QueryMap Map<String, String> param);

    @GET(QuestionApi.Home)
    Call<String> getHome(@QueryMap Map<String, String> param);

    @GET(QuestionApi.GameCenterHome)
    Call<String> getGameCenterHome(@QueryMap Map<String, String> param);

    @GET(QuestionApi.Sign)
    Call<String> sign(@QueryMap Map<String, String> param);

    @GET("mine/bookedMarkdownload")
    Call<String> markDownload(@QueryMap Map<String, String> param);

    @GET("mine/gameBooked")
    Call<String> userGameBooked(@QueryMap Map<String, String> param);

    @GET("yx/gyxzx")
    Call<String> lunchTime(@QueryMap Map<String, String> param);

    @GET("gamecenter/index.php")
    Call<String> queryMyPrize(@QueryMap Map<String, String> param);

    @GET("gamecenter/index.php")
    Call<String> savePrizeAdress(@QueryMap Map<String, String> param);



    @FormUrlEncoded
    @POST("mobile_login.action")
    Call<String> login(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("login.action")
    Call<String> yalogin(@FieldMap Map<String, String> param);

}
