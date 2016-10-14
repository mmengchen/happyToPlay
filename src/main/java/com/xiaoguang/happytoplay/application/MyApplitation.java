package com.xiaoguang.happytoplay.application;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

import c.b.BP;
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
     * 维护一个全局的当前user对象
     */
    public static User user;

    private static Map<String, Object> datas = new HashMap<String, Object>();
    /**
     * 用于存放活动对应的发布活动的用户信息
     */
    public static Map<String, User> userMap = new HashMap<String, User>();
    LocationClient mLocationClient = null;

    public static Object getDatas(String key, boolean delFlag) {
        if (delFlag) {
            return datas.remove(key);
        }
        return datas.get(key);
    }

    public static Object putDatas(String key, Object value) {
        return datas.put(key, value);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化BMob短信服务SDK
        BmobSMS.initialize(context, "89138a540444011eb66341febb70e16a");
        //初始化BMobSDK
        Bmob.initialize(this, "89138a540444011eb66341febb70e16a");
        //初始化支付
        BP.init(context, "89138a540444011eb66341febb70e16a");
        //初始化百度地图SDK
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LogUtils.i("TagMap", "我在请求获取位置信息");
                if (bdLocation != null) {
                    LogUtils.i("TagMap", "当前用户的位置为" + bdLocation.getAddrStr());
                    //将获取到的位置信息保存
                    putDatas("currentLocation", bdLocation);
                }
            }
        });    //注册监听函数
        initLocation();
        //获取当前用户所在位置
        mLocationClient.start();
    }

    /**
     * 初始化位置参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(true);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


}
