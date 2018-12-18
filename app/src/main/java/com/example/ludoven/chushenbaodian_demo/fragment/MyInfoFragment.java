package com.example.ludoven.chushenbaodian_demo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.util.HttpUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {
    private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_info, container, false);
        mImageView=view.findViewById(R.id.myinfo_back);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.getBack();
            }
        });
        return view;
    }

}
