package com.code.cframe.retrofit.http;

/**
 * Created by chengyangyang on 2018/12/26.
 * Desc: 接口数据返回 data = null； 会抛出该 exception；按需处理
 */
public class ApiNoDataException extends Exception {

    public ApiNoDataException(String message) {
        super(message);
    }

}
