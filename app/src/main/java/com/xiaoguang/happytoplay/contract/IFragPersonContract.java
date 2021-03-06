package com.xiaoguang.happytoplay.contract;

import android.content.Context;
import android.content.Intent;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;

import java.util.List;

/**
 * 契约接口，表明 View和Presenter之间的方法
 * Created by 11655 on 2016/9/26.
 */

public interface IFragPersonContract {
    interface IFragPersonView extends IBaseView<IFragPersonPresenter> {
        /**
         * Toast形式提示
         */
        void showMsg(String msg);

        /**
         * 加载提示框
         */
        void showLoading();

        void showLoading(String msg);

        /**
         * 隐藏提示框
         */
        void hiddenLoading();


        void jumpActivity(List list,int type);
        /**
         * 返回
         *
         * @return
         */
        boolean back();

        /**
         * 设置数据
         *
         * @param object
         */
        void setData(Object... object);

        /**
         * 获取控件中的数据
         */
        void getData();
        /**
         * 展示头像
         * @param uri
         */
        void displayImage(String uri);
    }

    interface IFragPersonPresenter extends IBasePresenter {
        /**
         * 加载数据
         *
         * @param o
         */
        void loadingData(Object... o);

        /**
         * 搜索相关的操作
         *
         * @param searchText
         */
        void search(String searchText);

        /**
         * 设置头像
         * @param context  上下文
         * @param data 从系统图库返回的Intent
         */
        void setIcoHeader(Context context, Intent data);

        /**
         * 设置头像，从拍照返回
         */
        void setIconHeader();

        /**
         * 从服务器加载头像
         */
        void loadHeader();

        /**
         * Item的查询方法
         * @param type 查询的类型 0 优惠查询 1.收藏查询 2.发起活动查询 3.订单查询 4.参加活动查询
         */
        void query(int type);
    }
}
