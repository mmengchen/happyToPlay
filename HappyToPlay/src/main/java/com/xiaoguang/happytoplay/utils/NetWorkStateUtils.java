package com.xiaoguang.happytoplay.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断手机网络状态
 *
 * Created by 11655 on 2016/9/26.
 */

public class NetWorkStateUtils {

    /**
     * 判断当前网络的状态
     * @param context 上下文
     * @return true 网络为可用状态  false 网络为不可用状态
     */
    public static boolean isNetworkAvailable(Context context){
        //获取系统提供的网络服务
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager!=null){
            //获取网络的状态信息
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info!=null&&info.isConnected()){
                //当前网络是连接的
                if (info.getState()==NetworkInfo.State.CONNECTED) ;
                //当前的网络连接是可用的
                return true;
            }
        }
        return false;
    }
}

