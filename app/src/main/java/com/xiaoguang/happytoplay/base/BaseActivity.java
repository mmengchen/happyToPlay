package com.xiaoguang.happytoplay.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

/**
 * 所有Activity的基类 ,此类继承FragmentActivity
 * Created by 11655 on 2016/9/25.
 */

public class BaseActivity extends FragmentActivity {

    //声明一个构建着对象，用于创建警告对话框
    private AlertDialog.Builder builder;
    //用于创建一个进度条对话框
    private ProgressDialog dialog;

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
     * 功能：显示一个警告对话框,无按钮，需要自己设置
     * @param title  标题
     * @param msg 内容
     * @param flag 是否可以取消
     * @return AlertDialog.Builder 对象
     */
    protected AlertDialog.Builder showAlertDialog(String title,String msg,boolean flag){
        if (builder==null){
        //创建一个构建者对象
        builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(msg).setCancelable(flag);
        }
//        alertDialog = builder.show();
        return builder;
    }

    /**
     * 功能:取消警告对话框
     */
    protected void dismissAlertDialog(AlertDialog alertDialog){
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
