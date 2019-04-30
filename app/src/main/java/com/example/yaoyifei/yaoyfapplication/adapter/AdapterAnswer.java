package com.example.yaoyifei.yaoyfapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.UserAnswer;
import com.example.yaoyifei.yaoyfapplication.R;

import java.util.List;

public class AdapterAnswer extends RecyclerView.Adapter<ViewHolderAnswer> {
    private Context mContext;
    private List<UserAnswer> data;
    private OnRecycleItemListener listener;
    public static String score;
    public static String assess;
    public static UserAnswer userAnswer;


    public AdapterAnswer(Context ctx, List<UserAnswer> data) {
        mContext = ctx;
        this.data = data;
    }

    /**
     * 相当于getView方法中创建View和ViewHolder
     *
     * @param parent   父容器
     * @param viewType 类型
     * @return ViewHolder
     */
    @Override
    public ViewHolderAnswer onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局文件
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_useranwser, null,false);
        return new ViewHolderAnswer(itemView);
    }

    /**
     * 相当于getView 绑定数据部分的代码
     * @param viewHolderAnswer  ViewHolder
     * @param i 位置
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderAnswer viewHolderAnswer, int i) {
        if (data != null) {
             userAnswer = data.get(i);
            viewHolderAnswer.mTitle.setText(userAnswer.getTitle());
            viewHolderAnswer.mUserName.setText(userAnswer.getUsername());
            viewHolderAnswer.mAnswer.setText(userAnswer.getUseranswer());
            score = viewHolderAnswer.mScore.getText().toString();
            assess = viewHolderAnswer.mAssess.getText().toString();
            viewHolderAnswer.mCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(score)){
                        userAnswer.setActualscore(score);
                    }else {
                        Toast.makeText(mContext,"请输入分数",Toast.LENGTH_SHORT).show();
                    }
                    if (!TextUtils.isEmpty(assess)){
                        userAnswer.setTeacherassess(assess);
                    }else {
                        Toast.makeText(mContext,"未输入评语",Toast.LENGTH_SHORT).show();
                    }
                   // new QuestionFragment().commitAnswer(userAnswer);
                    Toast.makeText(mContext,"题目已阅",Toast.LENGTH_SHORT).show();
                    //  listener.OnRecycleItemClick(v,viewHolderAnswer.mCommit);
                }
            });
        }
    }
    public void addRecycleItemListener(OnRecycleItemListener listener){
        this.listener = listener;
    }
    public interface OnRecycleItemListener <Button>{
        void OnRecycleItemClick(View v, Button button);
    }
    @Override
    public int getItemCount() {
        if (data != null){
            return  data.size();
        }else {
            return 0;
        }
    }
}