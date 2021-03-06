package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.Question;
import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.View.Activity.TeacherHomeActivity;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.example.yaoyifei.yaoyfapplication.tools.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SetQuestionFragment extends Fragment implements View.OnClickListener {

    private RadioButton danxuan;
    private RadioButton duoxuan;
    private RadioButton panduan;
    private RadioButton tiankongorjianda;
    private RadioGroup  mRadioGroup;
    private EditText title,a,b,c,d,answer,analysis,score,time;
    private WebView webView;
    private ScrollView scrollView;
    private Button btn_back,reload,generatetest;
    private LinearLayout function_area;
    private LinearLayout abcd;
    private String Type = "";
    private List<Question> mQuestions = null;
    final String address = "http://47.102.199.28/flyapp/getQuestionServlet";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_question, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRadioGroup = view.findViewById(R.id.radio_group);
        danxuan = (RadioButton) view.findViewById(R.id.danxuan);
        duoxuan = (RadioButton) view.findViewById(R.id.duoxuan);
        panduan = (RadioButton) view.findViewById(R.id.panduan);
        tiankongorjianda = (RadioButton) view.findViewById(R.id.tiankongorjianda);
        //判断题型
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(danxuan.isChecked()==true){
                    Type = "单选题";
                    abcd.setVisibility(View.VISIBLE);
                }
                if(duoxuan.isChecked()==true){
                    Type = "多选题";
                    abcd.setVisibility(View.VISIBLE);
                }
                if(panduan.isChecked()==true){
                    Type = "判断题";
                    abcd.setVisibility(View.GONE);
                }
                if(tiankongorjianda.isChecked()==true){
                    Type = "主观题";
                    abcd.setVisibility(View.GONE);
                }
            }
        });
        view.findViewById(R.id.addQuestion).setOnClickListener(this);
        view.findViewById(R.id.getQuestion).setOnClickListener(this);

        title = (EditText) view.findViewById(R.id.title);
        a = (EditText) view.findViewById(R.id.A);
        b = (EditText) view.findViewById(R.id.B);
        c = (EditText) view.findViewById(R.id.C);
        d = (EditText) view.findViewById(R.id.D);
        answer = (EditText) view.findViewById(R.id.answer);
        analysis = (EditText) view.findViewById(R.id.analysis);
        score = (EditText) view.findViewById(R.id.score);
        time = view.findViewById(R.id.time);

        webView = view.findViewById(R.id.web_view);

        scrollView = view.findViewById(R.id.scrollView);

        function_area = view.findViewById(R.id.function_area);
        btn_back = view.findViewById(R.id.back);
        reload = view.findViewById(R.id.reload);
        generatetest = view.findViewById(R.id.generate_test);
        abcd = view.findViewById(R.id.linear_layout2);
        abcd.setVisibility(View.GONE);
        btn_back.setOnClickListener(this);
        reload.setOnClickListener(this);
        generatetest.setOnClickListener(this);
    }
    //通过网络获取题目
    private void getQuestion() {
        HttpUtil.getQuestion(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                mQuestions = gson.fromJson(response,new TypeToken<List<Question>>(){}.getType());
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        if (mQuestions != null && mQuestions.size()>0) {
                            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                            //    设置Title的内容
                            builder2.setTitle("测试已生成");
                            //    设置Content来显示一个信息
                            builder2.setMessage("是否跳转到预览界面预览题目");
                            //    设置一个PositiveButton
                            builder2.setPositiveButton("跳转", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ViewPager vp = TeacherHomeActivity.getmViewPager();
                                    vp.setCurrentItem(2);
                                }
                            });
                            //    设置一个NegativeButton
                            builder2.setNegativeButton("取消", null);
                            //    显示出该对话框
                            builder2.show();
                        }else{
                            Toast.makeText(getActivity(), "题库没有题目", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onError(Exception e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addQuestion:
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //    设置Title的内容
                builder.setTitle("是否要保存添加的题目？");
                //    设置Content来显示一个信息
                builder.setMessage("确定保存吗？");
                //    设置一个PositiveButton
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        addQuestion();
                    }
                });
                //    设置一个NegativeButton
                builder.setNegativeButton("取消", null);
                //    显示出该对话框
                builder.show();
                break;
            case R.id.getQuestion:
                scrollView.setVisibility(View.GONE);
                function_area.setVisibility(View.GONE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl("http://47.102.199.28/flyapp/listQuestion");
                webView.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.VISIBLE);
                reload.setVisibility(View.VISIBLE);
                break;
            case R.id.back:
                scrollView.setVisibility(View.VISIBLE);
                function_area.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                btn_back.setVisibility(View.GONE);
                break;
            case R.id.reload:
                webView.reload();
                break;
            case R.id.generate_test:
                getQuestion();
                break;
        }
    }

    public void addQuestion() {
        String Title = title.getText().toString();
        String A = a.getText().toString();
        String B = b.getText().toString();
        String C = c.getText().toString();
        String D = d.getText().toString();
        String Answer = answer.getText().toString();
        String Analysis = analysis.getText().toString();
        String Score = score.getText().toString();
        String Time = time.getText().toString();
        if (TextUtils.isEmpty(Title)) {
            Toast.makeText(getActivity(), "请输入题目名称", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Answer)){
            Toast.makeText(getActivity(), "请输入正确答案", Toast.LENGTH_SHORT).show();
        }else  if (TextUtils.isEmpty(Analysis)){
            Toast.makeText(getActivity(), "请输入答案解析", Toast.LENGTH_SHORT).show();
        }else if (Type.equals("")) {
            Toast.makeText(getActivity(), "请选择题目类型", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(time.getText().toString())) {
            Toast.makeText(getActivity(), "请输入该题所需时长", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Score)) {
            Toast.makeText(getActivity(), "请输入该题分值", Toast.LENGTH_SHORT).show();
        }else{
            final String address = "http://47.102.199.28/flyapp/addQuestionFromClient";
            Question question = new Question();
            question.setTitle(Title);
            question.setAnswer(Answer);
            question.setAnalysis(Analysis);
            question.setType(Type);
            question.setScore(Score);
            question.setTime(Time);//题目用时
            question.setA(A);
            question.setB(B);
            question.setC(C);
            question.setD(D);
            final String json = JsonUtil.converJavaBeanToJson(question);
            HttpUtil.sendQuestion(address, json, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Looper.prepare();
                    Toast.makeText(getActivity(), "添加题目成功", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                @Override
                public void onError(Exception e) {
                    Looper.prepare();
                    Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //    设置Title的内容
            builder.setTitle("清除题目信息");
            //    设置Content来显示一个信息
            builder.setMessage("是否清除题目信息,编辑下一题？");
            //    设置一个PositiveButton
            builder.setPositiveButton("是", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    ClearData();
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("否", null);
            //    显示出该对话框
            builder.show();
        }
    }

    public void ClearData(){
        title.setText("");
        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");
        analysis.setText("");
        answer.setText("");
        score.setText("");
        time.setText("");
    }

}
