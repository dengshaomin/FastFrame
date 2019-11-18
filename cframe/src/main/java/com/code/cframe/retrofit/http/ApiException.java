package com.code.cframe.retrofit.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyangyang on 2018/6/13.
 * Desc:
 */
public class ApiException extends Exception {

    private String errorCode;

    private String errorMsg;

    private String extraData;

    private JSONObject extraDataMap;

    public ApiException(String code, String msg, String data) {
        this.errorCode = code;
        this.errorMsg = msg;
        this.extraData = data;
    }

    @Override
    public String getMessage() {
        return TextUtils.isEmpty(errorMsg) ? "" : errorMsg;
    }

    @NonNull
    public String getErrorCode() {
        if (TextUtils.isEmpty(errorCode)) {
            return "error code is empty";
        }
        return errorCode;
    }

    public String getExtraData() {
        return extraData;
    }

    /**
     * 如需获取接口失败返回的数据时，请重写 Bean 的 toString() 方法
     */
    public JSONObject getExtraJsonData() {
        if (extraData == null) {
            return null;
        }
        try {
            extraDataMap = new JSONObject(extraData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return extraDataMap;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("errorCode", errorCode);
            obj.put("errorMsg", errorMsg);
        } catch (JSONException e) {
        }
        return obj.toString();
    }
}
