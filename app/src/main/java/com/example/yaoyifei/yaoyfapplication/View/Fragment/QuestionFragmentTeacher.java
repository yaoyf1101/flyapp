package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.Question;
import com.example.yaoyifei.yaoyfapplication.Entity.UserAnswer;
import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragmentTeacher extends Fragment  {
    private Button previous,next,starttest,save;
    private TextView title;
    private TextView answer;
    private TextView analysis;
    private TextView countdowntimer;
    private LinearLayout torF;
    private LinearLayout radio;
    private LinearLayout answer_area;
    private LinearLayout analysis_area;
    private LinearLayout checkbox;
    private LinearLayout switch_area;
    private RadioGroup radioGroup;
    private RadioButton t;
    private RadioButton f;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;
    private CheckBox aa;
    private CheckBox bb;
    private CheckBox cc;
    private CheckBox dd;
    private EditText editText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Question> mQuestions = null;
    private int count;
    private int index;
    private int type;
    public List<UserAnswer> answers;
    final String address = "http://47.102.199.28/flyapp/getQuestionServlet";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getQuestion();
    }

    //通过网络获取题目
    private void getQuestion() {
        HttpUtil.getQuestion(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                mQuestions = gson.fromJson(response,new TypeToken<List<Question>>(){}.getType());
            }
            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //下拉刷新题库
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initAllView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //题目区域mQuestions.get(i).getAnswer()
        title = (TextView) view.findViewById(R.id.title);
        //判断题区域
        torF = (LinearLayout) view.findViewById(R.id.TorF);
        t = view.findViewById(R.id.T);
        f = view.findViewById(R.id.F);
        //单选题区域
        radio = (LinearLayout) view.findViewById(R.id.radio);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        a = (RadioButton) view.findViewById(R.id.A);
        b = (RadioButton) view.findViewById(R.id.B);
        c = (RadioButton) view.findViewById(R.id.C);
        d = (RadioButton) view.findViewById(R.id.D);
        //多选题区域
        checkbox = (LinearLayout) view.findViewById(R.id.checkbox);
        aa = (CheckBox) view.findViewById(R.id.AA);
        bb = (CheckBox) view.findViewById(R.id.BB);
        cc = (CheckBox) view.findViewById(R.id.CC);
        dd = (CheckBox) view.findViewById(R.id.DD);
        //简答和填空作答区域
        editText = view.findViewById(R.id.edit_area);
        //答案和解析区域（默认隐藏）
        answer_area = (LinearLayout) view.findViewById(R.id.answer_area);
        analysis_area = (LinearLayout) view.findViewById(R.id.analysis_area);
        analysis_area = (LinearLayout) view.findViewById(R.id.analysis_area);
        analysis = (TextView) view.findViewById(R.id.analysis);
        answer = (TextView) view.findViewById(R.id.answer);
        //切换题目和提交
        switch_area = (LinearLayout) view.findViewById(R.id.switch_area);
        next = (Button) view.findViewById(R.id.btn_next);
        previous = (Button) view.findViewById(R.id.btn_previous);
        starttest = (Button) view.findViewById(R.id.btn_start_test);
        save = view.findViewById(R.id.save_answer);
       // tips = view.findViewById(R.id.tips);
        //倒计时
        countdowntimer = view.findViewById(R.id.CountDownTimer);
    }


    //题目界面
    public void initAllView(){
        if (mQuestions != null) {
            count = mQuestions.size();//题目数量
            answers = new ArrayList<>(count);
            save.setVisibility(View.GONE);
            starttest.setVisibility(View.GONE);
            //初始化题库中的第一道题
            index = 0;
            Question question = mQuestions.get(index);
            type = getQuestionType(question);
            setAllViewFromType(type, question);
            swipeRefreshLayout.setEnabled(false);
            //切换题目的逻辑实现
            //下一题
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index < count-1) {
                        next.setEnabled(true);
                        previous.setEnabled(true);
                        index++;
                        Question qs = mQuestions.get(index);
                        type = getQuestionType(qs);
                        setAllViewFromType(type,qs);
                    } else {
                        next.setEnabled(false);
                        previous.setEnabled(true);
                        Toast.makeText(getActivity(), "最后一题了", Toast.LENGTH_SHORT).show();
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
                        Question qs = mQuestions.get(index);
                        type = getQuestionType(qs);
                        setAllViewFromType(type,qs);
                    }else {
                        previous.setEnabled(false);
                        next.setEnabled(true);
                        Toast.makeText(getActivity(), "已经是第一题了", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public int getQuestionType(Question question){
        if (question.getType().equals("单选题")){
            return 1;
        }else if (question.getType().equals("判断题")){
            return 2;
        }else if (question.getType().equals("主观题")){
            return 3;
        }else if (question.getType().equals("多选题")){
            return 4;
        }else {
            return 0;
        }
    }

    //根据不同的题目类型设置不同的界面
    public void setAllViewFromType(int type,Question question){
        switch (type) {
            case 1:
                title.setText(question.getTitle()+"("+ question.getScore()+"分"+")");
                torF.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                radio.setVisibility(View.VISIBLE);
                a.setText(question.getA());
                b.setText(question.getB());
                c.setText(question.getC());
                d.setText(question.getD());
                answer_area.setVisibility(View.VISIBLE);
                answer.setText(question.getAnswer());
                analysis_area.setVisibility(View.VISIBLE);
                analysis.setText(question.getAnalysis());
                break;
            case 2:
                title.setText(question.getTitle()+"("+ question.getScore()+"分"+")");
                torF.setVisibility(View.VISIBLE);
                radio.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                answer_area.setVisibility(View.VISIBLE);
                answer.setText(question.getAnswer());
                analysis_area.setVisibility(View.VISIBLE);
                analysis.setText(question.getAnalysis());
                break;
            case 3:
                title.setText(question.getTitle()+"("+ question.getScore()+"分"+")");
                editText.setVisibility(View.VISIBLE);
                torF.setVisibility(View.GONE);
                radio.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
                answer_area.setVisibility(View.VISIBLE);
                answer.setText(question.getAnswer());
                analysis_area.setVisibility(View.VISIBLE);
                analysis.setText(question.getAnalysis());
                break;
            case 4:
                title.setText(question.getTitle()+"("+ question.getScore()+"分"+")");
                torF.setVisibility(View.GONE);
                radio.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                checkbox.setVisibility(View.VISIBLE);
                answer_area.setVisibility(View.VISIBLE);
                answer.setText(question.getAnswer());
                analysis_area.setVisibility(View.VISIBLE);
                analysis.setText(question.getAnalysis());
                aa.setText(question.getA());
                bb.setText(question.getB());
                cc.setText(question.getC());
                dd.setText(question.getD());
                break;
            case 0:
                break;
        }
    }
}


