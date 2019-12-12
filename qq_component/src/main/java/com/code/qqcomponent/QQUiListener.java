package com.code.qqcomponent;

import com.andview.refreshview.utils.LogUtils;
import com.code.runtime.factory.UserInfoFactory;
import com.code.runtime.models.qq.QQLoginBean;
import com.code.runtime.models.qq.QQUserInfoBean;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import io.reactivex.schedulers.Schedulers;

public abstract class QQUiListener implements IUiListener {

}
