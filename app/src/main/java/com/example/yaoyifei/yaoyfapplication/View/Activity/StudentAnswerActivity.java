package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.UserAnswer;
import com.example.yaoyifei.yaoyfapplication.Entity.UserGrade;
import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.example.yaoyifei.yaoyfapplication.tools.JsonUtil;
import com.example.yaoyifei.yaoyfapplication.tools.SP;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class StudentAnswerActivity extends Activity  {

    private TextView tips;
    private SwipeRefreshLayout swipeRefresh;
    public  TextView mTitle;
    public  TextView mUserName;
    public  TextView mAnswer;
    public  TextView mScore;
    public  TextView mAssess;
    public  Button next;
    public  Button previous;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout1;
    private List<UserAnswer> mUserAnswers = null;
    public UserAnswer userAnswer = new UserAnswer();
    public UserGrade userGrade = new UserGrade();
    private Context context;
    private SP mSp;
    private int count;
    private int index;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_answer);
        context=this;
        mSp = new SP(context);
        tips = (TextView) findViewById(R.id.tips);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserAnswer();
                Map<String,Object> data =mSp.load();
                username=data.get("name").toString();
                userAnswer.setUsername(username);
                commitAnswer(userAnswer);
            }
        });
        mTitle = findViewById(R.id.title);
        mUserName = findViewById(R.id.user_name);
        mAnswer = findViewById(R.id.user_answer);
        mScore = findViewById(R.id.score);
        mAssess = findViewById(R.id.assess);
        next = findViewById(R.id.btn_next);
        previous = findViewById(R.id.btn_previous);
        linearLayout = findViewById(R.id.linear_layout);
        linearLayout1 = findViewById(R.id.switch_area);
        linearLayout.setVisibility(View.INVISIBLE);
        linearLayout1.setVisibility(View.INVISIBLE);
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
                        initView();
                        swipeRefresh.setRefreshing(false);
                        swipeRefresh.setEnabled(false);
                        tips.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout1.setVisibility(View.VISIBLE);
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

    public void initView() {
        if (mUserAnswers != null && mUserAnswers.size() > 0) {
            count = mUserAnswers.size();//题目数量
            //初始化题库中的第一道题
            index = 0;
            UserAnswer userAnswer = mUserAnswers.get(index);
            mTitle.setText(userAnswer.getTitle());
            mUserName.setText(userAnswer.getUsername());
            mAnswer.setText(userAnswer.getUseranswer());
            mScore.setText(userAnswer.getActualscore());
            mAssess.setText(userAnswer.getTeacherassess());
            //下一题
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index < count-1) {
                        next.setEnabled(true);
                        previous.setEnabled(true);
                        index++;
                        UserAnswer userAnswer = mUserAnswers.get(index);
                        setView(userAnswer);
                    } else {
                        next.setEnabled(false);
                        previous.setEnabled(true);
                        Toast.makeText(context, "最后一题了", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //上一题
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index > 0){
                        previous.setEnabled(true);
                        next.setEnabled(true);
                        index--;
                        UserAnswer userAnswer = mUserAnswers.get(index);
                        setView(userAnswer);
                    }else {
                        previous.setEnabled(false);
                        next.setEnabled(true);
                        Toast.makeText(context, "已经是第一题了", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(context, "学生还未提交答案", Toast.LENGTH_SHORT).show();
        }
    }

    private void setView(UserAnswer userAnswer) {
        mTitle.setText(userAnswer.getTitle());
        mUserName.setText(userAnswer.getUsername());
        mAnswer.setText(userAnswer.getUseranswer());
        mScore.setText(userAnswer.getActualscore());
        mAssess.setText(userAnswer.getTeacherassess());
    }

    //提交主观题答案信息
    public void commitAnswer(UserAnswer userAnswer){
        final String address = "http://47.102.199.28/flyapp/getScore";
        final String json = JsonUtil.converJavaBeanToJson(userAnswer);
        HttpUtil.sendQuestion(address, json, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Looper.prepare();
                //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                Map<String,Object> data =mSp.load();
                username=data.get("name").toString();
                userGrade.setUsername(username);
                userGrade.setScorezg(Integer.parseInt(response));
                commitGrade(userGrade);
                Looper.loop();
            }

            @Override
            public void onError(Exception e) {
                Looper.prepare();
                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
    //提交成绩信息
    public void commitGrade(UserGrade userGrade){
        final String address = "http://47.102.199.28/flyapp/addUserGradeFromClient";
        final String json = JsonUtil.converJavaBeanToJson(userGrade);
        HttpUtil.sendQuestion(address, json, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Looper.prepare();
                Toast.makeText(context, "主观题成绩更新完成", Toast.LENGTH_SHORT).show();
                Looper.loop();
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
