package com.code.demo.demo.presents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.code.demo.demo.model.LogisticsModel;
import com.code.fastframe.ciface.IBasePresent;
import com.code.fastframe.presents.BasePresent;
import com.code.retrofit.http.ApiFactory;
import com.code.retrofit.http.HttpUtils;
import com.code.retrofit.http.cb.HttpCallBack;
import com.code.retrofit.http.exception.HttpException;
import com.code.retrofit.http.models.ServerModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class TestPresent extends BasePresent implements IBasePresent {

    private ITest mITest;

    private ApiTest mApiTest;

    public TestPresent(Context context, ITest test) {
        super(context);
        this.mITest = test;
        mApiTest = ApiFactory.createServerApi(ApiTest.class, "http://www.kuaidi100.com");
    }

    public void getLogistics() {
        Map<String, String> params = new HashMap<>();
        params.put("type", "yuantong");
        params.put("postid", "11111111111");
        HttpUtils.execute(mContext, mApiTest.getWeather(params), new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                if (mITest != null) {
//                    mITest.showData(data);
                }
            }

            @Override
            public void onError(HttpException httpException) {
                if (mITest != null) {
                    mITest.showError(httpException == null ? "" : httpException.getMsg());
                }
            }
        });
    }

    public interface ITest {

        void showData(List<LogisticsModel> data);

        void showError(String s);
    }

    interface ApiTest {

        @GET("/query")
        Observable<String> getWeather(@QueryMap Map<String, String> params);
    }


}