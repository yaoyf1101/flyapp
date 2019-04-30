package com.example.yaoyifei.yaoyfapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaoyifei.yaoyfapplication.R;

public class ViewHolderYuntu extends RecyclerView.ViewHolder {

    public final TextView mTitle;
    public final ImageView mImageView;

    /**
     * itemView为MyViewHolder中onCreateViewHolder加载的布局
     * @param itemView 条目
     */
    public ViewHolderYuntu(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mTitle = itemView.findViewById(R.id.title);
        mImageView = itemView.findViewById(R.id.image);
    }
}
