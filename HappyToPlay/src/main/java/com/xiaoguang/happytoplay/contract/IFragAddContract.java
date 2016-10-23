package com.xiaoguang.happytoplay.contract;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;

/**
 * 契约接口，表明 View和Presenter之间的方法
 * Created by 11655 on 2016/9/26.
 */

public interface IFragAddContract {
    interface IFragAddView extends IBaseView<IFragAddPresenter> {
        /**
         *  Toast形式提示
         */
        void showMsg(String msg);

        /**
         * 加载提示框
         */
        void showLoading();

        /**
         * 隐藏提示框
         */
        void hiddenLoading();

        /**
         * 页面跳转
         */
        void jumpActivity();

        /**
         * 返回
         * @return
         */
        boolean back();

        /**
         * 设置数据
         * @param object
         */
        void setData(Object... object);

        /**
         * 获取控件中的数据
         */
        void getData();

     }
    interface IFragAddPresenter extends  IBasePresenter{
        /**
         * 加载数据
         * @param o
         */
        void loadingData(Object... o);

        /**
         * 搜索相关的操作
         * @param searchText
         */
        void search(String searchText);
    }
}
