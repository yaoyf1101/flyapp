package com.example.yaoyifei.yaoyfapplication.SQLiteUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yaoyifei.yaoyfapplication.Entity.UsersInfo;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private com.example.yaoyifei.yaoyfapplication.SQLiteUtil.DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
    }

    /**
     * add UsersInfo List
     * */
    public void add(List<UsersInfo> usersInfos){
        db.beginTransaction();//开始事务
        for (UsersInfo usersInfo : usersInfos){
            db.execSQL("insert into usertable values(null,?,?)",new Object[]{
                    usersInfo.name,usersInfo.password
            });
        }
        db.setTransactionSuccessful();//设置事务完成
        db.endTransaction();//结束事务
    }

    /**
     * add UserInfo String.etc
     * */
    public void add(String username,String userpassword){
        db.beginTransaction();
        db.execSQL("insert into usertable values(null,?,?)",new Object[]{
                username,userpassword
        });
    }


    /**
     * query all userInfo return list
     * */
    public List<UsersInfo> query(){
        ArrayList<UsersInfo> usersInfos = new ArrayList<>();
        Cursor c  = queryTheCursor();
        while (c.moveToNext()){
            UsersInfo usersInfo = new UsersInfo();
            usersInfo._id = c.getInt(c.getColumnIndex("_id"));
            usersInfo.name = c.getString(c.getColumnIndex("name"));
            usersInfos.add(usersInfo);
        }
        c.close();
        return usersInfos;
    }

    /**
     * query all userInfo return cursor
     * */
    public Cursor queryTheCursor(){
        Cursor c = db.rawQuery("SELECT * FROM usertable", null);
        return c;
    }

    /**
     * close database
     * */
    public void closeDB(){
        db.close();
    }
}

