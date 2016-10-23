package com.xiaoguang.happytoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseFragment;
import com.xiaoguang.happytoplay.contract.IFagFriendContract;
import com.xiaoguang.happytoplay.presenter.FragFridendPresenterImpl;
import com.xiaoguang.happytoplay.view.XListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 好友的Fragment
 * Created by 11655 on 2016/9/29.
 */

public class FriendFragment extends BaseFragment implements IFagFriendContract.IIFagFriendView {
    @BindView(R.id.frag_friend_xlv)
    XListView mFragFriendXlv;
    private IFagFriendContract.IFagFriendPresenter presenter;

    @Override
    protected View initLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.frag_friend, null);
        ButterKnife.bind(this,view);
        return view;
    }

    /**
     * 获取控件信息
     * @return
     */
    @Override
    public XListView getmFragFriendXlv(){
        return mFragFriendXlv;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new FragFridendPresenterImpl(this);
        presenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoading(String title, String msg, boolean flag) {

    }

    @Override
    public void hiddenLoading() {

    }

    @Override
    public void setPresenter(IFagFriendContract.IFagFriendPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public boolean back() {
        return false;
    }

}
