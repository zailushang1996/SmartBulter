package com.exaccu.smartbulter.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.exaccu.smartbulter.MainActivity;
import com.exaccu.smartbulter.R;
import com.exaccu.smartbulter.utils.ShareUtils;
import com.exaccu.smartbulter.utils.StaticClass;
import com.exaccu.smartbulter.utils.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 闪屏页
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.tv_splash)
    TextView tvSplash;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirstRun()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //延时2秒
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        //设置字体
        UtilTools.setFont(this,tvSplash);

    }

    /**
     * 判断是否是第一次运行
     * @return true表示第一次运行，false反之
     */
    private boolean isFirstRun() {
        boolean isFirst = ShareUtils.getBoolran(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
