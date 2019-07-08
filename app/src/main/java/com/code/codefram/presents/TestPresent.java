package com.code.codefram.presents;

import com.code.codefram.model.LoginModel;
import com.code.codefram.model.WeatherJson;
import com.code.codeframlibrary.commons.ciface.IBasePresent;
import com.code.codeframlibrary.commons.retrofit.RetrofitHttpUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class TestPresent implements IBasePresent {

    private ITest mITest;

    private Disposable mDisposables;

    private ApiTest mApiTest;

    public TestPresent(ITest test) {
        this.mITest = test;
        mApiTest = RetrofitHttpUtil.getInstance().getRetrofit().create(ApiTest.class);
    }

    @Override
    public void destory() {
        if (mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    public void getWeather(String cityId) {
        mApiTest.getWeather(cityId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LoginModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposables = d;
            }

            @Override
            public void onNext(LoginModel weatherJson) {
                if (mITest != null) {
                    mITest.showData(weatherJson);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mITest != null) {
                    mITest.showError(e.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public interface ITest {

        void showData(LoginModel data);

        void showError(String s);
    }

    interface ApiTest {

        @GET("login")
        Observable<LoginModel> getWeather(@Query("params") String qipuId);
    }


}