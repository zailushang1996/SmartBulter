package com.exaccu.smartbulter.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exaccu.smartbulter.R;
import com.exaccu.smartbulter.entity.MyUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_now)
    EditText etNow;
    @BindView(R.id.et_new)
    EditText etNew;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.btn_update_password)
    Button btnUpdatePassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_forget_password)
    Button btnForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_update_password, R.id.btn_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update_password:
                String oldPass = etNow.getText().toString().trim();
                String newPass = etNew.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(oldPass) && !TextUtils.isEmpty(newPass) && !TextUtils.isEmpty(newPassword)) {
                    //判断两次输入的密码是否一致
                    if (newPass.equals(newPassword)) {
                        //比目云 重置密码
                        MyUser.updateCurrentUserPassword(oldPass, newPass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (null == e) {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                } 
                break;
            case R.id.btn_forget_password:
                final String email = etEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    //比目云 重置密码
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (null == e) {
                                Toast.makeText(ForgetPasswordActivity.this, "邮箱已经发送至" + email, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, "邮箱发送失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
