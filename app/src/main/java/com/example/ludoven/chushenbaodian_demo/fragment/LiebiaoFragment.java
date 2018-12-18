package com.example.ludoven.chushenbaodian_demo.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.azhon.suspensionfab.FabAttributes;
import com.azhon.suspensionfab.OnFabClickListener;
import com.azhon.suspensionfab.SuspensionFab;
import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.adapter.CaipuAdapter;
import com.example.ludoven.chushenbaodian_demo.bean.CaipuData;
import com.example.ludoven.chushenbaodian_demo.bean.DataBean;
import com.example.ludoven.chushenbaodian_demo.bean.ListBean;
import com.example.ludoven.chushenbaodian_demo.bean.RecipeBean;
import com.example.ludoven.chushenbaodian_demo.bean.ResponseBean;
import com.example.ludoven.chushenbaodian_demo.util.FabAlphaAnimate;
import com.example.ludoven.chushenbaodian_demo.util.HttpUtil;
import com.example.ludoven.chushenbaodian_demo.util.RecycleViewDivider;
import com.google.gson.Gson;
import com.maning.mndialoglibrary.MProgressDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LiebiaoFragment extends Fragment implements OnFabClickListener {
    private RecyclerView mRecyclerView;
    private CaipuAdapter mCaipuAdapter;
    private String menu_text,responseData;
    private RefreshLayout mRefreshLayout;
    private ImageView mImageView;
    private int pn=0;
    private int page=1;
    private List<DataBean> dataBeanList,mDataBeanList;
    private FabAttributes shuaxin,top;
    public LiebiaoFragment(){};
    @SuppressLint("ValidFragment")
    public LiebiaoFragment(String menu){
        this.menu_text=menu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        MProgressDialog.showProgress(getContext(),"加载中");
        mRecyclerView=view.findViewById(R.id.recyclerview_list);
        mRefreshLayout=view.findViewById(R.id.refresh_liebiao);
        mImageView=view.findViewById(R.id.image_back_liebiao);

        getData(menu_text);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData(menu_text);
                mCaipuAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh(2000);
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HttpUtil.isFastClick()){
                    HttpUtil.getBack();
                }
                /**
                 * 执行返回键的功能（返回上一级）
                 */
            }
        });
        SuspensionFab fabTop = (SuspensionFab)view.findViewById(R.id.fab_liebiao);
//构建展开按钮属性
        shuaxin = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#FFFFFF"))
                .setSrc(getResources().getDrawable(R.drawable.ic_shuaxin))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();
        top = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#FFFFFF"))
                .setSrc(getResources().getDrawable(R.drawable.ic_top))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();

//添加菜单
        fabTop.addFab(shuaxin,top);
        fabTop.setAnimationManager(new FabAlphaAnimate(fabTop));
//设置菜单点击事件
        fabTop.setFabClickListener(this);
        return view;
    }
    private void getData(String menu){
        String adress="http://apicloud.mob.com/v1/cook/menu/search?key=2884a62c9ba96&name="+menu+"&page="+page+"&size=30";
        HttpUtil.sendOkhttpRequest(adress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                String responseData=response.body().string();
                CaipuData caipuData=gson.fromJson(responseData,CaipuData.class);
                CaipuData.ResultBean resultBean=caipuData.getResult();
                final List<ListBean> listBeanList=resultBean.getList();

                if (caipuData.getRetCode().equals("200")){
                    page=page+1;
                    final List<String> nameList=new ArrayList<>();
                    for (ListBean listBean:listBeanList){
                        nameList.add(listBean.getName());
                    }
                    final List<RecipeBean> recipeBeanList=new ArrayList<>();
                    for (ListBean listBean:listBeanList){
                        RecipeBean recipeBean=listBean.getRecipe();
                        recipeBeanList.add(recipeBean);
                    }


                if(listBeanList.size()<=0){
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"没有更多菜谱了哦。",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                GridLayoutManager manager=new GridLayoutManager(getContext(),2);
                                mRecyclerView.setLayoutManager(manager);
                                mCaipuAdapter=new CaipuAdapter(nameList,recipeBeanList,R.layout.item_liebiao);
                                mRecyclerView.setAdapter(mCaipuAdapter);
                                mCaipuAdapter.setItemClickListener(new CaipuAdapter.MyItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        //在这个碎片中 创建 详情页 碎片的实例
                                        XiangQingFragment fragment=new XiangQingFragment();
                                        Bundle bundle=new Bundle();
                                        ListBean listBean=listBeanList.get(position);
                                        bundle.putSerializable("list",listBean);
                                        fragment.setArguments(bundle);
                                        showFragment(fragment);
                                    }
                                });

                            }
                        });
                    }

                }
                else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MProgressDialog.dismissProgress();
                                Toast.makeText(getContext(),"对不起^_^，该分类正在维护。",Toast.LENGTH_SHORT).show();
                                /**
                                 * 执行返回键的功能（返回上一级）
                                 */
                             HttpUtil.getBack();
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
      fragmentTransaction.commit();
  }

    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
         if (tag.equals(1)){
                if (HttpUtil.isFastClick()){
                    getData(menu_text);
                    mCaipuAdapter.notifyDataSetChanged();
                }


         }else if (tag.equals(2)){
             mRecyclerView.scrollToPosition(0);
         }
    }
}
