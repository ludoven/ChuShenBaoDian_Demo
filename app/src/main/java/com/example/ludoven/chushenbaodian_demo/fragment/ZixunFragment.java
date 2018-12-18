package com.example.ludoven.chushenbaodian_demo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ludoven.chushenbaodian_demo.R;

import com.example.ludoven.chushenbaodian_demo.adapter.MsgAdapter;
import com.example.ludoven.chushenbaodian_demo.bean.Msg;
import com.example.ludoven.chushenbaodian_demo.util.HttpUtil;
import com.example.ludoven.chushenbaodian_demo.util.LogUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ZixunFragment extends Fragment {
    private List<Msg> mList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView mRecyclerView;
    private MsgAdapter mAdapter;
    private ImageView mImageView;
    private int code=100000;
    private   List<Msg.ListBean> beans;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_zixun, container, false);
        Msg msg3=new Msg("您可以问我一些问题哦^_^\n"+"比如:菜谱、土豆怎么做",Msg.TYPE_RECEIVED,code);
        mList.add(msg3);
        inputText=view.findViewById(R.id.input_text);
        mImageView=view.findViewById(R.id.zixun_back);
        send=view.findViewById(R.id.send);
        mRecyclerView=view.findViewById(R.id.recyclerview_msg);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new MsgAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.getBack();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=inputText.getText().toString();
                if (!"".equals(content)){
                    Msg sendmsg=new Msg(content,Msg.TYPE_SENT,code);
                    getData(content);
                    mList.add(sendmsg);
                    mAdapter.notifyItemInserted(mList.size()-1);
                    mRecyclerView.scrollToPosition(mList.size()-1);
                    inputText.setText("");
                }
            }
        });
        return view;
    }
    private void getData(String content){
        String adress="http://www.tuling123.com/openapi/api?key="+HttpUtil.zixun_key+"&info="+content+"";
        HttpUtil.sendOkhttpRequest(adress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                Log.d("json", " "+responseData);
                Gson gson=new Gson();

                final Msg msg=gson.fromJson(responseData,Msg.class);
                //如果是图文消息则 加载图文  308000：图文消息   100000：文本消息   200000：带链接的文本消息
                if (msg.getCode()==308000){
                    List<Msg.ListBean> mbean=msg.getList();

                    Msg msgText=new Msg(msg.getText(),Msg.TYPE_RECEIVED,msg.getCode(),mbean);
                    mList.add(msgText);
                }else if(msg.getCode()==200000){
                    Msg msgText=new Msg(msg.getText(),Msg.TYPE_RECEIVED,msg.getCode(),msg.getUrl());
                     mList.add(msgText);
                }
                else {
                    Msg msgText=new Msg(msg.getText(),Msg.TYPE_RECEIVED,msg.getCode());
                    mList.add(msgText);
                }

                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyItemInserted(mList.size()-1);
                        mRecyclerView.scrollToPosition(mList.size()-1);

                    }
                });
            }
        });
    }
}
