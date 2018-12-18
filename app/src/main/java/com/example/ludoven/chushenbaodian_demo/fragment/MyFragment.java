package com.example.ludoven.chushenbaodian_demo.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.WebviewActivity;
import com.example.ludoven.chushenbaodian_demo.activity.LoginActivity;
import com.example.ludoven.chushenbaodian_demo.bean.User;
import com.leon.lib.settingview.LSettingItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    @BindView(R.id.item_shoucang)
    LSettingItem mLSettingItem;
    @BindView(R.id.item_webview)
    LSettingItem mLSettingItem_web;
    @BindView(R.id.item_myInfo)
    LSettingItem mLSettingItem_info;
    @BindView(R.id.my_image)
    CircleImageView mImageView;
    @BindView(R.id.my_name)
    TextView mTextView;
    @BindView(R.id.item_kefu)LSettingItem mLSettingItem_kefu;
    private Unbinder mUnbinder;
    private String phone;
    private boolean check=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        SharedPreferences pref = getContext().getSharedPreferences("data", MODE_PRIVATE);
        phone = pref.getString("phone", "");
        check=pref.getBoolean("appCheck",false);
        if (check){
            User user=User.where("phone=?",phone+"").find(User.class).get(0);
            mImageView.setImageResource(R.mipmap.icon_chushen);
            mTextView.setText(user.getUsername());
        }

        mLSettingItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                showFragment(new ShoucangFragment());
            }
        });
        mLSettingItem_kefu.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                showFragment(new ZixunFragment());
            }
        });
        mLSettingItem_info.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                showFragment(new MyInfoFragment());
            }
        });
        mLSettingItem_web.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                intent.putExtra("WEB_URL", "http://m.xiachufang.com/");
                getContext().startActivity(intent);
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check){
                    Intent intent=new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check){
                    Intent intent=new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
