package com.example.yaoyifei.yaoyfapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yaoyifei.yaoyfapplication.R;

public class ViewHolderTest extends RecyclerView.ViewHolder {

    public final TextView mTv_title;

    /**
     * itemView为MyViewHolder中onCreateViewHolder加载的布局
     * @param itemView 条目
     */
    public ViewHolderTest(View itemView) {
        super(itemView);
        mTv_title = itemView.findViewById(R.id.tv_title);
    }
}
