package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.Question;
import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.SQLiteUtil.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment {

    private SQLiteDatabase db;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonA,mRadioButtonB,mRadioButtonC,mRadioButtonD;
    private TextView tv_title, tv_result;
    private Button btn_down, btn_up,btn_commit;
    private int count;
    private int index;
    private List<Question> mList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    DBHelper mDbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new DBHelper(getActivity(), "Test.db", null, 2); //创建数据库
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_result = (TextView) view.findViewById(R.id.tv_result);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.mRadioGroup);
        mRadioButtonA = (RadioButton) view.findViewById(R.id.RadioA);
        mRadioButtonB = (RadioButton) view.findViewById(R.id.RadioB);
        mRadioButtonC = (RadioButton) view.findViewById(R.id.RadioC);
        mRadioButtonD = (RadioButton) view.findViewById(R.id.RadioD);
        btn_down = (Button) view.findViewById(R.id.btn_down);
        btn_up = (Button) view.findViewById(R.id.btn_up);
        btn_commit = (Button) view.findViewById(R.id.commit);
        initData(false);
        view.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(mList);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(true);
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
    }

    public void initData(Boolean isToast) {
        db = mDbHelper.getReadableDatabase();
        mList = getQuestion();

        final int count = mList.size();
        if(count==0){
            if (isToast){
                Toast.makeText(getActivity(), "当前题库没有题目！", Toast.LENGTH_SHORT).show();
            }
            tv_title.setText("题库");
            mRadioButtonA.setText("空");
            mRadioButtonB.setText("空");
            mRadioButtonC.setText("如");
            mRadioButtonD.setText("也");
            tv_result.setText("等待老师发布考题吧！");
            tv_result.setVisibility(View.VISIBLE);
            btn_down.setVisibility(View.INVISIBLE);
            btn_up.setVisibility(View.INVISIBLE);
            btn_commit.setVisibility(View.INVISIBLE);
            return;
        }
        final int[] corrent = {0};

        Question q = mList.get(0);
        tv_title.setText(q.question);
        mRadioButtonA.setText(q.answerA);
        mRadioButtonB.setText(q.answerB);
        mRadioButtonC.setText(q.answerC);
        mRadioButtonD.setText(q.answerD);
        btn_down.setVisibility(View.VISIBLE);
        btn_up.setVisibility(View.VISIBLE);
        btn_commit.setVisibility(View.VISIBLE);
        tv_result.setVisibility(View.INVISIBLE);

        //上一题
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (corrent[0] > 0) {
                    corrent[0]--;
                    Question q = mList.get(corrent[0]);
                    tv_title.setText(q.question);
                    mRadioButtonA.setText(q.answerA);
                    mRadioButtonB.setText(q.answerB);
                    mRadioButtonC.setText(q.answerC);
                    mRadioButtonD.setText(q.answerD);

                    tv_result.setText(q.explaination);

                    mRadioGroup.clearCheck();

                    //设置选中
                    if (!q.selectedAnswer.equals("")) {
                        if(q.selectedAnswer.equals("A")){
                            mRadioButtonA.setChecked(true);
                        }
                        if(q.selectedAnswer.equals("B")){
                            mRadioButtonB.setChecked(true);
                        }
                        if(q.selectedAnswer.equals("C")){
                            mRadioButtonC.setChecked(true);
                        }
                        if(q.selectedAnswer.equals("D")){
                            mRadioButtonD.setChecked(true);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "这是第一题！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //下一题
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为最后一题
                if (corrent[0] < count - 1) {
                    corrent[0]++;
                    Question q = mList.get(corrent[0]);

                    tv_title.setText(q.question);

                    mRadioButtonA.setText(q.answerA);
                    mRadioButtonB.setText(q.answerB);
                    mRadioButtonC.setText(q.answerC);
                    mRadioButtonD.setText(q.answerD);

                    tv_result.setText(q.explaination);

                    mRadioGroup.clearCheck();

                    //设置选中
                    if (!q.selectedAnswer.equals("")) {
                        if(q.selectedAnswer.equals("A")){
                            mRadioButtonA.setChecked(true);
                        }
                        if(q.selectedAnswer.equals("B")){
                            mRadioButtonB.setChecked(true);
                        }
                        if(q.selectedAnswer.equals("C")){
                            mRadioButtonC.setChecked(true);
                        }
                        if(q.selectedAnswer.equals("D")){
                            mRadioButtonD.setChecked(true);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "最后一题啦！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //答案选中
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(mRadioButtonA.isChecked()==true){
                    mList.get(corrent[0]).selectedAnswer = "A";
                }
                if(mRadioButtonB.isChecked()==true){
                    mList.get(corrent[0]).selectedAnswer = "B";
                }
                if(mRadioButtonC.isChecked()==true){
                    mList.get(corrent[0]).selectedAnswer = "C";
                }
                if(mRadioButtonD.isChecked()==true){
                    mList.get(corrent[0]).selectedAnswer = "D";
                }
            }
        });
    }

    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<>();
        //执行sql语句
        Cursor cursor = db.rawQuery("select * from question", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                //ID
                question.ID = cursor.getInt(cursor.getColumnIndex("id"));
                //问题
                question.question = cursor.getString(cursor.getColumnIndex("name"));
                //四个选择
                question.answerA = cursor.getString(cursor.getColumnIndex("a"));
                question.answerB = cursor.getString(cursor.getColumnIndex("b"));
                question.answerC = cursor.getString(cursor.getColumnIndex("c"));
                question.answerD = cursor.getString(cursor.getColumnIndex("d"));
                //答案
                question.answer = cursor.getString(cursor.getColumnIndex("answer"));
                //解析
                question.explaination = cursor.getString(cursor.getColumnIndex("note"));
                //设置为没有选择任何选项
                question.selectedAnswer = "";
                list.add(question);
            }
        }
        return list;
    }

    private void check(final List<Question> list) {
        final List<Integer> wrongList = checkAnswer(list);

        if (wrongList.size() == 0) {
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("你好厉害，答对了所有题！是否查看解释？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            index = 0;
                            count = list.size();
                            Question q = list.get(0);
                            tv_title.setText(q.question);
                            mRadioButtonA.setText(q.answerA);
                            mRadioButtonB.setText(q.answerB);
                            mRadioButtonC.setText(q.answerC);
                            mRadioButtonD.setText(q.answerD);
                            tv_result.setText(q.explaination);
                            //显示结果
                            tv_result.setVisibility(View.VISIBLE);
                            btn_down.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (index < count-1) {
                                        index++;
                                        Question q = list.get(index);
                                        tv_title.setText(q.question);
                                        mRadioButtonA.setText(q.answerA);
                                        mRadioButtonB.setText(q.answerB);
                                        mRadioButtonC.setText(q.answerC);
                                        mRadioButtonD.setText(q.answerD);
                                        tv_result.setText(q.explaination);
                                        tv_result.setText(q.explaination);
                                        //显示结果
                                        tv_result.setVisibility(View.VISIBLE);
                                    }else {
                                        Toast.makeText(getActivity(), "最后一题啦！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            btn_up.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (index > 0) {
                                        index--;
                                        Question q = list.get(index);
                                        tv_title.setText(q.question);
                                        mRadioButtonA.setText(q.answerA);
                                        mRadioButtonB.setText(q.answerB);
                                        mRadioButtonC.setText(q.answerC);
                                        mRadioButtonD.setText(q.answerD);
                                        tv_result.setText(q.explaination);
                                        tv_result.setText(q.explaination);
                                        //显示结果
                                        tv_result.setVisibility(View.VISIBLE);
                                    }else {
                                        Toast.makeText(getActivity(), "这是第一题！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).setNegativeButton("取消",null).show();
        } else {
            //窗口提示
            new AlertDialog.Builder(getActivity()).setTitle("恭喜，答题完成！")
                    .setMessage("答对了" + (list.size() - wrongList.size()) + "道题" + "\n"
                            + "答错了" + wrongList.size() + "道题" + "\n" + "是否查看错题？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    List<Question> newList = new ArrayList<Question>();
                    for (int i = 0; i < wrongList.size(); i++) {
                        newList.add(list.get(wrongList.get(i)));
                    }
                    list.clear();
                    for (int i = 0; i < newList.size(); i++) {
                        list.add(newList.get(i));
                    }
                    index = 0;
                    count = newList.size();
                    //更新当前显示的内容
                    Question q = list.get(0);
                    tv_title.setText(q.question);
                    mRadioButtonA.setText(q.answerA);
                    mRadioButtonB.setText(q.answerB);
                    mRadioButtonC.setText(q.answerC);
                    mRadioButtonD.setText(q.answerD);
                    tv_result.setText(q.explaination);
                    //显示结果
                    tv_result.setVisibility(View.VISIBLE);
                    btn_down.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (index < count-1) {
                                index++;
                                Question q = list.get(index);
                                tv_title.setText(q.question);
                                mRadioButtonA.setText(q.answerA);
                                mRadioButtonB.setText(q.answerB);
                                mRadioButtonC.setText(q.answerC);
                                mRadioButtonD.setText(q.answerD);
                                tv_result.setText(q.explaination);
                                //显示结果
                                tv_result.setVisibility(View.VISIBLE);
                            }else {
                                Toast.makeText(getActivity(), "最后一题啦！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    btn_up.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (index > 0) {
                                index--;
                                Question q = list.get(index);
                                tv_title.setText(q.question);
                                mRadioButtonA.setText(q.answerA);
                                mRadioButtonB.setText(q.answerB);
                                mRadioButtonC.setText(q.answerC);
                                mRadioButtonD.setText(q.answerD);
                                tv_result.setText(q.explaination);
                                //显示结果
                                tv_result.setVisibility(View.VISIBLE);
                            }else {
                                Toast.makeText(getActivity(), "这是第一题！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).setNegativeButton("取消", null).show();
        }

    }

    private List<Integer> checkAnswer (List < Question > list) {
        List<Integer> wrongList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //判断对错
            if (!list.get(i).answer.equals(list.get(i).selectedAnswer)) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }
}
