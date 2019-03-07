package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;

public class ForgotPasswdActivity extends AppCompatActivity implements View.OnClickListener {

    final String address = "http://47.102.199.28/flyapp/updateUser";
    private TextView mtv_backlogin ;//返回登录
    private EditText mtv_username;//账号
    private EditText mtv_newpassword;//新密码
    private EditText mtv_ensurepassword; //确认新密码
    private EditText mtv_yaoyifei;//作者
    private Button mbtn_findpasswd;//找回密码
    private RadioGroup mRadioGroup;//身份选择
    private RadioButton mRadioButtonStudent;//学生
    private RadioButton mRadioButtonTeacher;//教师
   // private DBHelper mDbHelper; //操作数据库的类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passwd);
       // mDbHelper = new DBHelper(this, "User.db", null, 1); //创建数据库
        initView();
        initListener();
    }


    void initView() {
        mtv_backlogin = findViewById(R.id.back_login);
        mtv_username = findViewById(R.id.user_name);
        mtv_newpassword = findViewById(R.id.new_password);
        mtv_ensurepassword = findViewById(R.id.ensure_new_password);
        mtv_yaoyifei = findViewById(R.id.yaoyifei);
        mbtn_findpasswd = findViewById(R.id.findpasswd_button);
        mRadioGroup = findViewById(R.id.radio_group);
        mRadioButtonStudent = findViewById(R.id.radio_stu);
        mRadioButtonTeacher = findViewById(R.id.radio_tea);
    }


    void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_stu:
                        mRadioButtonStudent.setChecked(true);
                        break;
                    case R.id.radio_tea:
                        mRadioButtonTeacher.setChecked(true);
                        break;
                }
            }
        });
        mtv_backlogin.setOnClickListener(this);
        mbtn_findpasswd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findpasswd_button:
                findPassword();
                break;
            case R.id.back_login:
                Intent intent = new Intent(ForgotPasswdActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    public void findPassword(){
        String username = mtv_username.getText().toString();
        String newpassword = mtv_newpassword.getText().toString();
        String ensurepassword = mtv_ensurepassword.getText().toString();
        int usertype = mRadioButtonStudent.isChecked()?0:1;
        String yaoyifei = mtv_yaoyifei.getText().toString();
        if(!newpassword.equals(ensurepassword)) {
            Toast.makeText(ForgotPasswdActivity.this,"两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
        }else if(newpassword.length()<6) {
            Toast.makeText(ForgotPasswdActivity.this,"请保持密码长度不小于6位",Toast.LENGTH_SHORT).show();
        }else if(!yaoyifei.equals("yaoyifei")){
            Toast.makeText(ForgotPasswdActivity.this,"作者不叫这个哦",Toast.LENGTH_SHORT).show();
        }else {
            HttpUtil.login(address, username, newpassword, usertype, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Looper.prepare();
                    if (response.equals("1")){
                        new android.support.v7.app.AlertDialog.Builder(ForgotPasswdActivity.this).setTitle("密码修改成功！")
                                .setMessage("是否去登录").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ForgotPasswdActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("取消", null).show();
                    }else if(response.equals("0")) {
                        Toast.makeText(ForgotPasswdActivity.this,"您所修改的账号身份选择有误，请更正",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ForgotPasswdActivity.this,"该账号不存在或身份选择错误",Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }
                @Override
                public void onError(Exception e) {
                    Looper.prepare();
                    Toast.makeText(ForgotPasswdActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });

        }
    }

    /*public void findPassword(){
        String username = mtv_username.getText().toString();
        String newpassword = mtv_newpassword.getText().toString();
        String ensurepassword = mtv_ensurepassword.getText().toString();
        String usertype = mRadioButtonStudent.isChecked()?0+"":1+"";
        String yaoyifei = mtv_yaoyifei.getText().toString();

        if(!CheckIsDataAlreadyInDBorNot(username,usertype)){
            Toast.makeText(ForgotPasswdActivity.this,"该用户名不存在，请检查身份是否选择错误！",Toast.LENGTH_SHORT).show();
        }else if(!newpassword.equals(ensurepassword)) {
            Toast.makeText(ForgotPasswdActivity.this,"两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
        }else if(newpassword.length()<6) {
            Toast.makeText(ForgotPasswdActivity.this,"请保持密码长度不小于6位",Toast.LENGTH_SHORT).show();
        }else if(!yaoyifei.equals("yaoyifei")){
            Toast.makeText(ForgotPasswdActivity.this,"作者不叫这个哦",Toast.LENGTH_SHORT).show();
        }else {
            updateUserInfo(username,newpassword,usertype);
            Toast.makeText(ForgotPasswdActivity.this,"修改密码成功 ",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPasswdActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    *//**
     * 检验用户名是否已经存在
     *//*
    public boolean CheckIsDataAlreadyInDBorNot(String value,String usertype) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String Query = "Select * from usertable where username =? and usertype=?";
        Cursor cursor = db.rawQuery(Query, new String[]{value,usertype});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }
    *//**
     * 根据用户名更新数据库中的用户信息
     *//*
    public void updateUserInfo(String username,String userpassword,String usertype) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password", userpassword);
        values.put("usertype",usertype);
        db.update("usertable", values,"username=?",new String[]{username});
        db.close();
    }*/
}
