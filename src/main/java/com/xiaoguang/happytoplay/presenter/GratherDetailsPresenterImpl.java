package com.xiaoguang.happytoplay.presenter;

import android.widget.GridView;

import com.xiaoguang.happytoplay.adapter.DiscussXlvAdapter;
import com.xiaoguang.happytoplay.adapter.GratherDetailsGridViewAdapter;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Discuss;
import com.xiaoguang.happytoplay.contract.IGratherDetailsContract;
import com.xiaoguang.happytoplay.model.DiscussModel;
import com.xiaoguang.happytoplay.utils.LogUtils;
import com.xiaoguang.happytoplay.view.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * 活动详情处理的Presenter类
 * Created by 11655 on 2016/10/14.
 */

public class GratherDetailsPresenterImpl implements IGratherDetailsContract.IGratherDetailsPresenter {
    private IGratherDetailsContract.IGratherDetailsView view;
    private DiscussModel discussModel = new DiscussModel();
    private DiscussXlvAdapter adapter;

    public GratherDetailsPresenterImpl(IGratherDetailsContract.IGratherDetailsView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadingData(Object o) {

    }

    @Override
    public void setImages(List<BmobFile> imgFiles, GridView mActGratherDetialsGv) {
        ArrayList<String> imgUrls = new ArrayList<>();
        for (BmobFile file : imgFiles) {
            //将url放入集合中
            imgUrls.add(file.getUrl());
        }
        //设置适配器
        mActGratherDetialsGv.setAdapter(new GratherDetailsGridViewAdapter(imgUrls, MyApplitation.context, this));
    }

    @Override
    public void showDiscuss(final String objectId, final XListView mActGratherDetialsXlv) {
        view.showLoading(null, "数据加载中", false);
        //根据当前活动id 查询评论表中的数据
        discussModel.queryDisscuss(objectId, 200, new DiscussModel.QueryCallBack() {
            @Override
            public void done(List<Discuss> list, BmobException e) {
                if (e == null) {//查询成功
                    view.hiddenLoading();
                    //设置适配器
                    if (list == null) {
                        list = new ArrayList<Discuss>();
                    }
                    //设置适配器
                    adapter = new DiscussXlvAdapter(list, MyApplitation.context, GratherDetailsPresenterImpl.this);
                    mActGratherDetialsXlv.setAdapter(adapter);
                } else {
                    view.hiddenLoading();
                    view.showMsg("数据显示失败");
                    LogUtils.i("数据显示失败" + e.toString());
                }
            }
        });
        //评论的刷新功能
        //为XlistView添加上拉刷新和下拉加载
        mActGratherDetialsXlv.setPullLoadEnable(true);
        mActGratherDetialsXlv.setPullLoadEnable(true);
        //用于存放数据
        mActGratherDetialsXlv.setXListViewListener(new XListView.IXListViewListener() {

            //用于存放结果返回的数据，实现对数据的刷新和加载操作
            private List<Discuss> listDiscuss = new ArrayList<Discuss>();

            @Override
            public void onRefresh() {
                //根据当前活动id 查询评论表中的数据
                discussModel.queryDisscuss(objectId, 200, new DiscussModel.QueryCallBack() {
                    @Override
                    public void done(List<Discuss> list, BmobException e) {
                        mActGratherDetialsXlv.stopRefresh();
                        view.hiddenLoading();
                        if (e == null) {//查询成功
                            //设置适配器
                            if (list != null) {
                                listDiscuss = list;
                            }
                            adapter = new DiscussXlvAdapter(listDiscuss, MyApplitation.context, GratherDetailsPresenterImpl.this);
                            mActGratherDetialsXlv.setAdapter(adapter);
                        } else {
                            view.showMsg("数据刷新失败");
                            LogUtils.i("数据刷新失败" + e.toString());
                        }
                    }
                });
            }

            @Override
            public void onLoadMore() {//加载更多
                //根据当前活动id 查询评论表中的数据
                discussModel.queryDisscuss(objectId, 100, new DiscussModel.QueryCallBack() {
                    @Override
                    public void done(List<Discuss> list, BmobException e) {
                        mActGratherDetialsXlv.stopLoadMore();
                        if (e == null) {//查询成功
                            view.hiddenLoading();
                            //设置适配器
                            if (list != null) {
                                //将加载的更多数据放入集合中
                                listDiscuss.addAll(list);
                            }
                            //加载更多数据更新
                            adapter.notifyDataSetChanged();

                        } else {
                            view.hiddenLoading();
                            view.showMsg("数据加载失败");
                            LogUtils.i("数据显示失败" + e.toString());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void sendDiscuss(final String gratherObjectId, String text, String usrObjecId) {
        view.showLoading(null, "评论发布中", false);
        //获取系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strTime = formatter.format(curDate);
        final Discuss discuss = new Discuss();
        //设置数据
        discuss.setDiscussUserId(usrObjecId);
        discuss.setDiscussTime(strTime);
        discuss.setGratehrId(gratherObjectId);
        discuss.setDiscussText(text);
        //首先在保存评论到评论表中
        discussModel.save(discuss, new DiscussModel.CallBack() {
            @Override
            public void done(final String objectId, BmobException e) {
                if (e == null) {//评论成功
                    LogUtils.i("评论成功");
                    view.hiddenLoading();
                    view.showMsg("评论成功");
                } else {
                    view.showMsg("评论失败");
                    view.hiddenLoading();
                    LogUtils.i("评论失败" + e.toString());
                }
            }
        });
    }

    @Override
    public void queryUseError(BmobException e) {
        view.hiddenLoading();
        view.showMsg("查询用户信息失败");
        LogUtils.i("查询用户信息失败" + e.toString());
    }

    @Override
    public void queryUserSuccess() {
        view.hiddenLoading();
        view.showMsg("查询用户信息成功");
        LogUtils.i("查询用户信息成功");

    }

    @Override
    public void start() {

    }
}
