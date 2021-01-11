package com.code.fastframe.retrofit.cb;

import com.code.fastframe.retrofit.error.HttpError;

public interface HttpCallBack<T> {

    void onSuccess(T data);

    void onError(HttpError httpException);
}
