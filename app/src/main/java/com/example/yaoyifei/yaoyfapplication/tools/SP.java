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

    //保存教师出题的时间和分数
    public void write(int time,int score){
        SharedPreferences sp = mContext.getSharedPreferences("mysp1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("time",time);
        editor.putInt("score",score);
        editor.commit();
    }

    //读取教师出题的时间和分数
    public Map<String, Object> load() {
        Map<String, Object> data = new HashMap<String, Object>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp1", Context.MODE_PRIVATE);
        data.put("time", sp.getInt("time", 0));
        data.put("score", sp.getInt("score", 0));
        return data;
    }

    //保存学生的实际成绩
    public void writesScore(float score,float score1,float score2,float score3){
        SharedPreferences sp = mContext.getSharedPreferences("mysp2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("score",score);
        editor.putFloat("score1",score1);
        editor.putFloat("score2",score2);
        editor.putFloat("score3",score3);
        editor.commit();
    }

    //读取学生的实际成绩
    public Map<String, Object> loadScore() {
        Map<String, Object> data = new HashMap<String, Object>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp2", Context.MODE_PRIVATE);
        data.put("score", sp.getFloat("score", 0));
        data.put("score1", sp.getFloat("score1", 0));
        data.put("score2", sp.getFloat("score2", 0));
        data.put("score3", sp.getFloat("score3", 0));
        return data;
    }

    //保存题目分数
    public void setScore(float score,float score1,float score2,float score3){
        SharedPreferences sp = mContext.getSharedPreferences("mysp3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("score",score);
        editor.putFloat("score1",score1);
        editor.putFloat("score2",score2);
        editor.putFloat("score3",score3);
        editor.commit();
    }

    //读取题目分数
    public Map<String, Object> getScore() {
        Map<String, Object> data = new HashMap<String, Object>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp3", Context.MODE_PRIVATE);
        data.put("score", sp.getFloat("score", 0));
        data.put("score1", sp.getFloat("score1", 0));
        data.put("score2", sp.getFloat("score2", 0));
        data.put("score3", sp.getFloat("score3", 0));
        return data;
    }


    public void clear(){
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}