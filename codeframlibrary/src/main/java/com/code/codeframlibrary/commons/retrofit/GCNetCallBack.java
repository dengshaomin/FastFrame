package com.code.codeframlibrary.commons.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dengshaomin on 2016/11/28.
 */
public class GCNetCallBack<T> implements Callback<T> {
    public static final String Success_Code = "A00000";
    private final String Error_String = "服务器异常!";
    private NetInterface netInterface;
    private boolean fromCache = false;

    public boolean isFromCache() {
        return fromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }

    public GCNetCallBack(String indentify, NetInterface netInterface) {
        setIndentify(indentify);
        this.netInterface = netInterface;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null && response.raw() != null && response.raw().code() == 200) {//200是服务器有合理响应
//            String code = Constant.UNKONWERROR;
//            String msg = Error_String;
//            String data = null;
//            try {
//                JSONObject jsonObject = new JSONObject();
//                if (response != null && response.body() != null)
//                    jsonObject = new JSONObject(response.body().toString());
//                if (jsonObject.has("code")) {
//                    code = jsonObject.getString("code");
//                }
//                if (jsonObject.has("msg")) {
//                    msg = jsonObject.getString("msg");
//                }
//                if (jsonObject.has("data")) {
//                    data = jsonObject.getString("data");
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            if (response.body() == null || response.body().toString().length() < 5) {
                onError(Constant.UNKONWERROR + "", Error_String);
            } else {
//                if (Success_Code.equals(code)) {
//                    if (data == null || "".equals(data)) {
//                        onError(Constant.NULL_ERROR, Error_String);
//                    } else {
                onSuccess(Constant.SUCCESS, response.body().toString());
//                    }
//                } else {
//                    onError(code, msg);
//                }
            }
        } else {
            onError(Constant.UNKONWERROR + "", Error_String);
        }
    }

    private String indentify = "";

    private void setIndentify(String indentify) {
        this.indentify = indentify;
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        onError(Constant.UNKONWERROR + "", throwable.getMessage());
    }

    private void onSuccess(String code, String response) {
        if (netInterface != null) {
            netInterface.onSuccess(indentify, code, response);
        }
    }

    private void onError(String code, String message) {
        if (netInterface != null) {
            netInterface.onError(indentify, code, message);
        }
    }
}
