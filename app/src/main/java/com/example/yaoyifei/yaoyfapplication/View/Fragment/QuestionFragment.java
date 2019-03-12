package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.QuestionServer;
import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class QuestionFragment extends Fragment  {

    private TextView title;
    private LinearLayout torF;
    private LinearLayout radio;
    private RadioGroup radioGroup;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;
    private RadioButton t;
    private RadioButton f;
    private CheckBox aa;
    private CheckBox bb;
    private CheckBox cc;
    private CheckBox dd;
    private LinearLayout checkbox;
    private EditText editText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<QuestionServer> mQuestionServers = null;
    final String address = "http://47.102.199.28/flyapp/getQuestionServlet";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getQuestion() {
        HttpUtil.getQuestion(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                mQuestionServers = gson.fromJson(response,new TypeToken<List<QuestionServer>>(){}.getType());
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
                getQuestion();
                initView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //题目区域
        title = (TextView) view.findViewById(R.id.title);
        //判断题区域
        torF = (LinearLayout) view.findViewById(R.id.TorF);
        t = (RadioButton) view.findViewById(R.id.T);
        f = (RadioButton) view.findViewById(R.id.F);
        //单选题区域
        radio = (LinearLayout) view.findViewById(R.id.radio);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        a = (RadioButton) view.findViewById(R.id.A);
        b = (RadioButton) view.findViewById(R.id.B);
        c = (RadioButton) view.findViewById(R.id.C);
        d = (RadioButton) view.findViewById(R.id.D);
        //多选题区域
        checkbox = (LinearLayout) view.findViewById(R.id.checkbox);
        aa = view.findViewById(R.id.AA);
        bb = view.findViewById(R.id.BB);
        cc = view.findViewById(R.id.CC);
        dd = view.findViewById(R.id.DD);
        //简答和填空作答区域
        editText = view.findViewById(R.id.edit_area);
    }

    public void initView(){
        if (mQuestionServers!=null) {
            int count = mQuestionServers.size();//题目数量
            //当题库中没有题的时候
            if (count == 0) {
                Toast.makeText(getActivity(), "当前题库没有题目,请刷新题库！", Toast.LENGTH_SHORT).show();
                return;
            }
            //初始化题库中的第一道题
            QuestionServer questionServer = mQuestionServers.get(0);
            int type = getQuestionType(questionServer);
            switch (type) {
                case 1:
                    title.setText(questionServer.getTitle());
                    torF.setVisibility(View.GONE);
                    checkbox.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                    a.setText(questionServer.getA());
                    b.setText(questionServer.getB());
                    c.setText(questionServer.getC());
                    d.setText(questionServer.getD());
                    break;
                case 2:
                    title.setText(questionServer.getTitle());
                    radio.setVisibility(View.GONE);
                    checkbox.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                    break;
                case 3:
                    title.setText(questionServer.getTitle());
                    torF.setVisibility(View.GONE);
                    radio.setVisibility(View.GONE);
                    checkbox.setVisibility(View.GONE);
                    break;
                case 0:
                    Toast.makeText(getActivity(), "未知的题目类型", Toast.LENGTH_SHORT).show();
                    break;
            }
            //切换题目的逻辑实现
        }

    }

    public int getQuestionType(QuestionServer questionServer){
        if (questionServer.getType().equals("选择")||questionServer.getType().equals("选择题")){
            return 1;
        }else if (questionServer.getType().equals("判断")||questionServer.getType().equals("判断题题")){
            return 2;
        }else if (questionServer.getType().equals("简答")||questionServer.getType().equals("简答题")||questionServer.getType().equals("填空")||questionServer.getType().equals("填空题")){
            return 3;
        }else {
            return 0;
        }
    }

}
