package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.Entity.UserGrade;
import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.adapter.AdapterUserGrade;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


public class UserGradeFragmentTeacher extends Fragment  {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLlm;
    private TextView tips;
    final String address = "http://47.102.199.28/flyapp/getUserGradeServlet";
    private List<UserGrade> mUserGrades = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AdapterUserGrade adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grade, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tips = view.findViewById(R.id.tips);
        tips.setText("下拉刷新查看所有考生成绩");
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserGrade();
            }
        });
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(new AdapterUserGrade(getActivity(),mUserGrades));
        mLlm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLlm);
    }


    //通过网络获取用户成绩信息
    private void getUserGrade() {
        HttpUtil.getQuestion(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Gson gson = new Gson();
                mUserGrades = gson.fromJson(response,new TypeToken<List<UserGrade>>(){}.getType());
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        adapter = new AdapterUserGrade(getActivity(),mUserGrades);
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
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

}
