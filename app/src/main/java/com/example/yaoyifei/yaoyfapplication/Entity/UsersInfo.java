package com.example.yaoyifei.yaoyfapplication.Entity;

public class UsersInfo {
    public int _id;
    public String name;
    public String password;

    public UsersInfo(){

    }

    public UsersInfo(String name){
        this.name = name;
    }

    public UsersInfo(String name,String password){
        this.name = name;
        this.password = password;
    }

}
