package com.example.ludoven.chushenbaodian_demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.WebviewActivity;
import com.example.ludoven.chushenbaodian_demo.bean.Msg;

import java.util.List;

/**
 * 加载咨询 界面 的消息适配器
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mList;

    private Context mContext;
    public MsgAdapter(List<Msg>msgList){
        this.mList=msgList;
    }
    @NonNull
    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
      final Msg msg=mList.get(position);
      if (msg.getType()==Msg.TYPE_RECEIVED){
          holder.left_layout.setVisibility(View.VISIBLE);
          holder.right_layout.setVisibility(View.GONE);
          if (msg.getCode()==308000){
              for (int i=0;i<msg.getList().size();i++){
                  holder.leftMsg.append(msg.getList().get(i).getName()+"\n"+msg.getList().get(i).getDetailurl()+"\n");
              }
              textHtmlClick(mContext,holder.leftMsg);

          }else if (msg.getCode()==200000){
              holder.leftMsg.setText(msg.getText()+"\n"+msg.getUrl());
              textHtmlClick(mContext,holder.leftMsg);
          }
          else {
              holder.leftMsg.setText(msg.getText());
          }

      }
      else if (msg.getType()==Msg.TYPE_SENT){
          holder.right_layout.setVisibility(View.VISIBLE);
          holder.left_layout.setVisibility(View.GONE);
          holder.rightMsg.setText(msg.getText());
      }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout left_layout;
        LinearLayout right_layout;
        TextView rightMsg,leftMsg;
        public ViewHolder(View itemView) {
            super(itemView);
          // mRecyclerView=itemView.findViewById(R.id.recyclerview_received);
          left_layout=itemView.findViewById(R.id.left_layout);
          right_layout=itemView.findViewById(R.id.right_layout);
          rightMsg=itemView.findViewById(R.id.right_mag);
          leftMsg=itemView.findViewById(R.id.text_received);

        }
    }
    /**
     * 处理html文本超链接点击事件
     * @param context
     * @param tv
     */
    public static void textHtmlClick(Context context, TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();// should clear old spans
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(url.getURL(), context);
                style.setSpan(myURLSpan, sp.getSpanStart(url),
                        sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            tv.setText(style);
        }
    }
    private static class MyURLSpan extends ClickableSpan {
        private String mUrl;
        private Context mContext;

        MyURLSpan(String url, Context context) {
            mContext = context;
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {
           Intent intent=new Intent(mContext,WebviewActivity.class);
           intent.putExtra("WEB_URL",mUrl);
           mContext.startActivity(intent);
            // Toast.makeText(mContext,"跳转网页"+mUrl,Toast.LENGTH_SHORT).show();
        }
    }
}
