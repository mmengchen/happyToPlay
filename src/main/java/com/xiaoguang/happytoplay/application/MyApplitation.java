package com.xiaoguang.happytoplay.application;

import android.app.Application;
import android.content.Context;

import com.xiaoguang.happytoplay.bean.User;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

/**
 * Created by 11655 on 2016/9/26.
 */

public class MyApplitation extends Application {
    /**
     * 维护一个全局的context对象
     */
    public static Context context;
    /**
     * 维护一个全局的user对象
     */
    public static User user;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化BMob短信服务SDK
        BmobSMS.initialize(context,"89138a540444011eb66341febb70e16a");
        //初始化SDK
        Bmob.initialize(this, "89138a540444011eb66341febb70e16a");
    }
}
