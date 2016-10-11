package com.xiaoguang.happytoplay.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.contract.IWelContract;
import com.xiaoguang.happytoplay.presenter.WelPresenterImpl;
import com.xiaoguang.happytoplay.utils.ToastUtils;

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
    private AlertDialog alertDialog;
    private static final String TITLE = "蜂窝移动数据已关闭";
    private static final String TEXT = "打开蜂窝移动数据或使用无线局域网来访问数据";

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
        //获取一个builder对象
        AlertDialog.Builder builder = super.showAlertDialog(TITLE, TEXT, false);
        //设置按钮
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //跳转到系统网络设置
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //退出虚拟机
                System.exit(0);
            }
        });
        //显示对话框
        alertDialog = builder.show();
    }

    /**
     * 跳转到主页面
     */
    @Override
    public void jumpHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void setPresenter(IWelContract.IWelPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showLoading() {
        super.showProcessDialog("努力加载网络中", "努力加载网络中", false);
    }

    @Override
    public void hiddenLoading() {
        dismissAlertDialog(alertDialog);
        dismissProcessDialog();
    }

    @Override
    public void showMsg(String string) {
        ToastUtils.toastShort(string);
    }
}
