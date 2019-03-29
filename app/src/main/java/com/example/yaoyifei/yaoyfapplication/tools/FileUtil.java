package com.example.yaoyifei.yaoyfapplication.tools;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtil {
    private Context mContext;

    public FileUtil(Context mContext) {
        this.mContext = mContext;
    }

    public  void save(String string){
        FileOutputStream fileOutputStream;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = mContext.openFileOutput("data",Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(string);
        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            if (bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  String load(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            in = mContext.openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    public void deleteFile() {
        File file =  new File("/data/data/com.example.yaoyifei.yaoyfapplication/files/data");
        file.delete();
    }
}
