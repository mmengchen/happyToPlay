package com.xiaoguang.happytoplay.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

/**
 * 所有Activity的基类 ,此类继承FragmentActivity
 * Created by 11655 on 2016/9/25.
 */

public class BaseActivity extends FragmentActivity {

    private static final String TITLE = "蜂窝移动数据已关闭";
    private static final String TEXT = "打开蜂窝移动数据或使用无线局域网来访问数据";
    //声明一个构建着对象，用于创建警告对话框
    private AlertDialog.Builder builder;
    //用于创建一个进度条对话框
    private ProgressDialog dialog;
    private AlertDialog alertDialog;

    /**
     *  功能：实现沉浸式通知栏，使通知栏和APP的标题颜色一样
     */
    protected void immersiveNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    /**
     *  功能 ：显示一个警告对话框
     */
    protected void showAlertDialog(){
        if (builder==null){
        //创建一个构建者对象
        builder = new AlertDialog.Builder(this);
        builder.setTitle(TITLE).setMessage(TEXT).setCancelable(false);
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
            dialog = new ProgressDialog(this);
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
