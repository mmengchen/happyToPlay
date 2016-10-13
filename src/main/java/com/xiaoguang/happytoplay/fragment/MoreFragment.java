package com.xiaoguang.happytoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseFragment;
import com.xiaoguang.happytoplay.contract.IFragMoreContract;
import com.xiaoguang.happytoplay.presenter.FragMorePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/9/28.
 */

public class MoreFragment extends BaseFragment implements IFragMoreContract.IFragMoreView {

    @BindView(R.id.frag_more_sp)
    Spinner mFragMoreSp;
    @BindView(R.id.frag_more_ed_search)
    EditText mFragMoreEdSearch;
    @BindView(R.id.frag_more_iv_search)
    ImageView mFragMoreIvSearch;
    @BindView(R.id.frag_more_iv_top)
    ImageView mFragMoreIvTop;
    @BindView(R.id.frag_more_btn_free)
    Button mFragMoreBtnFree;
    @BindView(R.id.frag_more_btn_hot)
    Button mFragMoreBtnHot;
    @BindView(R.id.frag_more_btn_near)
    Button mFragMoreBtnNear;
    @BindView(R.id.frag_more_ln_zhoubian)
    LinearLayout mFragMoreLnZhoubian;
    @BindView(R.id.frag_more_ln_shaoer)
    LinearLayout mFragMoreLnShaoer;
    @BindView(R.id.frag_more_ln_diy)
    LinearLayout mFragMoreLnDiy;
    @BindView(R.id.frag_more_ln_jianshen)
    LinearLayout mFragMoreLnJianshen;
    @BindView(R.id.frag_more_ln_jishi)
    LinearLayout mFragMoreLnJishi;
    @BindView(R.id.frag_more_ln_yanchu)
    LinearLayout mFragMoreLnYanchu;
    @BindView(R.id.frag_more_ln_zhanlan)
    LinearLayout mFragMoreLnZhanlan;
    @BindView(R.id.frag_more_ln_shalong)
    LinearLayout mFragMoreLnShalong;
    @BindView(R.id.frag_more_ln_pincha)
    LinearLayout mFragMoreLnPincha;
    @BindView(R.id.frag_more_ln_jihui)
    LinearLayout mFragMoreLnJihui;
    @BindView(R.id.frag_more_iv_ad)
    ImageView mFragMoreIvAd;
    private IFragMoreContract.IFragMorePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected View initLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.frag_more, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new FragMorePresenterImpl(this);
        //为Spinner设置适配器
        presenter.loadingData(mFragMoreSp);
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
    public void setPresenter(IFragMoreContract.IFragMorePresenter presenter) {
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

    @OnClick({R.id.frag_more_iv_search, R.id.frag_more_iv_top, R.id.frag_more_btn_free, R.id.frag_more_btn_hot, R.id.frag_more_btn_near, R.id.frag_more_ln_zhoubian, R.id.frag_more_ln_shaoer, R.id.frag_more_ln_diy, R.id.frag_more_ln_jianshen, R.id.frag_more_ln_jishi, R.id.frag_more_ln_yanchu, R.id.frag_more_ln_zhanlan, R.id.frag_more_ln_shalong, R.id.frag_more_ln_pincha, R.id.frag_more_ln_jihui, R.id.frag_more_iv_ad})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_more_iv_search:
                break;
            case R.id.frag_more_iv_top:
                break;
            case R.id.frag_more_btn_free:
                break;
            case R.id.frag_more_btn_hot:
                break;
            case R.id.frag_more_btn_near:
                break;
            case R.id.frag_more_ln_zhoubian:
                break;
            case R.id.frag_more_ln_shaoer:
                break;
            case R.id.frag_more_ln_diy:
                break;
            case R.id.frag_more_ln_jianshen:
                break;
            case R.id.frag_more_ln_jishi:
                break;
            case R.id.frag_more_ln_yanchu:
                break;
            case R.id.frag_more_ln_zhanlan:
                break;
            case R.id.frag_more_ln_shalong:
                break;
            case R.id.frag_more_ln_pincha:
                break;
            case R.id.frag_more_ln_jihui:
                break;
            case R.id.frag_more_iv_ad:
                break;
        }
    }
}
