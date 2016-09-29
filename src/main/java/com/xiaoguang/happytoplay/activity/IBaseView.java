package com.xiaoguang.happytoplay.activity;

/**
 * 所有视图层的接口类
 * Created by 11655 on 2016/9/26.
 */

public interface IBaseView<T> {
    /**
     *  将View 和 Presenter 建立关系
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 跳转Activity的方法
     */
    void jumpActivity();

    /**
     * 加载提示框
     */
    void showLoading();

    /**
     * 隐藏提示框
     */
    void hiddenLoading();

    /**
     * Toast数据
     * @param string
     */
    void showMsg(String string);
}
