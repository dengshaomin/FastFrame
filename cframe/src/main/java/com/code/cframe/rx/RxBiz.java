package com.code.cframe.rx;

import android.support.annotation.Nullable;

import io.reactivex.disposables.Disposable;
public class RxBiz {
    public static void dispose(@Nullable Disposable disposable) {
        if (isNotDisposed(disposable)) {
            disposable.dispose();
        }
    }

    public static boolean isNotDisposed(@Nullable Disposable disposable) {
        return disposable != null && !disposable.isDisposed();
    }
}
