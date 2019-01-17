package com.zhxu.library.utils;

import com.google.gson.Gson;


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
            synchronized (GsonUtils.class){
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
}
