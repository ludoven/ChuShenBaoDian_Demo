package com.example.ludoven.chushenbaodian_demo.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.bean.CaipuData;
import com.example.ludoven.chushenbaodian_demo.bean.DataBean;
import com.example.ludoven.chushenbaodian_demo.bean.ListBean;
import com.example.ludoven.chushenbaodian_demo.bean.MethodBean;
import com.example.ludoven.chushenbaodian_demo.bean.ResponseBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/*
   工具类
 */
public class HttpUtil {
    public static String key="a2b34f8a1d3933cac953fd771ff44825";
    //public static String key="a780eff45da836aa7d594afea76a73b2";//备用
    public  static String zixun_key="aa8338c43b524198bffae27255e55082";

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
    /*
     封装okhttp3
     */
    public static  void sendOkhttpRequest(String address, Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Gson 解析
     * @param responseData
     * @return
     */
  public  static  List<DataBean> getData(String responseData){
        Gson gson=new Gson();
        ResponseBean responseBean=gson.fromJson(responseData,ResponseBean.class);
        ResponseBean.ResultBean resultBean=responseBean.getResult();
        List<DataBean> dataBeanList=resultBean.getData();
        return dataBeanList;
  }    /**
     * Gson 解析
     * @param responseData
     * @return
     */
  public  static  List<ListBean> getCaipuData(String responseData){
        Gson gson=new Gson();

      CaipuData caipuData=gson.fromJson(responseData,CaipuData.class);
      CaipuData.ResultBean resultBean=caipuData.getResult();
       List<ListBean> listBeanList=resultBean.getList();
        return listBeanList;
  }



    /**
     * 获取 系统返回按钮的操作     就是返回
     */
   public static void getBack(){
       Runtime runtime = Runtime.getRuntime();
       try {
           runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   /*
     Json中String 字符串转换为List
    */
   public static List<Object> getListJson(String jsonStr,Class<?>classT){
       List<Object> list=new ArrayList<Object>();
       JsonArray jsonArray=new Gson().fromJson(jsonStr,JsonArray.class);
       for (int i=0;i<jsonArray.size();i++){
           list.add(new Gson().fromJson(jsonArray.get(i),classT));
       }
       return list;
   }

}
