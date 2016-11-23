package com.xiaoguang.happytoplay.contract;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;

/**
 * 契约接口，表明 View和Presenter之间的方法
 * Created by 11655 on 2016/9/26.
 */

public interface ILoginContract {
    interface ILoginView extends IBaseView<ILoginPresenter> {
        /**
         * 获取控件中的数据
         */
        void getData();

        /**
         * 为控件设置数据显示
         * @param obj
         */
        void setData(Object obj);

        void clean();
    }
    interface ILoginPresenter extends  IBasePresenter{
        /**
         * 登陆处理
         * @param object
         */
        void login(Object object);

    }
}
