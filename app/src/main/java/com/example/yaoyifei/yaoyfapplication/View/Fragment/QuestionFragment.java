package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.Question;
import com.example.yaoyifei.yaoyfapplication.Entity.UserAnswer;
import com.example.yaoyifei.yaoyfapplication.Entity.UserGrade;
import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.View.Activity.HomeActivity;
import com.example.yaoyifei.yaoyfapplication.tools.FileUtil;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.example.yaoyifei.yaoyfapplication.tools.JsonUtil;
import com.example.yaoyifei.yaoyfapplication.tools.SP;
import com.example.yaoyifei.yaoyfapplication.tools.SimilarityUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionFragment extends Fragment  {
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
    private RadioGroup radioGroup,radioGroupTF;
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
    //更新界面 20190428 begin
    private LinearLayout switcharea;
    private ScrollView scrollView;
    private TextView tips;
    //更新界面 20190428 end
    private List<Question> mQuestions = null;
    private int count;
    private int index;
    private int type;
    public List<UserAnswer> answers;
    public UserGrade userGrade = new UserGrade();
    final String address = "http://47.102.199.28/flyapp/getQuestionServlet";

    private Context mContext;
    private SP mSp;
    private FileUtil fileUtil;
    private Boolean isBegin = false;
    private Boolean isEnd = false;
    public static Boolean isSave = false;
    private Boolean isCommit = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mSp = new SP(mContext);
        fileUtil = new FileUtil(mContext);
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
                        initView();
                        swipeRefreshLayout.setRefreshing(false);
                        scrollView.setVisibility(View.VISIBLE);
                        switcharea.setVisibility(View.VISIBLE);
                        tips.setVisibility(View.GONE);
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
                getQuestion();
            }
        });
        //题目区域mQuestions.get(i).getUseranswer()
        title = (TextView) view.findViewById(R.id.title);
        //判断题区域
        torF = (LinearLayout) view.findViewById(R.id.TorF);
        radioGroupTF = view.findViewById(R.id.radio_group_tf);
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
        next = (Button) view.findViewById(R.id.btn_next);
        previous = (Button) view.findViewById(R.id.btn_previous);
        starttest = (Button) view.findViewById(R.id.btn_start_test);
        save = view.findViewById(R.id.save_answer);
        //倒计时
        countdowntimer = view.findViewById(R.id.CountDownTimer);
        tips = view.findViewById(R.id.tips);
        tips.setVisibility(View.VISIBLE);
        scrollView = view.findViewById(R.id.scrollView);//更新界面
        scrollView.setVisibility(View.INVISIBLE);
        switcharea = view.findViewById(R.id.switch_area);//更新界面
        switcharea.setVisibility(View.INVISIBLE);
    }

    public void initView(){
        if (mQuestions != null && mQuestions.size()>0) {
            count = mQuestions.size();//题目数量
            answers = new ArrayList<>(count);
            //初始化题库中的第一道题
            index = 0;
            Question question = mQuestions.get(index);
            type = getQuestionType(question);
            setViewFromType(type, question);
            swipeRefreshLayout.setEnabled(false);
            //切换题目的逻辑实现
            //下一题
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBegin&&!isEnd) {
                        if (index < count - 1) {
                            next.setEnabled(true);
                            previous.setEnabled(true);
                            Question question = mQuestions.get(index);
                            String title = question.getTitle();
                            type = getQuestionType(question);
                            getYouAnswerFromType(index, type,title);
                            clearSelect();
                            index++;
                            Question qs = mQuestions.get(index);
                            type = getQuestionType(qs);
                            setViewFromType(type, qs);
                        } else {
                            next.setEnabled(false);
                            previous.setEnabled(true);
                            Toast.makeText(getActivity(), "最后一题了", Toast.LENGTH_SHORT).show();
                            Question question = mQuestions.get(count - 1);
                            type = getQuestionType(question);
                            String title = question.getTitle();
                            getYouAnswerFromType(count - 1, type,title);
                        }
                    }else {
                        Toast.makeText(getActivity(), "考试未开始或者考试已结束", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //上一题
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBegin && !isEnd) {
                        if (index > 0) {
                            previous.setEnabled(true);
                            next.setEnabled(true);
                            clearSelect();
                            index--;
                            Question qs = mQuestions.get(index);
                            type = getQuestionType(qs);
                            setViewFromType(type, qs);
                        } else {
                            previous.setEnabled(false);
                            next.setEnabled(true);
                            Toast.makeText(getActivity(), "已经是第一题了", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "考试未开始或者考试已结束", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //开始考试
            starttest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //具体的数值由教师端提供
                    isBegin=true;
                    starttest.setEnabled(false);
                    int time = getTime();
                    setTimer(time);
                    UserAnswer userAnswer = new UserAnswer();
                    Map<String,Object> data = mSp.load();//获取登录的用户信息
                    userAnswer.setUsername(data.get("name").toString());
                    clearAnswer(userAnswer);
                }
            });
            //保存提交
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setMessage("是否查看考试结果");
                    dialog.setTitle("提交成功");
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // Toast.makeText(getActivity(), showAnswers(), Toast.LENGTH_SHORT).show();
                            isEnd=true;
                            isSave=true;
                            next.setEnabled(true);
                            save.setEnabled(false);
                            getScoreFromAnswer();
                            checkAnswerToScore();
                            ChartFragment.showBarChartMore();
                           //ChartFragment.showYunTu();
                            ViewPager vp = HomeActivity.getmViewPager();
                            vp.setCurrentItem(2);
                        }
                    });
                    dialog.setNegativeButton("否",null);
                    dialog.show();
                }
            });
        }else{
            Toast.makeText(getActivity(), "题库为空,请添加题目后再次刷新", Toast.LENGTH_SHORT).show();
        }
    }

    //测试答案是否提交正确
    public String  showAnswers() {
        String result = "";
        for(int i=0;i<answers.size();i++){
            if(answers.get(i)!=null){
                result=result+answers.get(i).toString();
            }
        }
        return result;
    }

    //获取题目类型
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
    public void setViewFromType(int type,Question question){
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
                break;
            case 2:
                title.setText(question.getTitle()+"("+ question.getScore()+"分"+")");
                torF.setVisibility(View.VISIBLE);
                radio.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                break;
            case 3:
                title.setText(question.getTitle()+"("+ question.getScore()+"分"+")");
                editText.setVisibility(View.VISIBLE);
                torF.setVisibility(View.GONE);
                radio.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
                break;
            case 4:
                title.setText(question.getTitle()+"("+ question.getScore()+"分"+")");
                torF.setVisibility(View.GONE);
                radio.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                checkbox.setVisibility(View.VISIBLE);
                aa.setText(question.getA());
                bb.setText(question.getB());
                cc.setText(question.getC());
                dd.setText(question.getD());
                break;
            case 0:
                break;
        }
    }

    //通过类型获取题目答案
    public void getYouAnswerFromType(final int index, int type,String title){
        final UserAnswer userAnswer = new UserAnswer();
        //判断题和单选题的监听
        if (type==1){
            if (a.isChecked()==true){ userAnswer.setUseranswer("A"); }
            if (b.isChecked()==true){ userAnswer.setUseranswer("B"); }
            if (c.isChecked()==true){ userAnswer.setUseranswer("C"); }
            if (d.isChecked()==true){ userAnswer.setUseranswer("D"); }
        }else if (type==2){
            if (t.isChecked()==true){ userAnswer.setUseranswer("T"); }
            if (f.isChecked()==true){ userAnswer.setUseranswer("F"); }
        }else if (type==4){
            if (aa.isChecked()&&bb.isChecked()&&cc.isChecked()&&dd.isChecked()){ userAnswer.setUseranswer("ABCD"); }
            if (!aa.isChecked()&&bb.isChecked()&&!cc.isChecked()&&!dd.isChecked()){ userAnswer.setUseranswer("B"); }
            if (!aa.isChecked()&&!bb.isChecked()&&cc.isChecked()&&!dd.isChecked()){ userAnswer.setUseranswer("C"); }
            if (!aa.isChecked()&&!bb.isChecked()&&!cc.isChecked()&&dd.isChecked()){ userAnswer.setUseranswer("D"); }
            if (aa.isChecked()&&!bb.isChecked()&&!cc.isChecked()&&!dd.isChecked()){ userAnswer.setUseranswer("A"); }
            if (aa.isChecked()&&bb.isChecked()&&cc.isChecked()&&!dd.isChecked()){ userAnswer.setUseranswer("ABC"); }
            if (aa.isChecked()&&bb.isChecked()&&!cc.isChecked()&&dd.isChecked()){ userAnswer.setUseranswer("ABD"); }
            if (aa.isChecked()&&!bb.isChecked()&&cc.isChecked()&&dd.isChecked()){ userAnswer.setUseranswer("ACD"); }
            if (!aa.isChecked()&&bb.isChecked()&&cc.isChecked()&&dd.isChecked()){ userAnswer.setUseranswer("BCD"); }
            if (aa.isChecked()&&bb.isChecked()&&!cc.isChecked()&&!dd.isChecked()){ userAnswer.setUseranswer("AB"); }
            if (aa.isChecked()&&!bb.isChecked()&&cc.isChecked()&&!dd.isChecked()){ userAnswer.setUseranswer("AC"); }
            if (aa.isChecked()&&!bb.isChecked()&&!cc.isChecked()&&dd.isChecked()){ userAnswer.setUseranswer("AD"); }
            if (!aa.isChecked()&&bb.isChecked()&&cc.isChecked()&&!dd.isChecked()){ userAnswer.setUseranswer("BC"); }
            if (!aa.isChecked()&&bb.isChecked()&&!cc.isChecked()&&dd.isChecked()){ userAnswer.setUseranswer("BD"); }
            if (!aa.isChecked()&&!bb.isChecked()&&cc.isChecked()&&dd.isChecked()){ userAnswer.setUseranswer("CD"); }
        }else if (type==3){
            if (!TextUtils.isEmpty(editText.getText().toString())){
                userAnswer.setUseranswer(editText.getText().toString());
                Map<String,Object> data = mSp.load();//获取登录的用户信息
                userAnswer.setUsername(data.get("name").toString());
                userAnswer.setTitle(title);
                userAnswer.setActualscore(0+"");
                commitAnswer(userAnswer);//主观题答案需要保存到数据库
            }
        }
        if (TextUtils.isEmpty(userAnswer.getUseranswer())){
            Toast.makeText(getActivity(), "没有检测到上一题的答案,请点击'上一题'作答", Toast.LENGTH_SHORT).show();
            return;
        }
        if (answers.size()<count ){
            answers.add(userAnswer);
        }else {
            answers.set(index,userAnswer);
        }
    }

    //格式化时间
    public static String formatDuring(long mss) {
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000 ;
        return  "距离考试结束还有：" + minutes + " 分钟 " + seconds + " 秒 ";
    }

    //设置定时任务
    public void setTimer(int minutes){
        countdowntimer.setVisibility(View.VISIBLE);
        new CountDownTimer(1000*60*minutes+500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdowntimer.setText(formatDuring(millisUntilFinished));
            }
            @Override
            public void onFinish() {
                countdowntimer.setText("考试结束");
                isEnd = true;
                if (!isSave) {
                    getScoreFromAnswer();
                    checkAnswerToScore();
                    ChartFragment.showBarChartMore();
                   // ChartFragment.showYunTu();
                    ViewPager vp = HomeActivity.getmViewPager();
                    vp.setCurrentItem(2);
                }
            }
        }.start();
    }

    //核对答案给出分数
    public void checkAnswerToScore(){
        float score=0;//主观题分数
        float score1=0;//多选题分数
        float score2=0;//单选题分数
        float score3=0;//判断题分数
        StringBuilder stringBuilder = new StringBuilder();
        if (answers.size()>0&&!answers.isEmpty()) {
            for (int i = 0; i < mQuestions.size(); i++) {
                if (mQuestions.get(i).getType().equals("主观题")) {
                    stringBuilder.append(answers.get(i).getUseranswer());
                    if (mQuestions.get(i).getAnswer().equals(answers.get(i).getUseranswer())) {
                        score = score + Integer.parseInt(mQuestions.get(i).getScore());
                    } else if (SimilarityUtils.Index_BF(answers.get(i).getUseranswer(), mQuestions.get(i).getAnswer()) >= 0) {
                        score = score + Integer.parseInt(mQuestions.get(i).getScore());
                    } else if (SimilarityUtils.levenshtein(mQuestions.get(i).getAnswer().toLowerCase(), answers.get(i).getUseranswer().toLowerCase()) > 0.5) {
                        score = score + Integer.parseInt(mQuestions.get(i).getScore()) / 2;
                    } else {
                        score = score + 0;
                    }
                } else {
                    if (mQuestions.get(i).getType().equals("多选题")) {
                        if (mQuestions.get(i).getAnswer().equals(answers.get(i).getUseranswer())) {
                            score1 = score1 + Integer.parseInt(mQuestions.get(i).getScore());
                        } else {
                            if (SimilarityUtils.Index_BF(mQuestions.get(i).getAnswer(), answers.get(i).getUseranswer()) != -1) {
                                score1 = score1 + (Integer.parseInt(mQuestions.get(i).getScore()) / 2);
                            }
                        }
                    } else if (mQuestions.get(i).getType().equals("单选题")) {
                        if (mQuestions.get(i).getAnswer().equals(answers.get(i).getUseranswer())) {
                            score2 = score2 + Integer.parseInt(mQuestions.get(i).getScore());
                        }
                    } else if (mQuestions.get(i).getType().equals("判断题")) {
                        if (mQuestions.get(i).getAnswer().equals(answers.get(i).getUseranswer())) {
                            score3 = score3 + Integer.parseInt(mQuestions.get(i).getScore());
                        }
                    }
                }
            }
            fileUtil.save(stringBuilder.toString());//保存主观题答案到文件中
            mSp.writesScore(score, score1, score2, score3);
            Map<String,Object> data = mSp.load();//获取登录的用户信息
            userGrade.setUsername(data.get("name").toString());
            userGrade.setScorezg((int) score);//自动结算主观题分数，先存到数据库，后续老师可以更新分数。
            userGrade.setScoreduox((int) score1);
            userGrade.setScoredanx((int) score2);
            userGrade.setScorepd((int) score3);
            if (!isCommit){
                commitGrade(userGrade);
                //Toast.makeText(mContext, userGrade.toString(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mContext, "已经查看过答案和解析就不能再提交了哦", Toast.LENGTH_SHORT).show();
            }
        }else{
            Map<String,Object> data = mSp.load();
            userGrade.setUsername(data.get("name").toString());
            userGrade.setScorezg(0);
            userGrade.setScoreduox(0);
            userGrade.setScoredanx(0);
            userGrade.setScorepd(0);
            if (!isCommit){
                commitGrade(userGrade);
            }else {
                Toast.makeText(mContext, "已经查看过答案和解析就不能再提交了哦", Toast.LENGTH_SHORT).show();
            }
        }
        initAllView();
    }

    //提交成绩信息
    public void commitGrade(UserGrade userGrade){
        final String address = "http://47.102.199.28/flyapp/addUserGradeFromClient";
        final String json = JsonUtil.converJavaBeanToJson(userGrade);
        HttpUtil.sendQuestion(address, json, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Looper.prepare();
                Toast.makeText(getActivity(), "成绩提交成功", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onError(Exception e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
    //提交主观题答案信息
    public void commitAnswer(UserAnswer userAnswer){
        final String address = "http://47.102.199.28/flyapp/addUserAnswerFromClient";
        final String json = JsonUtil.converJavaBeanToJson(userAnswer);
        HttpUtil.sendQuestion(address, json, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Looper.prepare();
                //Toast.makeText(getActivity(), "答案信息提交成功", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onError(Exception e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

    //清除主观题答案信息
    public void clearAnswer(UserAnswer userAnswer){
        final String address = "http://47.102.199.28/flyapp/deleteAnswer";
        final String json = JsonUtil.converJavaBeanToJson(userAnswer);
        HttpUtil.sendQuestion(address, json, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Looper.prepare();
                //Toast.makeText(getActivity(), "答案信息提交成功", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onError(Exception e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
    // 获取题目的满分分数
    public void getScoreFromAnswer() {
        float score = 0; // 主观题分数
        float score1 = 0;// 多选题分数
        float score2 = 0;// 单选题分数
        float score3 = 0;// 判断题分数
        if (mQuestions != null) {
            for (int i = 0; i < mQuestions.size(); i++) {
                if (mQuestions.get(i).getType().equals("主观题")) {
                    score = score + Integer.parseInt(mQuestions.get(i).getScore());
                } else if (mQuestions.get(i).getType().equals("多选题")) {
                    score1 = score1 + Integer.parseInt(mQuestions.get(i).getScore());
                } else if (mQuestions.get(i).getType().equals("单选题")) {
                    score2 = score2 + Integer.parseInt(mQuestions.get(i).getScore());
                } else if (mQuestions.get(i).getType().equals("判断题")) {
                    score3 = score3 + Integer.parseInt(mQuestions.get(i).getScore());
                }
            }
        }
        mSp.setScore(score, score1, score2, score3);
    }

    //获取题目总用时
    public int getTime(){
        int time = 0;
        if (mQuestions != null) {
            for (int i = 0; i < mQuestions.size(); i++) {
                String string = mQuestions.get(i).getTime();
                time = time + Integer.valueOf(string);
            }
        }
        return time;
    }

    //清除选项
    public void clearSelect(){
        editText.setText("");
        radioGroup.clearCheck();
        radioGroupTF.clearCheck();
        aa.setChecked(false);
        bb.setChecked(false);
        cc.setChecked(false);
        dd.setChecked(false);
    }

    //题目界面
    public void initAllView(){
        isCommit = true;
        if (mQuestions != null && mQuestions.size()>0) {
            count = mQuestions.size();//题目数量
           // answers = new ArrayList<>(count);
            save.setVisibility(View.GONE);
            starttest.setVisibility(View.GONE);
            //初始化题库中的第一道题
            index = 0;
            Question question = mQuestions.get(index);
            type = getQuestionType(question);
            setAllViewFromType(type, question);
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
        }else{
            Toast.makeText(getActivity(), "题库为空,请再次刷新", Toast.LENGTH_SHORT).show();
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
