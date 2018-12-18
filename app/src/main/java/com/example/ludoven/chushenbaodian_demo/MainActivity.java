package com.example.ludoven.chushenbaodian_demo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ludoven.chushenbaodian_demo.bean.DataBean;
import com.example.ludoven.chushenbaodian_demo.bean.ListBean;
import com.example.ludoven.chushenbaodian_demo.fragment.FenleiFragment;
import com.example.ludoven.chushenbaodian_demo.fragment.MyFragment;
import com.example.ludoven.chushenbaodian_demo.fragment.ShouyeFragment;
import com.example.ludoven.chushenbaodian_demo.fragment.StoryFragment;
import com.example.ludoven.chushenbaodian_demo.fragment.XiangQingFragment;
import com.example.ludoven.chushenbaodian_demo.fragment.ZixunFragment;
import com.example.ludoven.chushenbaodian_demo.bean.MainTabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.litepal.LitePal;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener {
    private  int id;
    private FragmentManager mFragmentManager;
    private Fragment fragment_shouye,fragment_fenlei,fragment_zixun,fragment_wode;
    private FragmentTransaction fragmentTransaction;
    private ListBean listBean;
    ArrayList<CustomTabEntity> tabs = new ArrayList<>();
    String [] titles={"首页","分类","菜谱故事","我的"};
    int []  icons_f={R.drawable.ic_icon__shouye,R.drawable.ic_icon__fenlei,R.drawable.ic_icon_zixun,R.drawable.ic_icon_geren};
    int []  icons={R.drawable.ic_icon_shouye_f,R.drawable.ic_icon_fenlei_f,R.drawable.ic_icon_zixun_f,R.drawable.ic_icon_geren_f};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
       Bundle bundle=intent.getExtras();
       if (bundle!=null){
           id=bundle.getInt("id");
           listBean= (ListBean) bundle.getSerializable("databean");

       }
        if (id==1){
                FragmentManager fragmentManager1 =getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.replace(R.id.activity_main, new XiangQingFragment(listBean,2));
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();
        }
        //创建数据库
        LitePal.getDatabase();
        mFragmentManager=getSupportFragmentManager();
        fragmentTransaction=mFragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        //默认为首页
        if (fragment_shouye==null){
            fragment_shouye=new ShouyeFragment();
            fragmentTransaction.add(R.id.frame_layout,fragment_shouye);
        }else {
            fragmentTransaction.show(fragment_shouye);
        }
        fragmentTransaction.commit();
        //init();

        for (int i=0;i<4;i++){
            MainTabEntity mainTabEntity = new MainTabEntity(titles[i],icons[i],icons_f[i]);
            tabs.add(mainTabEntity);
        }

        CommonTabLayout commonTabLayout = findViewById(R.id.tab_layout);
        commonTabLayout.setTabData(tabs);
        commonTabLayout.setOnTabSelectListener(this);


        int backStackCount = getFragmentManager().getBackStackEntryCount();
        for(int i = 0; i < backStackCount; i++) {
            getFragmentManager().popBackStack();
        }
    }
    @Override
    public void onTabSelect(int position) {
        FragmentTransaction fTransaction1 = mFragmentManager.beginTransaction();
        hideAllFragment(fTransaction1);
        switch (position){
            case 0:
                if (fragment_shouye==null){
                    fragment_shouye=new ShouyeFragment();
                    fTransaction1.add(R.id.frame_layout,fragment_shouye);
                }else {
                    fTransaction1.show(fragment_shouye);
                }
                break;
            case 1:
                if (fragment_fenlei==null){
                    fragment_fenlei=new FenleiFragment();
                    fTransaction1.add(R.id.frame_layout,fragment_fenlei);
                }else {
                    fTransaction1.show(fragment_fenlei);
                }
                break;
            case 2:
                if (fragment_zixun==null){
                    fragment_zixun=new StoryFragment();
                    fTransaction1.add(R.id.frame_layout,fragment_zixun);
                }else {
                    fTransaction1.show(fragment_zixun);
                }
                break;
            case 3:
                if (fragment_wode==null){
                    fragment_wode=new MyFragment();
                    fTransaction1.add(R.id.frame_layout,fragment_wode);
                }else {
                    fTransaction1.show(fragment_wode);
                }
                break;

        }
        fTransaction1.commit();
    }

    @Override
    public void onTabReselect(int position) {

    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fragment_shouye != null)fragmentTransaction.hide(fragment_shouye);
        if(fragment_fenlei != null)fragmentTransaction.hide(fragment_fenlei);
        if(fragment_zixun != null)fragmentTransaction.hide(fragment_zixun);
        if(fragment_wode != null)fragmentTransaction.hide(fragment_wode);
    }


}
