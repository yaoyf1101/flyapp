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
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;

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

//    public static void showYunTu(){
//        //创建一个词语解析器,类似于分词
//        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        frequencyAnalyzer.setWordFrequenciesToReturn(600);
//        frequencyAnalyzer.setMinWordLength(2);
//        //引用中文的解析器
//        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
//
//        //拿到文档里面分出的词,和词频,建立一个集合存储起来
//        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(getInputStream("text/vina.txt"));
//
//        //设置图片相关的属性,这边是大小和形状,更多的形状属性,可以从CollisionMode源码里面查找
//        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
//        wordCloud.setPadding(2);
//
//        //这边要注意意思,是设置中文字体的,如果不设置,得到的将会是乱码,
//        //这是官方给出的代码没有写的,我这边拓展写一下,字体,大小可以设置
//        //具体可以参照Font源码
//        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 16);
//        wordCloud.setKumoFont(new KumoFont(font));
//        wordCloud.setBackgroundColor(new Color(255, 255, 255));
//        //因为我这边是生成一个圆形,这边设置圆的半径
//        wordCloud.setBackground(new CircleBackground(255));
//        //设置颜色
//        wordCloud.setColorPalette(buildRandomColorPalette(1));
//        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
//        //将文字写入图片
//        wordCloud.build(wordFrequencies);
//        //生成图片
//         wordCloud.writeToFile("output/chinese_language_circle.png");
//
//    }
}
