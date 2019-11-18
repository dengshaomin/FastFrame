package com.code.codefram.presents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.code.codefram.model.LogisticsModel;
import com.code.cframe.ciface.IBasePresent;
import com.code.cframe.model.ServerCommonModel;
import com.code.cframe.retrofit.RetrofitHttpUtil;
import com.code.cframe.rx.Transformers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class TestPresent implements IBasePresent {

    private ITest mITest;

    private Disposable mDisposables;

    private ApiTest mApiTest;

    public TestPresent(ITest test) {
        this.mITest = test;
        mApiTest = RetrofitHttpUtil.createServerApi(ApiTest.class, "http://www.kuaidi100.com");
    }

    @Override
    public void destory() {
        if (mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    public void getLogistics() {
        Map<String, String> params = new HashMap<>();
        params.put("type", "yuantong");
        params.put("postid", "11111111111");
        RetrofitHttpUtil.call(mApiTest.getWeather(params))
                .compose(Transformers.async())
                .subscribe(new Observer<List<LogisticsModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LogisticsModel> logisticsModels) {
                        if (mITest != null) {
                            mITest.showData(logisticsModels);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mITest != null) {
                            mITest.showError(e == null ? "" : e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface ITest {

        void showData(List<LogisticsModel> data);

        void showError(String s);
    }

    interface ApiTest {

        @GET("/query")
        Call<ServerCommonModel<List<LogisticsModel>>> getWeather(@QueryMap Map<String, String> params);
    }


}