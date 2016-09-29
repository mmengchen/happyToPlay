package com.xiaoguang.happytoplay.presenter;

import com.xiaoguang.happytoplay.contract.IFragMsgContract;

/**
 * Created by 11655 on 2016/9/28.
 */

public class FragMsgPresenterImpl implements IFragMsgContract.IFragMsgPresenter {
    @Override
    public void loadingData(Object... o) {

    }

    @Override
    public void search(String searchText) {

    }

    @Override
    public void start() {

    }
//    private IFragMsgContract.IFragMsgView view ;
//    //声明Fragment管理器
//    private FragmentManager manager;
//    //声明Fragment操作的事务
//    private FragmentTransaction tran;
//
//    public FragMsgPresenterImpl(IFragMsgContract.IFragMsgView view) {
//        this.view = view;
//        view.setPresenter(this);
//    }
//
//    @Override
//    public void loadingData(Object... o) {
//        //获取FragmentManager对象
//        manager = (FragmentManager) o[1];
//        //需要使用事务来操作fragment
//        tran = manager.beginTransaction();
//        //动态的加载Fragment
//        if ((int)o[3]==1){//加载好友布局
//            LogUtils.i("我在加载好友布局");
////            tran.add(R.id.frag_message_fl,new FriendFragment(),"friend");
//            tran.replace(R.id.frag_message_fl,new FriendFragment(),"friend");
//        } else if ((int)o[3]==2){//加载活动的布局
//            LogUtils.i("我在加载活动布局");
//            tran.replace(R.id.frag_message_fl,new ActiveFragment(),"act");
//        } else {
//            LogUtils.i("我在执行其他操作");
//        }
//        //提交操作
//        tran.commit();
//    }
//
//    @Override
//    public void search(String searchText) {
//
//    }
//
//    @Override
//    public void start() {
//
//    }
}
