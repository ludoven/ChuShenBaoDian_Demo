package com.example.ludoven.chushenbaodian_demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ludoven.chushenbaodian_demo.util.HttpUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebviewActivity extends Activity {
    public static String WEB_URL = "WEB_URL";
    private LinearLayout loadWeb;
    private boolean webIsTop,webIsLoadFinsh=false;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.prog_web)
    ProgressBar progressBar;
    @BindView(R.id.web_view_back)
    ImageView mImageView;
    private Unbinder mUnbinder;
    private int webTop=0,timeOut=5000;
    private TextView toastTimeOut;
    private Timer timer;
    private Runnable loadRunable=new Runnable() {
        @Override
        public void run() {
            if (loadWeb!=null&&mWebView!=null){
                mWebView.setY(webTop);
                loadWeb.setVisibility(View.INVISIBLE);
                mWebView.reload();
                webTop=0;
            }
            if (loadRunable!=null&&mWebView!=null){
                mWebView.removeCallbacks(loadRunable);
            }

        }
    };
    private View.OnTouchListener listener=new View.OnTouchListener() {
        int x=0,y=0;
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if (webIsTop){
                        x= (int) event.getX();
                        y= (int) event.getY();
                        webTop= (int) view.getY();
                    }

                    return  false;
                case MotionEvent.ACTION_MOVE:
                    int dx= (int) (event.getX()-x);
                    int mdy= (int) (event.getY()-y);
                    if(webIsTop) {
                        if (mdy > loadWeb.getHeight() / 2) {
                            if (mdy >= loadWeb.getHeight()) {
                                mWebView.setY(webTop + loadWeb.getHeight());
                            } else {
                                if (mWebView.getY() <= webTop + loadWeb.getHeight()) {
                                    mWebView.setY(mWebView.getY() + mdy);
                                } else {
                                    mWebView.setY(webTop + loadWeb.getHeight());
                                }
                            }
                            loadWeb.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        loadWeb.setVisibility(View.INVISIBLE);
                    }


                    break;
                case MotionEvent.ACTION_UP:
                    if (webIsTop) {
                        mWebView.postDelayed(loadRunable,3000);
                    }

                    break;
            }
            return false;
        }
    };
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                try{
                    if(url.startsWith("tbopen://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    return false;
                }
                mWebView.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(final WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                timer=new Timer();
                TimerTask timerTask=new TimerTask() {
                    @Override
                    public void run() {
                        if (!webIsLoadFinsh){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.stopLoading();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    toastTimeOut.setVisibility(View.VISIBLE);

                                }
                            });

                            timer.cancel();
                            timer.purge();
                        }
                    }
                };
                timer.schedule(timerTask,timeOut,1);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webIsLoadFinsh=true;
                String javascript = "javascript:function hideOther() {" +
                        "var hid=document.getElementById('site-header');" +
                        " hid.remove();" +
                        "document.querySelector('div#site-body>section.pop-lists>header>a').remove();" +
                        "document.querySelector('div#site-body>div:nth-child(3)>div:nth-child(3)').style.display='none';" +
                        "document.querySelector('div#site-body>div:nth-child(3)>div:nth-child(6)').style.display='none';" +
                        "document.querySelector('div#site-body>div:nth-child(5)>div:nth-child(3)').style.display='none';" +
                        "document.querySelector('div#site-body>div:nth-child(5)>div:nth-child(6)').style.display='none';" +
                        "document.querySelector('div#site-body>div:nth-child(7)>div:nth-child(4)').style.display='none';" +
                        "document.querySelector('div#site-body>div:nth-child(7)>div:nth-child(6)').style.display='none'; " +
                        "}";

                //创建方法
                view.loadUrl(javascript);
                //加载方法
                view.loadUrl("javascript:hideOther();");
                if (mWebView!=null){
                    mWebView.setVisibility(View.VISIBLE);
                    toastTimeOut.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }


                timer.cancel();
                timer.purge();
            }
        });
        mWebView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.d("this", "onScrollChange: scy"+view.getScrollY()+" i:"+i+"  "+i1+"   "+i2+"   "+i3);
                if (view.getScrollY()==0){
                    webIsTop=true;
                }
                else {
                    webIsTop=false;
                }
            }
        });
        webIsTop=true;
    }
    private void initView(){
        mUnbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        loadWeb=findViewById(R.id.loading_web);
        toastTimeOut=findViewById(R.id.timeOut);
        String url = intent.getStringExtra(WEB_URL);
        progressBar.setVisibility(View.VISIBLE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setOnTouchListener(listener);
        HashMap<String, String> header = new HashMap<>();
        header.put("token", "android");
        mWebView.loadUrl(url, header);
        mWebView.setVisibility(View.INVISIBLE);
        toastTimeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.reload();
            }
        });
        findViewById(R.id.web_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack()){
                    mWebView.goBack();
                }
                else {
                    finish();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击回退键时，不会退出浏览器而是返回网页上一页
        if ((keyCode == KEYCODE_BACK) ) {
            if (mWebView.canGoBack()){
                mWebView.goBack();
            }
            else {
                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.web_view_back)
    public void onMWebViewBackClicked() {
        HttpUtil.getBack();
    }



}
