package com.xiaoguang.happytoplay.presenter;

import android.widget.GridView;

import com.xiaoguang.happytoplay.adapter.DiscussXlvAdapter;
import com.xiaoguang.happytoplay.adapter.GratherDetailsGridViewAdapter;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Discuss;
import com.xiaoguang.happytoplay.bean.Grather;
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
import cn.bmob.v3.listener.UpdateListener;

/**
 * 活动详情处理的Presenter类
 * Created by 11655 on 2016/10/14.
 */

public class GratherDetailsPresenterImpl implements IGratherDetailsContract.IGratherDetailsPresenter {
    private IGratherDetailsContract.IGratherDetailsView view;
    private DiscussModel discussModel = new DiscussModel();
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
    public void showDisscuss(List<String> discussIds, XListView mXlv) {
//        view.showLoading(null,"数据加载中",false);
        //定义一个集合，存放评论对象//存在空指针情况
        if(discussIds==null){
            return;
        }
        LogUtils.i("活动ID集合为"+discussIds.size()+"评论的id"+discussIds.toString());
        final List<Discuss> discussList = new ArrayList<>();
        //根据评论的集合查询出每条数据，，可能有问题
//        for (String objectId:discussIds){
//            discussModel.queryDisscuss(objectId, new DiscussModel.QueryCallBack() {
//                @Override
//                public void done(Discuss discuss, BmobException e) {
//                    if (e==null){
//                        discussList.add(discuss);
//                    }
//                    else {
//                        LogUtils.i("查询评论失败"+e.toString());
//                    }
//                }
//            });
//        }
//        for (int i=0;i<)
        LogUtils.i("我的数据源"+discussList.size());
        //设置适配器
        mXlv.setAdapter(new DiscussXlvAdapter(discussList,MyApplitation.context,this));
        LogUtils.i("我在进行设置适配器");
    }

    @Override
    public void sendDiscuss(final String gratherObjectId, String text, String usrObjecId) {
        view.showLoading(null, "评论发布中", false);

        final Discuss discuss = new Discuss();
        //设置数据
        discuss.setDiscussUserId(usrObjecId);

        //获取系统时间
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strTime = formatter.format(curDate);
        discuss.setDiscussTime(strTime);
        //首先在保存评论到评论表中
        discussModel.save(discuss, new DiscussModel.CallBack() {
            @Override
            public void done(final String objectId, BmobException e) {
                if (e == null) {//评论保存成功
                    Grather grather = new Grather();
                    grather.add("discussIds",objectId);
                    //将当前评论的id保存到活动表中
                    grather.update(gratherObjectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){//更新表中数据成功
                                view.hiddenLoading();
                                view.showMsg("评论发布成功");
                                LogUtils.i("评论发布成功");
                            }else {//更新表失败，进行回退操作，删除上一个表中数据,保证数据的统一
                                discuss.delete(objectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            LogUtils.i("删除评论成功");
                                        }else {
                                            LogUtils.i(" 删除评论失败"+e.toString());
                                       }
                                    }
                                });
                            }
                        }
                    });
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
        LogUtils.i("查询用户信息失败"+e.toString());
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
