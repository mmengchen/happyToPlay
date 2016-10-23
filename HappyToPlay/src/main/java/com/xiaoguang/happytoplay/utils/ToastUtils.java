package com.xiaoguang.happytoplay.utils;


import android.widget.Toast;

import com.xiaoguang.happytoplay.application.MyApplitation;

/**
 * Toast 工具类
 * Created by 11655 on 2016/9/28.
 */

public class ToastUtils {
    public static void toastShort(CharSequence text){
        Toast.makeText(MyApplitation.context,text,Toast.LENGTH_SHORT).show();
    }
    public static void toastLong(CharSequence text){
        Toast.makeText(MyApplitation.context,text,Toast.LENGTH_LONG).show();
    }
}
