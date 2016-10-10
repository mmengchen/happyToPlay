package com.xiaoguang.happytoplay.presenter;

import com.xiaoguang.happytoplay.adapter.XListVewAdapter;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.contract.IFragHomeContract;
import com.xiaoguang.happytoplay.model.GratherModelImpl;
import com.xiaoguang.happytoplay.utils.LogUtils;
import com.xiaoguang.happytoplay.view.XListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * FragmentHome 的Presenter类
 * Created by 11655 on 2016/9/28.
 */

public class FragHomePresenterImpl implements IFragHomeContract.IFragHomePresenter {
    private final IFragHomeContract.IFragHomeView view;
    //声明一个listview 控件
    private XListView xlistview;
    private List<Grather> grathers = new ArrayList<>();
    //声明Model对象
    private GratherModelImpl gratherModel;
    private XListVewAdapter adapter;

    public FragHomePresenterImpl(IFragHomeContract.IFragHomeView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadingData(Object... o) {
        //为listview 添加数据源
        xlistview = (XListView) o[0];
        start();
        //实例化适配器
        adapter = new XListVewAdapter(MyApplitation.context, grathers);
        //设置适配器
        xlistview.setAdapter(adapter);
        //为XlistView添加上拉刷新和下拉加载
        xlistview.setPullLoadEnable(true);
        xlistview.setPullLoadEnable(true);
        //实例化模型对象
        gratherModel = new GratherModelImpl();
        xlistview.setXListViewListener(new XListView.IXListViewListener() {
            /**
             *  刷新数据
             */
            @Override
            public void onRefresh() {
                gratherModel.queryGrather(100, null, 0, 10, new GratherModelImpl.QueryCallBack<Grather>() {
                    @Override
                    public void done(List<Grather> result, BmobException e) {
                        if (e == null) {
                            //将服务器返回的活动信息保存到内存中,保证数据的统一性
                            MyApplitation.putDatas("grathers", result);
                            //更新
                            grathers = result;
                            //停止刷新
                            xlistview.stopRefresh();
                            //重新设置适配器，已更新数据
                            //实例化适配器
                            adapter = new XListVewAdapter(MyApplitation.context, grathers);
                            xlistview.setAdapter(adapter);
                            view.showMsg("刷新成功");
                        } else {
                            xlistview.stopRefresh();
                            view.showMsg("刷新失败");
                            LogUtils.i("刷新失败" + e.toString());
                        }
                    }
                });
            }

            /**
             * 加载更多
             */
            @Override
            public void onLoadMore() {
                gratherModel.queryGrather(200, null, grathers.size(), 10, new GratherModelImpl.QueryCallBack<Grather>() {
                    @Override
                    public void done(List<Grather> result, BmobException e) {

                        if (e == null) {
                            //将加载多的数据添加的集合中
                            grathers.addAll(result);
                            //将服务器返回的活动信息保存到内存中
                            MyApplitation.putDatas("grathers", result);
                            //更新数据
                            adapter.notifyDataSetChanged();
                            //停止加载数据
                            xlistview.stopLoadMore();
                            view.showMsg("加载数据成功");
                        } else {
                            //停止加载数据
                            xlistview.stopLoadMore();
                            view.showMsg("加载数据失败");
                            LogUtils.i("加载数据失败" + e.toString());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void search(String searchText) {

    }

    /**
     * 进行数据的初始化操作
     */
    @Override
    public void start() {
        //初始化XlistView数据,从内存中获取活动数据
        if ((List<Grather>) MyApplitation.getDatas("grathers", false)!=null){
            grathers = (List<Grather>) MyApplitation.getDatas("grathers", false);
        }
    }
}
