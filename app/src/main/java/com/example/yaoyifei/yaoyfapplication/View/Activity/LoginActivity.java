package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.example.yaoyifei.yaoyfapplication.tools.SP;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    final String address = "http://47.102.199.28/flyapp/login";
    public Context mContext;//上下文
    private Button mBtnLogin; //登录按钮
    private TextView mTvRegister;//注册入口
    private EditText metName; //用户账号
    private EditText metPassword; //用户密码
    private TextView mTvdisbaleLogin;//无法登录
    private RadioGroup mRadioGroup;//身份选择
    private RadioButton mRadioButtonStudent;//学生登录入口
    private RadioButton mRadioButtonTeacher;//教师登录入口
    private CheckBox mCheckBoxRememberPasswd;//记住密码
    private CheckBox mCheckBoxAutoLogin;//自动登录
    public static SP mSp;//处理用户偏好设置的工具类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        mSp = new SP(mContext);
        initView();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        autoLogin();
    }

    void initView() {
        mBtnLogin = findViewById(R.id.login_button);
        mTvRegister = findViewById(R.id.news);
        metName = findViewById(R.id.name);
        metPassword = findViewById(R.id.password);
        mTvdisbaleLogin = findViewById(R.id.non);
        mRadioGroup = findViewById(R.id.radio_group);
        mRadioButtonStudent = findViewById(R.id.radio_stu);
        mRadioButtonTeacher = findViewById(R.id.radio_tea);
        mCheckBoxRememberPasswd = findViewById(R.id.rememberPass);
        mCheckBoxAutoLogin = findViewById(R.id.autologin);
    }


    void initListener() {
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mTvdisbaleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this,ForgotPasswdActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
        mCheckBoxRememberPasswd.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckBoxRememberPasswd.setChecked(isChecked);
            }
        });
        mCheckBoxAutoLogin.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckBoxAutoLogin.setChecked(isChecked);
            }
        });
    }

    public void login(){
        String name = metName.getText().toString();
        String password = metPassword.getText().toString();
        int usertype = mRadioButtonStudent.isChecked()?0:1;
        boolean isRemember = mCheckBoxRememberPasswd.isChecked();
        boolean isStudent = mRadioButtonStudent.isChecked();
        boolean isTeacher = mRadioButtonTeacher.isChecked();
        boolean isAutoLogin = mCheckBoxAutoLogin.isChecked();
        mSp.save(name,password,isStudent,isTeacher,isRemember,isAutoLogin);
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
        } else if((TextUtils.isEmpty(password))){
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        }else {
            HttpUtil.login(address, name, password, usertype, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Looper.prepare();
                    if (response.equals("1")) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(response.equals("2")){
                        Intent intent = new Intent(LoginActivity.this, TeacherHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "请确认密码以及身份是否选择正确", Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }
                @Override
                public void onError(Exception e) {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });
        }
    }

    private void autoLogin() {
        Map<String, Object> data = mSp.read();
        if (mSp!=null){
            if ((Boolean) data.get("isRemember")) {
                metName.setText(data.get("username").toString());
                metPassword.setText(data.get("password").toString());
                mCheckBoxRememberPasswd.setChecked((Boolean) data.get("isRemember"));
                mRadioButtonStudent.setChecked((Boolean) data.get("isStudent"));
                mRadioButtonTeacher.setChecked((Boolean) data.get("isTeacher"));
            }
            if ((Boolean) data.get("isAutoLogin")) {
                if ((Boolean) data.get("isStudent")){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else if ((Boolean) data.get("isTeacher")){
                    Intent intent = new Intent(LoginActivity.this, TeacherHomeActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        }
    }

    public void clearData() {
        mSp.clear();
    }
}
