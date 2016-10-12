package com.xiaoguang.happytoplay.presenter;

import com.xiaoguang.happytoplay.contract.IFragMsgContract;

/**
 * Created by 11655 on 2016/9/28.
 */

public class FragMsgPresenterImpl implements IFragMsgContract.IFragMsgPresenter {
    private IFragMsgContract.IFragMsgView view;
    public FragMsgPresenterImpl(IFragMsgContract.IFragMsgView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadingData(Object... o) {

    }

    @Override
    public void search(String searchText) {

    }

    @Override
    public void start() {

    }
}
