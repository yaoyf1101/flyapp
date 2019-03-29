package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.tools.BarChartManager;
import com.example.yaoyifei.yaoyfapplication.tools.FileUtil;
import com.example.yaoyifei.yaoyfapplication.tools.SP;
import com.github.mikephil.charting.charts.BarChart;
import com.mordred.wordcloud.WordCloud;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartFragment extends Fragment  {

    public static BarChart barChat;
    public static ImageView imageView;
    private Context mContext;
    private static SP mSp;
    private static FileUtil mFileUtil;
    private static TextView textView,textView1;
    static Float keguan,keguan1,zhuguan,zhuguan1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mSp = new SP(mContext);
        mFileUtil = new FileUtil(mContext);
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
        imageView = view.findViewById(R.id.image);
        textView= view.findViewById(R.id.text_chart);
        textView1= view.findViewById(R.id.text1_chart);
    }

    //显示柱状图
    public static void showBarChartMore() {
        Map<String, Object> data = mSp.loadScore();
        Map<String, Object> data1 = mSp.getScore();
        mSp.clear2();//每次获取sp文件中的数据后清除
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
        keguan = (Float) data.get("score1")+(Float) data.get("score2")+(Float) data.get("score3");
        zhuguan = (Float) data.get("score");
        //第二条柱子的数据，从考生答题的结果中获取
        if (data1!=null){
            x2.add((Float) data1.get("score1"));
            x2.add((Float) data1.get("score2"));
            x2.add((Float) data1.get("score3"));
        }else{
            x2.add(20f);
            x2.add(10f);
            x2.add(20f);
        }
        keguan1 = (Float) data1.get("score1")+(Float) data1.get("score2")+(Float) data1.get("score3");
        zhuguan1 = (Float) data1.get("score");
        textView.setText("总分为:"+keguan1+"    "+"你的得分为:"+keguan);
        textView1.setText("总分为:"+zhuguan1+"    "+"你的得分为:"+zhuguan);
        yAxisValues.add(x1);
        yAxisValues.add(x2);
        labels.add("");
        labels.add("");
        colours.add(Color.parseColor("#F08080"));
        colours.add(Color.parseColor("#00EEEE"));
        barChartManager.showMoreBarChart(xAxisValues, yAxisValues, labels, colours);
        barChartManager.setXAxis(3, 0, 3);
    }

    //显示云图
    public static void showYunTu() {
        String str = mFileUtil.load();
        mFileUtil.deleteFile();//每次从文件中服务数据后清除数据
        Map<String, Integer> wordMap = new HashMap<>();
        org.ansj.domain.Result result =ToAnalysis.parse(str);
        List<Term> terms = result.getTerms();
        int count = 1;
        for (int i=0;i<=terms.size()-count;i++){
            if(terms.size()==1){
                wordMap.put(terms.get(0).getName(),100);
            }else{
                for (int j=i;j<terms.size()-1;j++){
                    if (terms.get(j).getName().equals(terms.get(j+1).getName())){
                        count++;
                    }
                }
                String word = terms.get(i).getName();
                wordMap.put(word,count*100);
            }
        }
        WordCloud wd = new WordCloud(wordMap, 400, 250,Color.parseColor("#F08080"),Color.parseColor("#1AC0C0C0"));
        wd.setWordColorOpacityAuto(true);
        Bitmap generatedWordCloudBmp = wd.generate();
        imageView.setImageBitmap(generatedWordCloudBmp);
    }

}
