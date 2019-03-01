package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.SQLiteUtil.DBHelper;


public class TeacherFragment extends Fragment implements View.OnClickListener {

    EditText mEtname;
    EditText A,B,C,D;
    EditText anwser;
    EditText ETnote;
    Button commit,delete,deleteall,fix;
    DBHelper mDbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new DBHelper(getActivity(), "Test.db", null, 2); //创建数据库
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEtname = getETname();
        A = getA();
        B = getB();
        C = getC();
        D = getD();
        anwser =getAnwser();
        ETnote = getETnote();
        delete = getDelete();
        deleteall=getDeleteall();
        commit=getCommit();
        fix = view.findViewById(R.id.fix);
        commit.setOnClickListener(this);
        delete.setOnClickListener(this);
        deleteall.setOnClickListener(this);
        fix.setOnClickListener(this);
    }

    private EditText getETname(){
        return (EditText) getView().findViewById(R.id.ETname);
    }

    private EditText getA(){
        return (EditText) getView().findViewById(R.id.A);
    }

    private EditText getB(){
        return (EditText) getView().findViewById(R.id.B);
    }

    private EditText getC(){
        return (EditText) getView().findViewById(R.id.C);
    }

    private EditText getD(){
        return (EditText) getView().findViewById(R.id.D);
    }

    private EditText getAnwser(){
        return (EditText) getView().findViewById(R.id.anwser);
    }

    private EditText getETnote(){
        return (EditText) getView().findViewById(R.id.ETnote);
    }

    public Button getCommit() { return (Button) getView().findViewById(R.id.commit); }

    public Button getDelete() { return (Button) getView().findViewById(R.id.delete); }

    public Button getDeleteall() { return (Button) getView().findViewById(R.id.deleteall); }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                addQuestion();
                break;
            case R.id.delete:
                String name = mEtname.getText().toString();
                delQuestion(name);
                break;
            case R.id.deleteall:
                delAllQuestion();
                break;
            case R.id.fix:
                fixQuestion();
                break;
                default:
        }
    }

    private void addQuestion() {
        getActivity().findViewById(R.id.swipe_refresh).setEnabled(true);
        String name = mEtname.getText().toString();
        String a = A.getText().toString();
        String b = B.getText().toString();
        String c = C.getText().toString();
        String d = D.getText().toString();
        String answer = anwser.getText().toString();
        String note = ETnote.getText().toString();
/*        if(!TextUtils.isEmpty(name)) {
            for (int i = 0; i <20 ; i++) {
                name = i+"";
                a = "A选项";
                b = "B选项";
                c = "C选项";
                d = "D选项";
                answer = "C";
                note = "不会就选C";
                addQuestion(name, a, b, c, d, answer, note);
            }
            addQuestion(name, a, b, c, d, answer, note);
        }else {
            Toast.makeText(getActivity(), "请输入题目名称", Toast.LENGTH_SHORT).show();
        }*/
        for (int i = 0; i <20 ; i++) {
            name = i+"";
            a = "A选项";
            b = "B选项";
            c = "C选项";
            d = "D选项";
            answer = "C";
            note = "不会就选C";
            addQuestion(name, a, b, c, d, answer, note);
        }
    }

    private void addQuestion(String name, String a, String b, String c, String d, String answer, String note) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("a", a);
        values.put("b", b);
        values.put("c", c);
        values.put("d", d);
        values.put("answer", answer);
        values.put("note", note);
        db.insert("question", null, values);
        db.close();
        Toast.makeText(getActivity(),"提交成功", Toast.LENGTH_SHORT).show();
        clearData();
    }

    private void fixQuestion() {
        getActivity().findViewById(R.id.swipe_refresh).setEnabled(true);
        String name = mEtname.getText().toString();
        String a = A.getText().toString();
        String b = B.getText().toString();
        String c = C.getText().toString();
        String d = D.getText().toString();
        String answer = anwser.getText().toString();
        String note = ETnote.getText().toString();
        if(!TextUtils.isEmpty(name)) {
            fixQuesion(name, a, b, c, d, answer, note);
        }else {
            Toast.makeText(getActivity(), "请输入题目名称", Toast.LENGTH_SHORT).show();
        }
    }

    private void fixQuesion(String name,String a, String b, String c, String d, String answer, String note){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("a", a);
        values.put("b", b);
        values.put("c", c);
        values.put("d", d);
        values.put("answer", answer);
        values.put("note", note);
        if (name.length() != 0) {
            db.update("question",values,"name=?",new String[]{name});
            Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
            clearData();
        } else {
            Toast.makeText(getActivity(), "该题目不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void delQuestion(String name) {
        getActivity().findViewById(R.id.swipe_refresh).setEnabled(true);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (name.length() != 0) {
            db.delete("question", "name=?", new String[]{name});
            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
            clearData();
        } else {
            Toast.makeText(getActivity(), "该题目不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void delAllQuestion() {
        getActivity().findViewById(R.id.swipe_refresh).setEnabled(true);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete("question", null, null);
        clearData();
    }

    private void clearData() {
        mEtname.setText("");
        A.setText("");
        B.setText("");
        C.setText("");
        D.setText("");
        anwser.setText("");
        ETnote.setText("");
    }

}
