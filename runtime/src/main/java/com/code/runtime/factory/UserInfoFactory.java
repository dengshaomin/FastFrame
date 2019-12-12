package com.code.runtime.factory;

import com.code.runtime.models.qq.QQLoginBean;
import com.code.runtime.models.qq.QQUserInfoBean;

public class UserInfoFactory {

    public QQLoginBean mQQLoginBean;

    public QQUserInfoBean mQQUserInfoBean;

    private static volatile UserInfoFactory infoFactory;

    public static UserInfoFactory getInstance() {
        if (infoFactory == null) {
            synchronized (UserInfoFactory.class) {
                if (infoFactory == null) {
                    infoFactory = new UserInfoFactory();
                }
            }
        }
        return infoFactory;
    }
}
