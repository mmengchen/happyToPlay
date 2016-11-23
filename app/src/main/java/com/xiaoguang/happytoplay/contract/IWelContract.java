package com.xiaoguang.happytoplay.contract;

import android.view.View;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;

/**
 *
 * 欢迎界面的的契约接口
 * Created by 11655 on 2016/9/27.
 */

public interface IWelContract {
    interface IWelView extends IBaseView<IWelPresenter> {
        void showDialog();

        void jumpHome();
    }
    interface IWelPresenter extends IBasePresenter {
        /**
         * 开启第一个动画
         * @param view 与动画绑定的view 控件
         */
        void startAnimation(View view);

        /**
         * 判断当前的网络状态
         * @return 返回true代表网络连接成功，返回false 表示网络连接失败，无网络连接
         */
        void isNetWorkState();

        /**
         * 处理网络状态
         * @param netstate
         */
        void handleNetWorkState(boolean netstate);

    }
}
