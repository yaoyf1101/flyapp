package com.example.yaoyifei.yaoyfapplication.Entity;

import java.io.Serializable;

public class Question implements Serializable {

    /**
     * a :
     * score : 10
     * b :
     * c :
     * answer : java是个坑爹玩意儿
     * d :
     * t :
     * f :
     * id : 2
     * analysis : java是个坑爹玩意儿
     * title : 请问java是什么？
     * type : 简答题
     */

    private String a;
    private String score;//题目分值
    private String b;
    private String c;
    private String answer;//正确答案
    private String d;
    private String t;
    private String f;
    private int id;//题目id
    private String analysis;//答案解析
    private String title;// 题目描述
    private String type;//支持单选 多选 判断 填空 简答 该属性用来区分题型

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
