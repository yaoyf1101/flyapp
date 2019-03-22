package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.tools.BarChartManager;
import com.github.mikephil.charting.charts.BarChart;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment  {

    public BarChart barChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChat = (BarChart) view.findViewById(R.id.Bar_chat);
        showBarChartMore();
    }

    //显示柱状图
    public void showBarChartMore() {
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
        x1.add(18f);
        x1.add(8f);
        x1.add(14f);

        //第二条柱子的数据，从考生答题的解果中获取
        x2.add(20f);
        x2.add(10f);
        x2.add(20f);

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
