package com.example.yaoyifei.yaoyfapplication.Entity;

public class UserAnswer {
    private int id;
    private String username;//用户名字
    private String title;//题目
    private String actualscore;//主观题实际得分
    private String useranswer;//用户答案
    private String teacherassess;//老师评语

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActualscore() {
        return actualscore;
    }

    public void setActualscore(String actualscore) {
        this.actualscore = actualscore;
    }

    public String getUseranswer() {
        return useranswer;
    }

    public void setUseranswer(String useranswer) {
        this.useranswer = useranswer;
    }

    public String getTeacherassess() {
        return teacherassess;
    }

    public void setTeacherassess(String teacherassess) {
        this.teacherassess = teacherassess;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", actualscore='" + actualscore + '\'' +
                ", useranswer='" + useranswer + '\'' +
                ", teacherassess='" + teacherassess + '\'' +
                '}';
    }
}
