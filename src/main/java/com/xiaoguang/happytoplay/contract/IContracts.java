package com.xiaoguang.happytoplay.contract;

/**
 * 用于存放一些SDK 的APP_KEY 一些不便的数据
 * Created by 11655 on 2016/10/16.
 */

public interface IContracts {
    /**
     * 默认头像的地址
     */
    String DEFAULT_HEADE_URI = "http://bmob-cdn-6590.b0.upaiyun.com/2016/10/16/22901ee0406f7af280b56a1b5d555f58.png";
    /**
     * 默认城市信息地址
     */
    String CITYS_URL ="http://bmob-cdn-6590.b0.upaiyun.com/2016/10/17/d99fff2a407a272480feabe07227945e.html";
    /**
     * Bmob 云的application key
     */
    String BMOB_SDK_APP_KEY = "89138a540444011eb66341febb70e16a";
    /**
     * 此为支付插件的官方最新版本号,请在更新时留意更新说明
     */
    int PLUGINVERSION = 7;
    /**
     * 支付成功的返回码
     */
    int RESULT_SUCCESS = 200;
    /**
     * 支付失败的返回码
     */
    int RESULT_FAIL = 300;
    /**
     * 支付其他情况的返回码
     */
    int RESULT_UN = 400;


}
