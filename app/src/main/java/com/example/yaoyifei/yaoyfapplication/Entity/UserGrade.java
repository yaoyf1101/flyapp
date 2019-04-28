package com.example.yaoyifei.yaoyfapplication.Entity;

public class UserGrade {
    private int id;
    private String username;
    private int scorezg;
    private int scoreduox;
    private int scoredanx;
    private int scorepd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usename) {
        this.username = usename;
    }

    public int getScorezg() {
        return scorezg;
    }

    public void setScorezg(int scorezg) {
        this.scorezg = scorezg;
    }

    public int getScoreduox() {
        return scoreduox;
    }

    public void setScoreduox(int scoreduox) {
        this.scoreduox = scoreduox;
    }

    public int getScoredanx() {
        return scoredanx;
    }

    public void setScoredanx(int scoredanx) {
        this.scoredanx = scoredanx;
    }

    public int getScorepd() {
        return scorepd;
    }

    public void setScorepd(int scorepd) {
        this.scorepd = scorepd;
    }

    @Override
    public String toString() {
        return "UserGrade{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", scorezg=" + scorezg +
                ", scoreduox=" + scoreduox +
                ", scoredanx=" + scoredanx +
                ", scorepd=" + scorepd +
                '}';
    }
}
