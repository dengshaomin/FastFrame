package com.code.codefram.demo.presents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.code.cframe.ciface.IBasePresent;
import com.code.cframe.presents.BasePresent;
import com.code.codefram.demo.model.LogisticsModel;
import com.code.rxretrofitlibrary.http.HttpUtils;
import com.code.rxretrofitlibrary.http.RetrofitHttpUtil;
import com.code.rxretrofitlibrary.http.cb.HttpCallBack;
import com.code.rxretrofitlibrary.http.exception.HttpException;
import com.code.rxretrofitlibrary.http.models.ServerModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class TestPresent extends BasePresent implements IBasePresent {

    private ITest mITest;

    private ApiTest mApiTest;

    public TestPresent(Context context, ITest test) {
        super(context);
        this.mITest = test;
        mApiTest = RetrofitHttpUtil.createServerApi(ApiTest.class, "http://www.kuaidi100.com");
    }

    public void getLogistics() {
        Map<String, String> params = new HashMap<>();
        params.put("type", "yuantong");
        params.put("postid", "11111111111");
        HttpUtils.executeObject(mContext, mApiTest.getWeather(params), new HttpCallBack<List<LogisticsModel>>() {
            @Override
            public void onSuccess(List<LogisticsModel> data) {
                if (mITest != null) {
                    mITest.showData(data);
                }
            }

            @Override
            public void onError(HttpException httpException) {
                if (mITest != null) {
                    mITest.showError(httpException == null ? "" : httpException.getMessage());
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
        Observable<ServerModel<List<LogisticsModel>>> getWeather(@QueryMap Map<String, String> params);
    }


}