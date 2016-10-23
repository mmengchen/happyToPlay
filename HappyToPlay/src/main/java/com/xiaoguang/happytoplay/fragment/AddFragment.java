package com.xiaoguang.happytoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseFragment;
import com.xiaoguang.happytoplay.contract.IFragAddContract;

/**
 * Created by 11655 on 2016/9/28.
 */

public class AddFragment extends BaseFragment implements IFragAddContract.IFragAddView{
    @Override
    protected View initLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_add,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hiddenLoading() {

    }

    @Override
    public void setPresenter(IFragAddContract.IFragAddPresenter presenter) {

    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public void setData(Object... object) {

    }

    @Override
    public void getData() {

    }
}
