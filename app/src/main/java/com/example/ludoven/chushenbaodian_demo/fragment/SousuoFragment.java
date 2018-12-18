package com.example.ludoven.chushenbaodian_demo.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.adapter.CaipuAdapter;
import com.example.ludoven.chushenbaodian_demo.bean.DataBean;
import com.example.ludoven.chushenbaodian_demo.bean.ResponseBean;
import com.example.ludoven.chushenbaodian_demo.bean.StepsBean;
import com.example.ludoven.chushenbaodian_demo.util.HttpUtil;
import com.google.gson.Gson;
import com.maning.mndialoglibrary.MProgressDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchListView;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;


public class SousuoFragment extends Fragment  {
   private SearchView mSearchView;
   private RecyclerView mRecyclerView;
   private SearchListView mListView;
    private  CaipuAdapter mCaipuAdapter;
    private EditText et_search;
    private String  responseData;
    private ScrollView mScrollView;
    private TextView mTextView;
    private FloatingActionButton mButton;
    private List<DataBean> mDataBeanList=new ArrayList<>();
    private int pn=0;
    String name;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sousuo, container, false);
        mSearchView=view.findViewById(R.id.searchview_sousuo);
        mListView=view.findViewById(R.id.listView);
        et_search=view.findViewById(R.id.et_search);
        mScrollView=view.findViewById(R.id.scroll_sousuo);
        mRecyclerView=view.findViewById(R.id.recyclerview_sousuo);
        mTextView=view.findViewById(R.id.text_sousuo_bottom);
        mButton=view.findViewById(R.id.fab_sousuo);

        mTextView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              getData(name);
              mCaipuAdapter.notifyDataSetChanged();
          }
      });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 获取用户点击列表里的文字,并自动填充到搜索框内
               TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String string = textView.getText().toString();
                et_search.setText(string);
                name=string;
                MProgressDialog.showProgress(getContext(),"正在努力搜索哦");
                getData(string);
            }
        });
        mSearchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                name=string;
                MProgressDialog.showProgress(getContext(),"正在努力搜索哦");
                getData(string);
            }
        });

        mSearchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                /**
                 * 执行返回键的功能（返回上一级）
                 */

           HttpUtil.getBack();
            }
        });



        return view;
    }
    private void getData(String menu){
        String adress="http://apis.juhe.cn/cook/query?key="+HttpUtil.key+"&menu="+menu+"&rn=30&pn="+pn+"" ;
        HttpUtil.sendOkhttpRequest(adress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                  responseData=response.body().string();
                ResponseBean responseBean=gson.fromJson(responseData,ResponseBean.class);
               pn=pn+30;
            if (responseBean.getError_code()==0){
                ResponseBean.ResultBean resultBean=responseBean.getResult();
                final List<DataBean> dataBeanList=  resultBean.getData();
                if (dataBeanList.size()<=0){
                    mTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText("已经到底了哦");
                            mTextView.setEnabled(false);
                          //  Toast.makeText(getContext(),"没有更多菜谱了哦。",Toast.LENGTH_SHORT).show();
                        }
                    });
                      return;
                }
                for (DataBean dataBean:dataBeanList){
                    mDataBeanList.add(dataBean);
                }
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager manager=new LinearLayoutManager(getActivity()){
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        };
                        mRecyclerView.setLayoutManager(manager);
                        mCaipuAdapter =new CaipuAdapter(mDataBeanList,R.layout.item_tuijian);
                        mRecyclerView.setAdapter(mCaipuAdapter);

                        mTextView.setVisibility(View.VISIBLE);
                        mTextView.setText("点击加载更多");
                        mTextView.setEnabled(true);
                        mCaipuAdapter.setItemClickListener(new CaipuAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //在这个碎片中 创建 详情页 碎片的实例
                                XiangQingFragment fragment=new XiangQingFragment();
                                Bundle bundle=new Bundle();
                                DataBean dataBean=mDataBeanList.get(position);
                                bundle.putSerializable("databean",dataBean);
                                bundle.putInt("position",position);
                                fragment.setArguments(bundle);
                               showFragment(fragment);
                            }
                        });
                    }
                });
            }else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MProgressDialog.dismissProgress();
                        Toast.makeText(getContext(),"换个关键词试试",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            }
        });

    }



    private void showFragment(Fragment fragment){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


}
