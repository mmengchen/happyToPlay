package com.xiaoguang.happytoplay.contract;

import com.xiaoguang.happytoplay.activity.IBaseView;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.presenter.IBasePresenter;

import java.util.List;

/**
 * 契约接口，表明 View和Presenter之间的方法
 * Created by 11655 on 2016/9/26.
 */

public interface IFragMoreContract {
    interface IFragMoreView extends IBaseView<IFragMorePresenter> {
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
         * @param datas 查询结果List 类型的数据
         */
        void jumpActivity(List<Grather> datas);

        /**
         * 返回
         * @return
         */
        boolean back();

     }
    interface IFragMorePresenter extends  IBasePresenter{
        /**
         * 加载数据
         * @param o
         */
        void loadingData(Object... o);

        /**
         * 查询活动信息
         * @param datas 参数
         * @param i 类型 0 模糊查询 1，2，3， 其他条件查询 4 类型查询
         */
        void query(String datas, int i);
    }
}
