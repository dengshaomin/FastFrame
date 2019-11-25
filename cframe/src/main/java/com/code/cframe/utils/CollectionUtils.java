package com.code.cframe.utils;

import java.util.List;
import java.util.Map;

public class CollectionUtils {

    public boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public boolean isEmpty(Map list) {
        return list == null || list.size() == 0;
    }
}
