package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.tools.BarChartManager;
import com.example.yaoyifei.yaoyfapplication.tools.SP;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartFragment extends Fragment  {

    public static BarChart barChat;
    private Context mContext;
    private static SP mSp;
    private boolean isGetData = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mSp = new SP(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChat = (BarChart) view.findViewById(R.id.Bar_chat);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            showBarChartMore();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    //显示柱状图
    public static void showBarChartMore() {
        Map<String, Object> data = mSp.loadScore();
        Map<String, Object> data1 = mSp.getScore();
        BarChartManager barChartManager = new BarChartManager(barChat);

        List<Float> xAxisValues = new ArrayList<>();
        List<List<Float>> yAxisValues = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();
        List<Float> x1 = new ArrayList<>();
        List<Float> x2 = new ArrayList<>();
        xAxisValues.add(1.0f);
        xAxisValues.add(2.0f);
        xAxisValues.add(3.0f);

        //第一条柱子的数据,从题目设置的分值中获取
        if (data!=null){
            x1.add((Float) data.get("score1"));
            x1.add((Float) data.get("score2"));
            x1.add((Float) data.get("score3"));
        }else{
            x1.add(20f);
            x1.add(10f);
            x1.add(20f);
        }

        //第二条柱子的数据，从考生答题的解果中获取
        if (data1!=null){
            x2.add((Float) data1.get("score1"));
            x2.add((Float) data1.get("score2"));
            x2.add((Float) data1.get("score3"));
        }else{
            x2.add(20f);
            x2.add(10f);
            x2.add(20f);
        }

        yAxisValues.add(x1);
        yAxisValues.add(x2);
        labels.add("");
        labels.add("");
        colours.add(Color.parseColor("#F08080"));
        colours.add(Color.parseColor("#00EEEE"));
        barChartManager.showMoreBarChart(xAxisValues, yAxisValues, labels, colours);
        barChartManager.setXAxis(3, 0, 3);
    }

}
