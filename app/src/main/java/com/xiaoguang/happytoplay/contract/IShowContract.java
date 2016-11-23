package com.xiaoguang.happytoplay.contract;

import android.view.View;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;

import java.util.List;

/**
 * 契约接口，表明 View和Presenter之间的方法
 * Created by 11655 on 2016/9/26.
 */

public interface IShowContract {
    interface IShowView extends IBaseView<IShowPresenter> {

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
        void setData(Object object);

        /**
         * 获取控件中的数据
         */
        List getData();

     }
    interface IShowPresenter extends  IBasePresenter{
        /**
         * 设置数据
         * @param data 数据
         * @param v 控件
         */
        void setData(List data, View v);
    }
}
