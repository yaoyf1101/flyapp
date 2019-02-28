package com.example.yaoyifei.yaoyfapplication.tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SP {

    private Context mContext;

    public SP(Context mContext) {
        this.mContext = mContext;
    }
    //定义一个保存数据的方法
    public void save(String username, String password,boolean isStudent,boolean isTeacher,boolean isRemember,boolean isAutoLogin) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("isStudent",isStudent);
        editor.putBoolean("isTeacher",isTeacher);
        editor.putBoolean("isRemember",isRemember);
        editor.putBoolean("isAutoLogin",isAutoLogin);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public Map<String, Object> read() {
        Map<String, Object> data = new HashMap<String, Object>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("password", sp.getString("password", ""));
        data.put("isStudent",sp.getBoolean("isStudent",true));
        data.put("isTeacher",sp.getBoolean("isTeacher",false));
        data.put("isRemember",sp.getBoolean("isRemember",false));
        data.put("isAutoLogin",sp.getBoolean("isAutoLogin",false));
        return data;

    }

    public void clear(){
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}