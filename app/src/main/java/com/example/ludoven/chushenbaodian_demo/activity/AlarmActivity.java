package com.example.ludoven.chushenbaodian_demo.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ludoven.chushenbaodian_demo.MainActivity;
import com.example.ludoven.chushenbaodian_demo.R;

import java.util.Calendar;

public class AlarmActivity extends Activity {
  MediaPlayer mPlayer;
  private AlertDialog mAlertDialog;
  private AlertDialog.Builder mBuilder=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmMusic_play();
        mAlertDialog=null;
        mBuilder=new AlertDialog.Builder(this);
        mAlertDialog=mBuilder.setIcon(R.drawable.ic_launcher_background)
                .setTitle("小厨神提醒您^_^")
                .setMessage("该做菜啦")
                .setNegativeButton("延迟一小时", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alarmMusic_stop();
                        alarmdelay();
                        AlarmActivity.this.finish();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alarmMusic_stop();
                        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        manager.cancel(1);
                        AlarmActivity.this.finish();
                    }
                }).create();//创建
        mAlertDialog.show();
    }
    private void alarmMusic_play(){
        //加载指定音乐，并为之创建MediaPlayer对象
        mPlayer=MediaPlayer.create(this,R.raw.shape);
        mPlayer.setLooping(true);
        //播放闹钟
        mPlayer.start();
    }
    private void alarmMusic_stop(){
        //停止音乐
        mPlayer.stop();
        if (mManager!=null){
            mManager.cancel(alarmIntent);
        }
    }
    private AlarmManager mManager;
    private PendingIntent alarmIntent;
    private void alarmdelay(){
        mManager= (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,MainActivity.class);
        alarmIntent=PendingIntent.getBroadcast(this,0,intent,0);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour=calendar.get(Calendar.HOUR);
        int min=calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.HOUR,hour);
        calendar.set(Calendar.MINUTE,min);
        mManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*60,alarmIntent);
    }
}
