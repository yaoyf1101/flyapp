package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    final String address = "http://47.102.199.28/flyapp/addUser";
    private TextView mtv_backlogin ;
    private EditText mtv_newname;
    private EditText mtv_newpassword;
    private EditText mtv_ensurepassword;
    private Button mbtn_register;
    private RadioGroup mRadioGroup;//身份选择
    private RadioButton mRadioButtonStudent;//学生注册
    private RadioButton mRadioButtonTeacher;//教师注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
        nameFilter();
    }

    void initView() {
        mtv_backlogin = findViewById(R.id.back_login);
        mtv_newname = findViewById(R.id.new_name);
        mtv_newpassword = findViewById(R.id.new_password);
        mtv_ensurepassword = findViewById(R.id.ensure_new_password);
        mbtn_register = findViewById(R.id.register_button);
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
        mbtn_register.setOnClickListener(this);
    }

    public void nameFilter() {
        mtv_newname.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if (!Character.isLetterOrDigit(source.charAt(i)) &&
                                    !Character.toString(source.charAt(i)).equals("_")) {
                                Toast.makeText(RegisterActivity.this, "只能使用下划线、字母、数字、汉字注册！", Toast.LENGTH_SHORT).show();
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                register();
                break;
            case R.id.back_login:
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    public void register(){
        String newname = mtv_newname.getText().toString();
        String newpassword = mtv_newpassword.getText().toString();
        String ensurepassword = mtv_ensurepassword.getText().toString();
        int usertype = mRadioButtonStudent.isChecked()?0:1;
        if(!newpassword.equals(ensurepassword)) {
            Toast.makeText(RegisterActivity.this,"两次输入的密码不一致，请再输一次吧",Toast.LENGTH_SHORT).show();
        }else if(newpassword.length()<6) {
            Toast.makeText(RegisterActivity.this,"请保持密码长度不小于6位",Toast.LENGTH_SHORT).show();
        }else if(usertype==1){
            Toast.makeText(RegisterActivity.this,"请联系作者申请教师账号",Toast.LENGTH_SHORT).show();
        }else {
                HttpUtil.login(address, newname, newpassword, usertype, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Looper.prepare();
                        if (response.equals("1")) {
                            new android.support.v7.app.AlertDialog.Builder(RegisterActivity.this).setTitle("恭喜，注册完成！")
                                    .setMessage("是否去登录").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).setNegativeButton("取消", null).show();
                        }else{
                            Toast.makeText(RegisterActivity.this,"注册失败,用户名已经存在！",Toast.LENGTH_SHORT).show();
                        }
                        Looper.loop();
                    }
                    @Override
                    public void onError(Exception e) {
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        }

}
