package com.is.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //避免布局文件加载开销
        mHandler = new Handler();
        mHandler.postDelayed(()-> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        },500);//极短延迟保证视觉连贯性
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关键：移除所有回调，避免泄漏
        mHandler.removeCallbacksAndMessages(null);
    }
}
