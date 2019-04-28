package com.example.yaoyifei.yaoyfapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yaoyifei.yaoyfapplication.Entity.UserGrade;
import com.example.yaoyifei.yaoyfapplication.R;

import java.util.List;

public class AdapterUserGrade extends RecyclerView.Adapter<ViewHolderUserGrade> {
    private Context mContext;
    private List<UserGrade> data;

    public AdapterUserGrade(Context ctx, List<UserGrade> data) {
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
    public ViewHolderUserGrade onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局文件
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_usergrade, null,false);
        return new ViewHolderUserGrade(itemView);
    }

    /**
     * 相当于getView 绑定数据部分的代码
     * @param viewHolderUserGrade  ViewHolder
     * @param i 位置
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderUserGrade viewHolderUserGrade, int i) {
        if (data != null) {
            UserGrade userGrade = data.get(i);
            viewHolderUserGrade.mTv_user_name.setText("亲爱的"+userGrade.getUsername()+"同学,你的成绩如下：");
            viewHolderUserGrade.mTv_panduanti.setText(userGrade.getScorepd()+"分");
            viewHolderUserGrade.mTv_zhuguanti.setText(userGrade.getScorezg()+"分");
            viewHolderUserGrade.mTv_duoxuanti.setText(userGrade.getScoreduox()+"分");
            viewHolderUserGrade.mTv_danxuanti.setText(userGrade.getScoredanx()+"分");
        }
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