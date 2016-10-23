package com.xiaoguang.happytoplay.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.xiaoguang.happytoplay.adapter.HomeFragPagerAdapter;
import com.xiaoguang.happytoplay.contract.IHomeContract;
import com.xiaoguang.happytoplay.fragment.HomeFragment;
import com.xiaoguang.happytoplay.fragment.MessageFragment;
import com.xiaoguang.happytoplay.fragment.MoreFragment;
import com.xiaoguang.happytoplay.fragment.PersonFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11655 on 2016/9/28.
 */

public class HomePresenterImpl implements IHomeContract.IHomePresenter {
    private IHomeContract.IHomeView view;
    //声明一个viewPager对象
    private ViewPager viewPager;
    //声明一个集合用于存放Fragment
    private List<Fragment> fragments;

    public HomePresenterImpl(IHomeContract.IHomeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadingData(Object o,FragmentManager fm) {
        //获取viewPager对象
        viewPager  = (ViewPager) o;
        //进行相关数据的初始化操作
        start();
        //绑定适配器
        viewPager.setAdapter(new HomeFragPagerAdapter(fm,fragments));

        //为vp添加点击事件
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//            //当vp滑动后改变其颜色
//            @Override
//            public void onPageSelected(int position) {
//                view.changBtnSelectedStatus(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        //2016.10.06修改，
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //当vp滑动后改变其颜色
            @Override
            public void onPageSelected(int position) {
                view.changBtnSelectedStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void start() {
        //实例化集合
        fragments = new ArrayList<Fragment>();
        /*集合中添加Fragment*/
        fragments.add(new HomeFragment());
        fragments.add(new MessageFragment());
//        fragments.add(new AddFragment());
        fragments.add(new PersonFragment());
        fragments.add(new MoreFragment());

    }
}
