package com.xiaoguang.happytoplay.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseFragment;
import com.xiaoguang.happytoplay.contract.IFragMsgContract;
import com.xiaoguang.happytoplay.presenter.FragMsgPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/9/28.
 */

public class MessageFragment extends BaseFragment implements IFragMsgContract.IFragMsgView {

    //使用绑定的方式获取控件对象
    @BindView(R.id.frag_message_btn_friend)
    Button mFragMessageBtnFriend;
    @BindView(R.id.frag_message_btn_grahter)
    Button mFragMessageBtnGrahter;
    @BindView(R.id.frag_message_rl_header)
    RelativeLayout mRlHeader;
    @BindView(R.id.frag_message_view_pager)
    ViewPager mFragMessageViewPager;
    private IFragMsgContract.IFragMsgPresenter presenter;
    public static final int MESSAGE_FRAGMENT_TYPE = 1;
    public static final int CALL_FRAGMENT_TYPE = 2;
    public int currentFragmentType = -1;
    private ActiveFragment gratherFragment;
    private MessageFragment messageFragment;
    private FragmentPagerAdapter mAdapter;
    //设置子Fragment的数据源
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取子FragmentManager对象
        new FragMsgPresenterImpl(this);
    }
    @Override
    protected View initLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.frag_message, null);
        ButterKnife.bind(this, view);
        initDatas();
        //实例化适配器对象，并且重写方法
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
        };
        //设置viewPager为不可滑动状态
        mFragMessageViewPager.setOnTouchListener( new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;  //修改为true
            }

        });
        //设置适配器
        mFragMessageViewPager.setAdapter(mAdapter);
        //为其添加监听事件
        mFragMessageViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //当前子Fragment的索引
            private int currentIndex;

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return view;
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new FriendFragment());
        mFragments.add(new ActiveFragment());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hiddenLoading() {

    }

    @Override
    public void setPresenter(IFragMsgContract.IFragMsgPresenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public void setData(Object... object) {

    }

    @Override
    public void getData() {

    }

    @OnClick({R.id.frag_message_btn_friend, R.id.frag_message_btn_grahter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_message_btn_friend:
                changeButtonState(0);
                mFragMessageViewPager.setCurrentItem(0);
                break;
            case R.id.frag_message_btn_grahter:
                mFragMessageViewPager.setCurrentItem(1);
                changeButtonState(1);
                break;
        }
    }

    /**
     * 改变选中按钮的状态
     */
    private void changeButtonState(int type) {
        if (type == 0) {
            mFragMessageBtnFriend.setTextColor(Color.parseColor("#bb00cc74"));
            mFragMessageBtnGrahter.setTextColor(Color.WHITE);
            mFragMessageBtnFriend
                    .setBackgroundResource(R.drawable.baike_btn_pink_left_f_96);
            mFragMessageBtnGrahter
                    .setBackgroundResource(R.drawable.baike_btn_trans_right_f_96);
        } else if (type == 1) {
            mFragMessageBtnFriend.setTextColor(Color.WHITE);
            mFragMessageBtnGrahter.setTextColor(Color.parseColor("#bb00cc74"));
            mFragMessageBtnFriend
                    .setBackgroundResource(R.drawable.baike_btn_trans_left_f_96);
            mFragMessageBtnGrahter
                    .setBackgroundResource(R.drawable.baike_btn_pink_right_f_96);
        }
    }
}
