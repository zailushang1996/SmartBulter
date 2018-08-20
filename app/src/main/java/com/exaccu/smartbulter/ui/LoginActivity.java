package com.exaccu.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exaccu.smartbulter.MainActivity;
import com.exaccu.smartbulter.R;
import com.exaccu.smartbulter.entity.MyUser;
import com.exaccu.smartbulter.utils.ShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.keep_password)
    CheckBox keepPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btn_registered)
    Button btnRegistered;
    @BindView(R.id.tv_forget)
    TextView tvForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //设置选中状态
        boolean isCheck = ShareUtils.getBoolran(this, "keeppass", false);
        keepPassword.setChecked(isCheck);
        if (isCheck) {
            //设置密码
            etName.setText(ShareUtils.getString(this, "name", ""));
            etPassword.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @OnClick({R.id.keep_password, R.id.btnLogin, R.id.btn_registered})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.keep_password:
                break;
            case R.id.btnLogin:
                //获取输入框数据
                String name = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            //判断登录结果
                            if (null == e) {
                                /*if (user.getEmailVerified()) {
                                    判断邮箱是否验证，此功能已收费
                                }*/
                                 startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败！"+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
        }
    }

    //用户输入用户名和密码，但没有点击登录，而是直接退出
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this, "keeppass", keepPassword.isChecked());

        //是否记住密码
        if (keepPassword.isChecked()) {
            //记住用户名和密码
            ShareUtils.putString(this, "name", etName.getText().toString().trim());
            ShareUtils.putString(this, "password", etPassword.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }
    }
}
