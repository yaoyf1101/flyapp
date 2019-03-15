package com.example.yaoyifei.yaoyfapplication.tools;

import android.text.TextUtils;

import com.google.gson.Gson;

public class JsonUtil {

    //将JSON字符串转换成javabean
    public static <T> T parsr(String json ,Class<T> tClass){
        //判读字符串是否为空
        if(TextUtils.isEmpty(json)){
            return null;
        }
        Gson gson=null;
        if(gson==null){
            gson = new Gson();
        }
        return gson.fromJson(json,tClass);
    }
    //将javabean转换成JSON字符串
    public static String converJavaBeanToJson(Object obj){
        Gson gson=null;
        if(obj == null){
            return "";
        }
        if(gson == null){
            gson = new Gson();
        }
        String beanstr = gson.toJson(obj);
        if(!TextUtils.isEmpty(beanstr)){
            return beanstr;
        }
        return "";
    }
}
