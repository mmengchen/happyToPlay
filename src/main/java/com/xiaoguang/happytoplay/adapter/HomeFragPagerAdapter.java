package com.xiaoguang.happytoplay.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 主页Fragment的适配器
 * Created by 11655 on 2016/9/28.
 */

public class HomeFragPagerAdapter extends FragmentPagerAdapter {
    //定一个Fragment数据源
    private List<Fragment> fragments;
    public HomeFragPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
