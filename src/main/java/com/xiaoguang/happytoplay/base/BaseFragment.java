package com.xiaoguang.happytoplay.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 所有fagment基类
 * Created by 11655 on 2016/9/28.
 */

public abstract class BaseFragment extends Fragment {
    //用于创建一个进度条对话框
    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    //声明一个构建着对象，用于创建警告对话框
    private AlertDialog.Builder builder;
    //创建View对象，用于Fragment的优化
    private View contentView = null;
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null){
            contentView = initLayout(inflater);
        }
        return contentView;
    }

    /**
     * 初始化Fragment布局
     * @param inflater
     * @return
     */
    protected abstract View initLayout(LayoutInflater inflater);
    /**
     *  功能 ：显示一个警告对话框
     */
    protected void showAlertDialog(String title,String text){
        if (builder==null){
            //创建一个构建者对象
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle(title).setMessage(text).setCancelable(false);
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
        }
        alertDialog = builder.show();
    }

    /**
     * 功能:取消警告对话框
     */
    protected void dismissAlertDialog(){
        if (alertDialog!=null){
            //取消警告对话框
            alertDialog.dismiss();
        }
    }
    /**
     * 功能 ：显示一个进度条对话框
     */
    protected void showProcessDialog(String title,String msg,boolean falg){
        if(dialog==null){
            dialog = new ProgressDialog(getContext());
        }
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(falg);
        dialog.show();
    }

    /**
     * 功能 ：取消一个进度条对话框
     */
    protected void dismissProcessDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }
}
