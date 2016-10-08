package com.xiaoguang.happytoplay.presenter;

import com.xiaoguang.happytoplay.adapter.XListVewAdapter;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.contract.IFragHomeContract;
import com.xiaoguang.happytoplay.view.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentHome 的Presenter类
 * Created by 11655 on 2016/9/28.
 */

public class FragHomePresenterImpl implements IFragHomeContract.IFragHomePresenter {
    private final IFragHomeContract.IFragHomeView view;
    //声明一个listview 控件
    private XListView xlistview;
    private List<Grather> grathers;

    public FragHomePresenterImpl(IFragHomeContract.IFragHomeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadingData(Object... o) {
        //为listview 添加数据源
        xlistview = (XListView)o[0];
        start();
        xlistview.setAdapter(new XListVewAdapter(MyApplitation.context,grathers));
    }
    @Override
    public void search(String searchText) {

    }

    /**
     * 进行数据的初始化操作
     */
    @Override
    public void start() {
        grathers = new ArrayList<Grather>();
        for (int i = 0; i<2;i++){
            grathers.add(new Grather());
        }
        //暂时进行模拟数据

    }
}
