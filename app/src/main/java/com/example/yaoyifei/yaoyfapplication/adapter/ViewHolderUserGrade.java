package com.example.yaoyifei.yaoyfapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yaoyifei.yaoyfapplication.R;

public class ViewHolderUserGrade extends RecyclerView.ViewHolder {

    public final TextView mTv_user_name,mTv_zhuguanti,mTv_danxuanti,mTv_duoxuanti,mTv_panduanti;

    /**
     * itemView为MyViewHolder中onCreateViewHolder加载的布局
     * @param itemView 条目
     */
    public ViewHolderUserGrade(View itemView) {
        super(itemView);
        mTv_user_name = itemView.findViewById(R.id.user_name);
        mTv_danxuanti = itemView.findViewById(R.id.danxuanti);
        mTv_duoxuanti = itemView.findViewById(R.id.duoxuanti);
        mTv_zhuguanti = itemView.findViewById(R.id.zhuguanti);
        mTv_panduanti = itemView.findViewById(R.id.panduanti);
    }
}
