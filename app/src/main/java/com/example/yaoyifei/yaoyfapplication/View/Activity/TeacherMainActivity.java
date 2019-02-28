package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.SQLiteUtil.DBHelper;
import com.example.yaoyifei.yaoyfapplication.R;

public class TeacherMainActivity extends AppCompatActivity {

    EditText mEtname;
    EditText A,B,C,D;
    EditText anwser;
    EditText ETnote;
    Button commit;
    DBHelper mDbHelper;
    Button delete;
    Button deleteall;
    Button backtohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        mEtname = (EditText) findViewById(R.id.ETname);
        A = (EditText) findViewById(R.id.A);
        B = (EditText) findViewById(R.id.B);
        C = (EditText) findViewById(R.id.C);
        D = (EditText) findViewById(R.id.D);
        anwser = (EditText) findViewById(R.id.anwser);
        ETnote = (EditText) findViewById(R.id.ETnote);
        commit = (Button) findViewById(R.id.commit);
        delete = (Button) findViewById(R.id.delete);
        deleteall = (Button) findViewById(R.id.deleteall);
        backtohome = (Button) findViewById(R.id.backtohome);
        findViewById(R.id.backtohome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked((View) v);
            }
        });
        findViewById(R.id.deleteall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked((View) v);
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked((View) v);
            }
        });
        findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked((View) v);
            }
        });
        mDbHelper = new DBHelper(this, "Test.db", null, 2); //创建数据库
    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.backtohome:
                Intent intent = new Intent(TeacherMainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void addQuestion() {
        String name = mEtname.getText().toString();
        String a = A.getText().toString();
        String b = B.getText().toString();
        String c = C.getText().toString();
        String d = D.getText().toString();
        String answer = anwser.getText().toString();
        String note = ETnote.getText().toString();
        addQuestion(name, a, b, c, d, answer, note);
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
        Toast.makeText(TeacherMainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
        clearData();
    }

    private void delQuestion(String name) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (name.length() != 0) {
            db.delete("question", "name=?", new String[]{name});
            Toast.makeText(TeacherMainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            clearData();
        } else {
            Toast.makeText(TeacherMainActivity.this, "该题目不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void delAllQuestion() {
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
