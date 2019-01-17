package com.huanxi.renrentoutiao.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;


/**
 * Created by Dinosa on 2018/1/31.
 */

public class GsonUtils {


    private static GsonUtils gsonUtils;
    private final Gson gson;

    private GsonUtils() {

        gson = new Gson();
    }

    public  static  GsonUtils getInstance(){

        if (gsonUtils==null) {
            synchronized (SharedPreferencesUtils.class){
                gsonUtils=new GsonUtils();
            }
        }
        return gsonUtils;
    }

    public String toString(Object object){
        String jsong = gson.toJson(object);
        return jsong;
    }

    public <T> T toBean(String str,Class<T> clazz){
        return gson.fromJson(str,clazz);
    }

    public <T> T toListBean(String str,Type clazz){
        return gson.fromJson(str,clazz);
    }



}
