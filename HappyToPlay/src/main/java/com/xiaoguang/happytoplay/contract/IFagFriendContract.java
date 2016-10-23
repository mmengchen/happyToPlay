package com.xiaoguang.happytoplay.contract;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;
import com.xiaoguang.happytoplay.view.XListView;

/**
 * 契约接口，表明 View和Presenter之间的方法
 * Created by 11655 on 2016/9/26.
 */

public interface IFagFriendContract {
    interface IIFagFriendView extends IBaseView<IFagFriendPresenter> {
        XListView getmFragFriendXlv();

        /**
         *  Toast形式提示
         */
        void showMsg(String msg);

        /**
         * 加载提示框
         */
        void showLoading(String title, String msg, boolean flag);

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


     }
    interface IFagFriendPresenter extends  IBasePresenter{
        /**
         * 释放资源的方法
         */
        void destroy();
    }
}
