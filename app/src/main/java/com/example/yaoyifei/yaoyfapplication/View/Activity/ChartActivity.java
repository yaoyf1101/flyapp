package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.UserAnswer;
import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.adapter.AdapterYuntu;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ChartActivity extends Activity  {

    private TextView tips;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerview;
    private List<UserAnswer> mUserAnswers = null;
    private AdapterYuntu adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        context=this;
        tips = (TextView) findViewById(R.id.tips);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserAnswer();
            }
        });
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setAdapter(new AdapterYuntu(this,mUserAnswers));
        LinearLayoutManager mLlm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLlm);
    }
    //通过网络获取用户成绩信息
    private void getUserAnswer() {
        final String address = "http://47.102.199.28/flyapp/getUserAnswerServlet";
        HttpUtil.getQuestion(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Gson gson = new Gson();
                mUserAnswers = gson.fromJson(response,new TypeToken<List<UserAnswer>>(){}.getType());
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        adapter = new AdapterYuntu(context,mUserAnswers);
                        recyclerview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                        swipeRefresh.setEnabled(false);
                        tips.setVisibility(View.GONE);
                    }
                });
            }
            @Override
            public void onError(Exception e) {
                Looper.prepare();
                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
}
