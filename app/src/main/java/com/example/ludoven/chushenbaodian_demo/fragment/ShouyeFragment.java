package com.example.ludoven.chushenbaodian_demo.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.adapter.CaipuAdapter;
import com.example.ludoven.chushenbaodian_demo.bean.DataBean;
import com.example.ludoven.chushenbaodian_demo.bean.ListBean;
import com.example.ludoven.chushenbaodian_demo.util.GlideRoundTransform;
import com.example.ludoven.chushenbaodian_demo.util.HttpUtil;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.config.MDialogConfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShouyeFragment extends Fragment{
    @BindView(R.id.custom_banner) CustomBanner customBanner;
    @BindView(R.id.recyclerview_tuijian) RecyclerView mRecyclerView;
    @BindView(R.id.recyclerview_caidan) RecyclerView mRecyclerView_caidan;
    @BindView(R.id.linear_sousuo) LinearLayout mLinearLayout;
    @BindView(R.id.text_tuijian_1) TextView mTextTuijian1;
    @BindView(R.id.text_tuijian_2) TextView mTextTuijian2;
    @BindView(R.id.text_tuijian_3) TextView mTextTuijian3;
    @BindView(R.id.text_tuijian_4) TextView mTextTuijian4;
    @BindView(R.id.text_tuijian_5) TextView mTextTuijian5;
    private CaipuAdapter mCaipuAdapter,mCaipuAdapter2;
    @BindView(R.id.text_shouye_update)TextView mTextUpdate;
    private Unbinder mUnbinder;
      private List<ListBean> listBeanList;
      private List<ListBean> mListBeanList=new ArrayList<>();

    private   String responseData;
    private String caipu_menu = "猪";
    //推荐的菜单
    private String tuijian_1="奶";
    private String tuijian_2="土豆";
    private String tuijian_3="沙拉";
    private String tuijian_4="饺子";
    private String tuijian_5="汤";
    //轮播菜单
    private String customBannerName_1="布丁";
    private String customBannerName_2="蛋糕";
    private String customBannerName_3="水果";
    private String customBannerName_4="面";
    private List<String> imgList=new ArrayList<>();
    private int pn=0;
    private String img1="蟹";
    private String img2="粥";
    private String img3="烤";
    private String img4="饭";
    private String img5="饼";
    private Context mContext;
    String customBanner_Image4 = "http://www.ioix.top/csbd/mian.jpg";//面
     String customBanner_Image2 = "http://www.ioix.top/csbd/dangao.jpg";//蛋糕
      String customBanner_Image3 = "http://www.ioix.top/csbd/shuiguo.jpg";//水果
      String customBanner_Image1 = "http://www.ioix.top/csbd/buding.jpg";//布丁
    private String url1="http://www.ioix.top/csbd/pangxie.jpg";
    private String url2="http://www.ioix.top/csbd/bing.jpg";
    private String url3="http://www.ioix.top/csbd/fan.jpeg";
    private String url4="http://www.ioix.top/csbd/shaokao.jpg";
    private String url5="http://www.ioix.top/csbd/zhou.jpg";


    //img
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shouye, container, false);
        if(mContext==null){
            mContext=getContext();
        }
        mUnbinder = ButterKnife.bind(this, view);
        imgList.add(url1);
        imgList.add(url2);
        imgList.add(url3);
        imgList.add(url4);
        imgList.add(url5);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView_caidan.setLayoutManager(layoutManager);
        mCaipuAdapter2=new CaipuAdapter(imgList,R.layout.item_shouye_caidan);
        mRecyclerView_caidan.setAdapter(mCaipuAdapter2);

        mCaipuAdapter2.setItemClickListener(new CaipuAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        showLiebiaoFragment(img1);
                        break;
                    case 1:
                        showLiebiaoFragment(img5);
                        break;
                    case 2:
                        showLiebiaoFragment(img4);
                        break;
                    case 3:
                        showLiebiaoFragment(img3);
                        break;
                    case 4:
                        showLiebiaoFragment(img2);
                        break;
                }
            }
        });

        MProgressDialog.showProgress(mContext,"菜谱正在路上^_^");
        init(caipu_menu);
        setBean();


        // 搜索框点击事件
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(new SousuoFragment());
            }
        });
        mTextUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView(listBeanList);
                mCaipuAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    private void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 切换到列表
     * @param query  内容
     */
    private void showLiebiaoFragment(String query) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, new LiebiaoFragment(query));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void init(String caipu_menu) {
        String adress="http://apicloud.mob.com/v1/cook/menu/search?key=2884a62c9ba96&name="+caipu_menu+"&page=2&size=30";
        HttpUtil.sendOkhttpRequest(adress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response){
                try {
                    responseData = response.body().string();
                  //   dataBeanList= HttpUtil.getData(responseData);
                     listBeanList=HttpUtil.getCaipuData(responseData);
                      setView(listBeanList);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
private void setView(final List<ListBean> listBeanList){
        mListBeanList.clear();
        if (pn==30){
            pn=0;
        }
        for (int i=pn;i<pn+1;i++){
            mListBeanList.add(listBeanList.get(i));
        }
        pn=pn+1;
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                //使用线性布局
                LinearLayoutManager manager = new LinearLayoutManager(getContext()){
                    //解决ScrollView嵌套RecyclerView时，惯性滑动失效的问题
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
         //  LinearLayoutManager manager=new LinearLayoutManager(getContext());

                mRecyclerView.setLayoutManager(manager);
              //  mRecyclerView.setNestedScrollingEnabled(false);
                mCaipuAdapter = new CaipuAdapter(mListBeanList, R.layout.item_tuijian);
                mRecyclerView.setAdapter(mCaipuAdapter);

               mCaipuAdapter.setItemClickListener(new CaipuAdapter.MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //在这个碎片中 创建 详情页 碎片的实例
                        XiangQingFragment fragment=new XiangQingFragment();
                        Bundle bundle=new Bundle();
                        ListBean listBean=mListBeanList.get(position);
                        bundle.putSerializable("list",listBean);
                        fragment.setArguments(bundle);
                        showFragment(fragment);
                    }
                });
            }
        });
    }


    //设置普通指示器  轮播图的点击事件、设置
    private void setBean() {
        ArrayList<String> images = new ArrayList<>();
        images.add(customBanner_Image1);
        images.add(customBanner_Image2);
        images.add(customBanner_Image3);
        images.add(customBanner_Image4);
        customBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String entity) {
                //通过glide 加载网络图片
                // transform 将图片设置为圆角
                Glide.with(context).load(entity)
                        .transform(new GlideRoundTransform(context, 5))
                        .into((ImageView) view);
            }
        }, images)
                .startTurning(5000)//设置轮播图片间隔
                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)
                //圆点的切换
                .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect);
        /*
         轮播图的点击事件
         */
        customBanner.setOnPageClickListener(new CustomBanner.OnPageClickListener() {
            @Override
            public void onPageClick(int i, Object o) {
                switch (i) {
                    case 0:
                        showLiebiaoFragment(customBannerName_1);
                        break;
                    case 1:
                        showLiebiaoFragment(customBannerName_2);
                        break;
                    case 2:
                        showLiebiaoFragment(customBannerName_3);
                        break;
                    case 3:
                        showLiebiaoFragment(customBannerName_4);
                        break;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
   /*
     菜单的点击事件
    */
    @OnClick(R.id.text_tuijian_1)
    public void onMTextTuijian1Clicked() {
        showLiebiaoFragment(tuijian_1);
    }

    @OnClick(R.id.text_tuijian_2)
    public void onMTextTuijian2Clicked() {
        showLiebiaoFragment(tuijian_2);
    }

    @OnClick(R.id.text_tuijian_3)
    public void onMTextTuijian3Clicked() {
        showLiebiaoFragment(tuijian_3);
    }

    @OnClick(R.id.text_tuijian_4)
    public void onMTextTuijian4Clicked() {
        showLiebiaoFragment(tuijian_4);
    }

    @OnClick(R.id.text_tuijian_5)
    public void onMTextTuijian5Clicked() {
        showLiebiaoFragment(tuijian_5);
    }

}
