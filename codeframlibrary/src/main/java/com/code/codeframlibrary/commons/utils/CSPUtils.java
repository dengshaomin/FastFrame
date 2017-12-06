package com.code.codeframlibrary.commons.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 目前发现apply可能出现插件环境下不能及时保存数据的问题，在插件主页退出时加上commit逻辑
 */


public class CSPUtils {


    private static final String PREFERENCE_NAME = "sp.utils";

    private static CSPUtils instance;

    private SharedPreferences sharedPreferences;

    private Editor editor;

    public CSPUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public static synchronized CSPUtils getInstance(Context context) {
        if (instance == null) {
            instance = new CSPUtils(context);
        }
        return instance;
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object).apply();
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object).apply();
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object).apply();
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object).apply();
        } else {
            editor.putString(key, object.toString()).apply();
        }
    }

    public void commit() {
        editor.commit();
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key 键的值
     * @param defaultObject 默认值
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }

    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        editor.remove(key).apply();
    }

    /**
     * 清除所有的数据
     */
    public void clear() {
        editor.clear().apply();
    }

    /**
     * 查询某个key是否存在
     */
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

}
