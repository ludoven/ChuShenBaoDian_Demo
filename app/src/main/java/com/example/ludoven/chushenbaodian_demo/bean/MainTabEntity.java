package com.example.ludoven.chushenbaodian_demo.bean;


import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by 5499 on 2018/10/17.
 */

public class MainTabEntity implements CustomTabEntity {
    String title;
    int selectedIcon;
    int unSelectIcon;


    public MainTabEntity(String title, int selectedIcon, int unSelectIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectIcon = unSelectIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectIcon;
    }
}
