package com.xiaoguang.happytoplay.fragment;

import android.content.Intent;
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
import com.xiaoguang.happytoplay.activity.ShowResultActivity;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.base.BaseFragment;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.contract.IFragMoreContract;
import com.xiaoguang.happytoplay.presenter.FragMorePresenterImpl;
import com.xiaoguang.happytoplay.utils.ToastUtils;

import java.util.List;

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
        ToastUtils.toastShort(msg);
    }

    @Override
    public void showLoading() {
        super.showProcessDialog("查询数据中","数据查询中，请稍等",false);
    }

    @Override
    public void hiddenLoading() {
        super.dismissProcessDialog();
    }

    @Override
    public void setPresenter(IFragMoreContract.IFragMorePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public void jumpActivity(List<Grather> datas){
        //将数据显示到内存中
        MyApplitation.putDatas("queryDatas",datas);
        //跳转到信息显示界面
        Intent intent = new Intent(getContext(), ShowResultActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean back() {
        return false;
    }

    /**
     * 查询界面
     * @param view
     */
    @OnClick({R.id.frag_more_iv_search, R.id.frag_more_iv_top, R.id.frag_more_btn_free, R.id.frag_more_btn_hot, R.id.frag_more_btn_near, R.id.frag_more_ln_zhoubian, R.id.frag_more_ln_shaoer, R.id.frag_more_ln_diy, R.id.frag_more_ln_jianshen, R.id.frag_more_ln_jishi, R.id.frag_more_ln_yanchu, R.id.frag_more_ln_zhanlan, R.id.frag_more_ln_shalong, R.id.frag_more_ln_pincha, R.id.frag_more_ln_jihui, R.id.frag_more_iv_ad})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.frag_more_iv_search:
                //获取文本数据
                String datas = mFragMoreEdSearch.getText().toString();
                //查询数据
                presenter.query(datas,0);
                break;
            case R.id.frag_more_iv_top://头部广告
                break;
            case R.id.frag_more_btn_free:
                presenter.query("免费",1);
                break;
            case R.id.frag_more_btn_hot:
                presenter.query("热门",2);
                break;
            case R.id.frag_more_btn_near:
                presenter.query("附近",3);
                break;
            case R.id.frag_more_ln_zhoubian:
                presenter.query("周边",4);
                break;
            case R.id.frag_more_ln_shaoer:
                presenter.query("少儿",4);
                break;
            case R.id.frag_more_ln_diy:
                presenter.query("DIY",4);
                break;
            case R.id.frag_more_ln_jianshen:
                presenter.query("健身",4);
                break;
            case R.id.frag_more_ln_jishi:
                presenter.query("集市",4);
                break;
            case R.id.frag_more_ln_yanchu:
                presenter.query("演出",4);
                break;
            case R.id.frag_more_ln_zhanlan:
                presenter.query("展览",4);
                break;
            case R.id.frag_more_ln_shalong:
                presenter.query("沙龙",4);
                break;
            case R.id.frag_more_ln_pincha:
                presenter.query("品茶",4);
                break;
            case R.id.frag_more_ln_jihui:
                presenter.query("聚会",4);
                break;
            case R.id.frag_more_iv_ad://广告
                break;
        }
    }
}
