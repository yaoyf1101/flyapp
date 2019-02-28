package com.example.yaoyifei.yaoyfapplication.SQLiteUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "Test.db";
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String usersInfo_table = "create table usertable" +
                "(id integer primary key autoincrement, username text," +
                "password text,usertype text)";
        String question_table = "create table question"+
                "(id INTEGER  PRIMARY KEY AUTOINCREMENT , name TEXT NOT NULL, " +
                "a TEXT, b TEXT, c TEXT, d TEXT, answer TEXT, note TEXT )";
        db.execSQL(usersInfo_table);
        db.execSQL(question_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table usertable");
        db.execSQL("drop table question");
        String usersInfo_table = "create table usertable" +
                "(id integer primary key autoincrement, username text," +
                "password text,usertype text)";
        String question_table = "create table question"+
                "(id INTEGER  PRIMARY KEY AUTOINCREMENT , name TEXT NOT NULL, " +
                "a TEXT, b TEXT, c TEXT, d TEXT, answer TEXT, note TEXT )";
        db.execSQL(usersInfo_table);
        db.execSQL(question_table);
    }
}
