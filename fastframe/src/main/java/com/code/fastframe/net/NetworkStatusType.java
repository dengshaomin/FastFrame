package com.code.fastframe.net;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Created by lijinwei on 1/2/18.
 */

public class NetworkStatusType {
    public final static int NETWORK_STATUS_NO = 0;
    public final static int NETWORK_STATUS_MOBILE = 1;
    public final static int NETWORK_STATUS_WIFI = 2;

    @IntDef({NETWORK_STATUS_NO, NETWORK_STATUS_MOBILE, NETWORK_STATUS_WIFI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkStatus {
    }

}
