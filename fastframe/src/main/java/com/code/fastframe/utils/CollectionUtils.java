package com.code.fastframe.utils;

import java.util.List;
import java.util.Map;

public class CollectionUtils {

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNull(List list) {
        return list == null;
    }

    public static boolean isEmpty(Map list) {
        return list == null || list.size() == 0;
    }
}
