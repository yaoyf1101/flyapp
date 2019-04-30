package com.example.yaoyifei.yaoyfapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaoyifei.yaoyfapplication.R;

public class ViewHolderAnswer extends RecyclerView.ViewHolder {

    public final TextView mTitle;
    public final TextView mUserName;
    public final TextView mAnswer;
    public final EditText mScore;
    public final EditText mAssess;
    public final Button mCommit;


    /**
     * itemView为MyViewHolder中onCreateViewHolder加载的布局
     * @param itemView 条目
     */
    public ViewHolderAnswer(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mTitle = itemView.findViewById(R.id.title);
        mUserName = itemView.findViewById(R.id.user_name);
        mAnswer = itemView.findViewById(R.id.user_answer);
        mScore = itemView.findViewById(R.id.score);
        mAssess = itemView.findViewById(R.id.assess);
        mCommit = itemView.findViewById(R.id.commit);
    }
}
