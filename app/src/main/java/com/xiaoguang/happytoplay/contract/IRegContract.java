package com.xiaoguang.happytoplay.contract;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;

/**
 * 注册的契约接口
 * Created by 11655 on 2016/9/27.
 */

public interface IRegContract {
    interface IRegView extends IBaseView<IRegPresenter> {
        /**
         * 获取控件中的数据
         */
        void getData();

        /**
         * 为控件设置数据显示
         */
        void setData(Object object);

        /**
         * 清楚文本框中的数据
         */
        void clean();
    }

    interface IRegPresenter extends IBasePresenter {
        /**
         * 注册用户
         *
         * @param object
         */
        void register(Object object, String code);

        void getCode(String phoneNumber);
    }
}
