package com.example.ludoven.chushenbaodian_demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.adapter.ShoucangAdapter;
import com.example.ludoven.chushenbaodian_demo.bean.DataBean;
import com.example.ludoven.chushenbaodian_demo.bean.ListBean;
import com.example.ludoven.chushenbaodian_demo.util.HttpUtil;

import java.util.List;

public class ShoucangFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private  ShoucangAdapter shoucangAdapter;
   // private  List<DataBean> dataBeanList;
    private List<ListBean> listBeanList;
    private ImageView mImageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shoucang, container, false);
        mRecyclerView=view.findViewById(R.id.recyclerview_shoucang);
        mImageView=view.findViewById(R.id.shoucang_back);
      //  dataBeanList=DataBean.where("clock=?","0").find(DataBean.class);
        listBeanList=ListBean.findAll(ListBean.class);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        shoucangAdapter=new ShoucangAdapter(listBeanList);
        mRecyclerView.setAdapter(shoucangAdapter);
         shoucangAdapter.setItemClickListener(new ShoucangAdapter.MyItemClickListener() {
             @Override
             public void onItemClick(View view, int position) {
                 //在这个碎片中 创建 详情页 碎片的实例
                 XiangQingFragment fragment = new XiangQingFragment(1);
                 Bundle bundle=new Bundle();
                 ListBean dataBean=listBeanList.get(position);
                 bundle.putSerializable("list",dataBean);
                 bundle.putString("id",dataBean.getMenuId());
                 fragment.setArguments(bundle);
                 showFragment(fragment);
             }
         });
         mImageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 HttpUtil.getBack();
             }
         });
        return view;
    }
    private void showFragment(Fragment fragment){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
