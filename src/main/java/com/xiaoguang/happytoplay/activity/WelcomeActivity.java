package com.xiaoguang.happytoplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.contract.IWelContract;
import com.xiaoguang.happytoplay.presenter.WelPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 欢迎动画的界面
 * Created by 11655 on 2016/9/27.
 */

public class WelcomeActivity extends BaseActivity implements IWelContract.IWelView {

    //使用注解获取控件
    @BindView(R.id.act_wel_iv)
    ImageView mImageView;
    private IWelContract.IWelPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);
        ButterKnife.bind(this);
        new WelPresenterImpl(this);
        //开启动画
        presenter.startAnimation(mImageView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //获取网络状态
        presenter.isNetWorkState();
    }

    @Override
    public void showDialog() {
        super.showAlertDialog();
    }

    /**
     * 跳转到主页面
     */
    @Override
    public void jumpHome() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    @Override
    public void setPresenter(IWelContract.IWelPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    public void showLoading() {
        super.showProcessDialog("努力加载网络中","努力加载网络中",false);
    }

    @Override
    public void hiddenLoading() {
        dismissAlertDialog();
        dismissProcessDialog();
    }

    @Override
    public void showMsg(String string) {
        Toast.makeText(this,""+ string,Toast.LENGTH_SHORT).show();
    }
}
