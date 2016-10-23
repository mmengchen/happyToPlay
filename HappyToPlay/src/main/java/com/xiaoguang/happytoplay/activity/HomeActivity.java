package com.xiaoguang.happytoplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.xiaoguang.happytoplay.*;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.contract.IHomeContract;
import com.xiaoguang.happytoplay.presenter.HomePresenterImpl;
import com.xiaoguang.happytoplay.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/9/27.
 */

public class HomeActivity extends BaseActivity implements IHomeContract.IHomeView {

    //获取控件
    @BindView(R.id.act_home_vp)
    ViewPager mVp;
    @BindView(R.id.act_home_btn_home)
    Button mBtnHome;
    @BindView(R.id.act_home_btn_msg)
    Button mBtnMsg;
    @BindView(R.id.act_home_btn_add)
    Button mBtnAdd;
    //自定义按钮
    @BindView(R.id.act_home_btn_person)
    Button mBtnPerson;
    @BindView(R.id.act_home_btn_more)
    Button mBtnMore;
    private IHomeContract.IHomePresenter presenter;
    /**
     * 定义一个button数组，用于改变颜色
     *
     */
    private Button btns[];
    private int btnID [] ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        ButterKnife.bind(this);
        new HomePresenterImpl(this);
        //初始化ID
        btnID = new int []{R.id.act_home_btn_home,R.id.act_home_btn_msg,R.id.act_home_btn_person,R.id.act_home_btn_more};
        btns  = new Button[]{mBtnHome,mBtnMsg,mBtnPerson,mBtnMore};
        //默认第一个按钮被选中
        mBtnHome.setEnabled(false);
        getData();
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
    public void setPresenter(IHomeContract.IHomePresenter presenter) {
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
    public void setData(Object object) {
    }

    @Override
    public void getData() {
        //为ViewPager设置数据
        presenter.loadingData(mVp, getSupportFragmentManager());
        LogUtils.i("我正在请求加载vp布局");
    }

    @OnClick({R.id.act_home_btn_home, R.id.act_home_btn_msg, R.id.act_home_btn_add, R.id.act_home_btn_person, R.id.act_home_btn_more})
    public void onClick(View view) {
        LogUtils.i("我被点击了呢");
        switch (view.getId()) {
            case R.id.act_home_btn_home:
                changBtnSelectedStatus(0);
                //设置当前ViewPager的当前状态
                mVp.setCurrentItem(0);
                break;
            case R.id.act_home_btn_msg:
                //改变当前选中的状态，改变颜色
                changBtnSelectedStatus(1);
                //设置当前ViewPager的当前状态
                mVp.setCurrentItem(1);
                break;
            case R.id.act_home_btn_add:
                LogUtils.i("我正在打开发布信息的activity");
                startActivity(new Intent(this,PublishActivity.class));
                break;
            case R.id.act_home_btn_person:
                changBtnSelectedStatus(2);
                //设置当前ViewPager的当前状态
                mVp.setCurrentItem(2);
                break;
            case R.id.act_home_btn_more:
                changBtnSelectedStatus(3);
                //设置当前ViewPager的当前状态
                mVp.setCurrentItem(3);
                break;
        }
    }
    @Override
    public void changBtnSelectedStatus(int position){
        for (int i =0 ;i<4;i++){
            if (i==position){
                //将选中的设置背景颜色为绿色
                btns[i].setEnabled(false);
            }else {
                //将为选中的设置背景颜色为默认
                btns[i].setEnabled(true);
            }
        }
    }
}
