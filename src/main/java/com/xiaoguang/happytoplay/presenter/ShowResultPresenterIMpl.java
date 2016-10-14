package com.xiaoguang.happytoplay.presenter;

import android.view.View;

import com.xiaoguang.happytoplay.adapter.XListVewAdapter;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.contract.IShowContract;
import com.xiaoguang.happytoplay.view.XListView;

import java.util.List;

/**
 * Created by 11655 on 2016/10/14.
 */

public class ShowResultPresenterIMpl implements IShowContract.IShowPresenter {
    private  IShowContract.IShowView view;

    public ShowResultPresenterIMpl(IShowContract.IShowView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void setData(List datas, View v) {
        //获取XListView 控件
        XListView xlv  = (XListView) v;
        //取消上拉刷新和下拉加载
        xlv.setPullLoadEnable(false);
        xlv.setPullRefreshEnable(false);
        //设置适配器对象
        xlv.setAdapter(new XListVewAdapter(MyApplitation.context,datas));
    }

    @Override
    public void start() {

    }
}
