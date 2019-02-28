package com.example.yaoyifei.yaoyfapplication.tools;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
