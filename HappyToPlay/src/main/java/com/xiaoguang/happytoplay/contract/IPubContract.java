package com.xiaoguang.happytoplay.contract;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;

import java.util.ArrayList;

/**
 * 契约接口，表明 View和Presenter之间的方法
 * Created by 11655 on 2016/9/26.
 */

public interface IPubContract {
    interface IPubView extends IBaseView<IPubPresenter> {
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

        /**
         * 设置数据
         * @param object
         */
        void setData(Object object);

        /**
         * 获取控件中的数据
         */
        Grather getData();

        void onCreateBottomMenu(View v);
    }
    interface IPubPresenter extends  IBasePresenter{
        /**
         * 加载数据
         * @param o
         */
        void loadingData(Object... o);

        /**
         * 创建一个弹出框
         */

        void onCreateBottomMenu(View view);

        /**
         * 设置图片
         * @param context
         * @param data
         */
        void setIcoHeader(Context context, Intent data);

        void setIconHeader();

        /**
         * 更新UI操作
         * @param allSelectedPicture
         */
        void updateUI(ArrayList<String> allSelectedPicture);

        /**
         * 选择时间
         * @param context  当前activity的上下文
         */
        void chooseTime(Context context);

        /**
         * 发布活动
         * @param data  活动对象
         */
        void submit(Grather data);
    }
}
