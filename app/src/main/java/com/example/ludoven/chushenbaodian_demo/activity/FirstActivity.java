package com.example.ludoven.chushenbaodian_demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.ludoven.chushenbaodian_demo.MainActivity;
import com.example.ludoven.chushenbaodian_demo.R;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class FirstActivity extends Activity {
     private boolean check=false;
    private BGABanner mBackgroundBanner;
    private BGABanner mForegroundBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        processLogic();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
                check=pref.getBoolean("appCheck",false);
                if (check){
                    Intent intent2=new Intent();
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.setClass(FirstActivity.this,MainActivity.class);
                    startActivity(intent2);
                }else {
                    Intent intent=new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(FirstActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        },5000);*/
    }
    private void initView() {
        setContentView(R.layout.activity_first);
        mBackgroundBanner = findViewById(R.id.banner_guide_background);
        mForegroundBanner = findViewById(R.id.banner_guide_foreground);
    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(FirstActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.first_one,
                R.drawable.first_two,
                R.drawable.first_three);
        mForegroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_foreground_1,
                R.drawable.uoko_guide_foreground_2,
                R.drawable.uoko_guide_foreground_3);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
       mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}
