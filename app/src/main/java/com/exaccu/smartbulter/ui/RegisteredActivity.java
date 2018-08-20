package com.exaccu.smartbulter.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.exaccu.smartbulter.R;
import com.exaccu.smartbulter.entity.MyUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisteredActivity extends BaseActivity {

    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.rb_boy)
    RadioButton rbBoy;
    @BindView(R.id.rb_girl)
    RadioButton rbGirl;
    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btnRegistered)
    Button btnRegistered;

    //性别
    private boolean isGender = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rb_boy, R.id.rb_girl, R.id.btnRegistered})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_boy:
                break;
            case R.id.rb_girl:
                break;
            case R.id.btnRegistered:
                //获取输入框的值
                String name = etUser.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                final String pass = etPass.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {

                    //判断两次的密码是否一致
                    if (pass.equals(password)) {
                        //判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                if (checkedId == R.id.rb_boy) {
                                    isGender = true;
                                } else if (checkedId == R.id.rb_girl) {
                                    isGender = false;
                                }
                            }
                        });
                        //判断简介是否为空
                        if (TextUtils.isEmpty(desc)) {
                            desc = getString(R.string.no_desc);
                        }
                        //注册
                        MyUser myUser = new MyUser();
                        myUser.setUsername(name);
                        myUser.setAge(Integer.parseInt(age));
                        myUser.setPassword(password);
                        myUser.setSex(isGender);
                        myUser.setDesc(desc);
                        myUser.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (null == e) {
                                    Toast.makeText(RegisteredActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisteredActivity.this, "注册失败"+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(this, R.string.two_pass_not_same,Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, R.string.input_cannot_empty,Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
