package com.example.yaoyifei.yaoyfapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yaoyifei.yaoyfapplication.Entity.UserAnswer;
import com.example.yaoyifei.yaoyfapplication.R;
import com.mordred.wordcloud.WordCloud;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterYuntu extends RecyclerView.Adapter<ViewHolderYuntu> {
    private Context mContext;
    private List<UserAnswer> data;
//    List<String> users = null;
//    List<String> titles = null;

    public AdapterYuntu(Context ctx, List<UserAnswer> data) {
        mContext = ctx;
        this.data = data;
//        dateToArray(data);
    }

    /**
     * 相当于getView方法中创建View和ViewHolder
     *
     * @param parent   父容器
     * @param viewType 类型
     * @return ViewHolder
     */
    @Override
    public ViewHolderYuntu onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局文件
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_yuntu, null,false);
        return new ViewHolderYuntu(itemView);
    }

    /**
     * 相当于getView 绑定数据部分的代码
     * @param viewHolderYuntu  ViewHolder
     * @param i 位置
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderYuntu viewHolderYuntu, int i) {
        if (data != null) {
            UserAnswer userAnswer = data.get(i);
            viewHolderYuntu.mTitle.setText("题目："+userAnswer.getTitle());
            String string = getAnswer(userAnswer.getTitle());
            viewHolderYuntu.mImageView.setImageBitmap(showYunTu(string));
        }
    }

    @Override
    public int getItemCount() {
        if (data != null){
            return  data.size();
        }else {
            return 0;
        }
    }
    //生成云图
    public  Bitmap showYunTu(String string) {
        Map<String, Integer> wordMap = new HashMap<>();
        org.ansj.domain.Result result =ToAnalysis.parse(string);
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
        return generatedWordCloudBmp;
    }

/*    public void dateToArray(List<UserAnswer> data){
        String defultuser = data.get(0).getUsername();//第一个用户名字
        users.add(defultuser);
        String defulttitle = data.get(0).getTitle();//第一题的标题
        titles.add(defulttitle);
        //得出用户名字数组
        for (int i = 0; i<getItemCount();i++){
            if (!defultuser.equals(data.get(i).getUsername())){
                users.add(data.get(i).getUsername());
            }
        }
        //得出题目标题数组
        for (int i = 0; i<getItemCount();i++){
            if (!defulttitle.equals(data.get(i).getTitle())){
                titles.add(data.get(i).getTitle());
            }
        }
    }*/

    public String getAnswer(String title){
        StringBuilder stringBuilder = new StringBuilder();
         for (int i = 0; i<getItemCount();i++){
            if (title.equals(data.get(i).getTitle())){
                stringBuilder.append(data.get(i).getUseranswer());
            }
        }
        return stringBuilder.toString();
    }
}