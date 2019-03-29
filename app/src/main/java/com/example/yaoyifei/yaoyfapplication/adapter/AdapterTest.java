package com.example.yaoyifei.yaoyfapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yaoyifei.yaoyfapplication.R;

import java.util.ArrayList;

public class AdapterTest extends RecyclerView.Adapter<ViewHolderTest> {
    private Context mContext;
    private ArrayList<String> data;

    public AdapterTest(Context ctx, ArrayList<String> data) {
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
    public ViewHolderTest onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局文件
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_usergrade, null,false);
        return new ViewHolderTest(itemView);
    }

    /**
     * 相当于getView 绑定数据部分的代码
     * @param viewHolderTest  ViewHolder
     * @param i 位置
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderTest viewHolderTest, int i) {
        String mData = data.get(i);
        viewHolderTest.mTv_title.setText(mData);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}