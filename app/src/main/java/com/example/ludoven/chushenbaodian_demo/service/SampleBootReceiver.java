package com.example.ludoven.chushenbaodian_demo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ludoven.chushenbaodian_demo.activity.AlarmActivity;

public class SampleBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent(context, AlarmActivity.class);//定义要操作的Intent
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//传递一个新的任务标记
        context.startActivity(it);//启动Intent
    }
}
