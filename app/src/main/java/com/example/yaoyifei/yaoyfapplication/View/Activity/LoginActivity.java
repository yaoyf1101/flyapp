package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.yaoyifei.yaoyfapplication.SQLiteUtil.DBHelper;
import com.example.yaoyifei.yaoyfapplication.tools.HttpCallbackListener;
import com.example.yaoyifei.yaoyfapplication.tools.HttpUtil;
import com.example.yaoyifei.yaoyfapplication.tools.SP;
import com.example.yaoyifei.yaoyfapplication.R;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button mBtnLogin; //登录按钮
    TextView mTvRegister;//注册入口
    EditText metName; //用户账号
    EditText metPassword; //用户密码
    TextView mTvdisbaleLogin;//无法登录
    RadioGroup mRadioGroup;//身份选择
    RadioButton mRadioButtonStudent;//学生登录入口
    RadioButton mRadioButtonTeacher;//教师登录入口
    CheckBox mCheckBoxRememberPasswd;//记住密码
    CheckBox mCheckBoxAutoLogin;//自动登录
    public static SP mSp;//处理用户偏好设置的工具类
    public DBHelper mDbHelper;//数据库工具类
    public Context mContext;//上下文

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        mSp = new SP(mContext);
        mDbHelper = new DBHelper(this, "Test.db", null, 2); //创建数据库
        initView();
        initListener();
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
                loginOrNot();
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

    private void loginOrNot() {
        String name = metName.getText().toString();
        String password = metPassword.getText().toString();
        String usertype = mRadioButtonStudent.isChecked()?0+"":1+"";
        boolean isRemember = mCheckBoxRememberPasswd.isChecked();
        boolean isStudent = mRadioButtonStudent.isChecked();
        boolean isTeacher = mRadioButtonTeacher.isChecked();
        boolean isAutoLogin = mCheckBoxAutoLogin.isChecked();
        mSp.save(name,password,isStudent,isTeacher,isRemember,isAutoLogin);
        if (isStudent) { //学生身份登录验证逻辑
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                if (login(name,password,usertype)) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码有误,请重新输入", Toast.LENGTH_SHORT).show();
                }
            } else if (TextUtils.isEmpty(name)) {
                Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            }
        }
        if(isTeacher){ //教师身份登录验证逻辑
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                if (login(name,password,usertype)) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码有误,请重新输入", Toast.LENGTH_SHORT).show();
                }
            } else if (TextUtils.isEmpty(name)) {
                Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 验证登录信息
     * */
    private boolean login(String username, String password, String usertype) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String sql = "Select * from usertable where username=? and password=? and usertype=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password, usertype});
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    private boolean login(String username, String password) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String sql = "Select * from usertable where username=? and password=? ";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        autoLogin();
    }

    private void autoLogin() {
        Map<String, Object> data = mSp.read();
        if ((Boolean) data.get("isAutoLogin")) {
            if (login(data.get("username").toString(),data.get("password").toString())
                    && (Boolean) data.get("isStudent")) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            if (login(data.get("username").toString(),data.get("password").toString())
                    && (Boolean) data.get("isTeacher")) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
        if ((Boolean) data.get("isRemember")) {
                metName.setText(data.get("username").toString());
                metPassword.setText(data.get("password").toString());
                mCheckBoxRememberPasswd.setChecked((Boolean) data.get("isRemember"));
                mRadioButtonStudent.setChecked((Boolean) data.get("isStudent"));
                mRadioButtonTeacher.setChecked((Boolean) data.get("isTeacher"));
        }
    }

    public void clearData() {
        mSp.clear();
    }

}
