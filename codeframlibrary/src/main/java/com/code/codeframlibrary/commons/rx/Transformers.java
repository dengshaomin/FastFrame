package com.code.codeframlibrary.commons.rx;

public class Transformers {
    /**
     * 异步 do not use in Dao [data] layer
     */
    public static <T> AsyncTransformer<T> async() {
        return new AsyncTransformer<>();
    }
}
