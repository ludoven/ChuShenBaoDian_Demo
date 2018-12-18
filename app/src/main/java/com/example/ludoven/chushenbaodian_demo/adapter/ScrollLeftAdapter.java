package com.example.ludoven.chushenbaodian_demo.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ludoven.chushenbaodian_demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类 左边列表
 */

public class ScrollLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    TextView tv;
    private String mSelected = null;
    private boolean mInitComplete=false;
    public ScrollLeftAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.left_text, item)
                .addOnClickListener(R.id.item);
        tv= helper.getView(R.id.left_text);
        //如果被选中
        if(item.equals(mSelected)) {
           // tv.setBackgroundColor(0xff0068b7); //设置背景色为蓝色
            tv.setBackgroundColor(Color.parseColor("#ffffff")); //设置背景色为白色
            tv.setTextColor(ContextCompat.getColor(mContext, R.color.app_red));//设置字体为红色

            //以下是指定某一个TextView跑马灯的效果
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv.setFocusable(true); //可以将焦点移到当前控件
            tv.setFocusableInTouchMode(true);// 可以通过触摸获取焦点
            tv.setMarqueeRepeatLimit(-1);//跑马灯  -1为无限循环   1为一次

        }else {
            tv.setBackgroundColor(Color.parseColor("#F0F0F0")); //设置背景色为白色
            tv.setTextColor(ContextCompat.getColor(mContext, R.color.color4));// 设置字体为黑色
            //失去焦点则停止滚动
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setFocusable(false);//不能将焦点移到当前控件
            tv.setFocusableInTouchMode(false);
            tv.setMarqueeRepeatLimit(0);
        }
    }


    public void selectItem(String selected) {
        mSelected = selected;
        //数据刷新
        notifyDataSetChanged();
    }
}
