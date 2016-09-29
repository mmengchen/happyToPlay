package com.xiaoguang.happytoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.contract.IFragMsgContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/9/28.
 */

public class MessageFragment extends Fragment implements IFragMsgContract.IFragMsgView {

    //获取控件
    @BindView(R.id.frag_message_ib_back)
    ImageButton mFragMessageIbBack;
    @BindView(R.id.frag_message_tv_friend)
    TextView mFragMessageTvFriend;
    @BindView(R.id.frag_message_tv_act)
    TextView mFragMessageTvAct;
    @BindView(R.id.frag_person_ib_menu)
    ImageButton mFragPersonIbMenu;
    @BindView(R.id.frag_message_fl)
    FrameLayout mFragMessageFl;
    private IFragMsgContract.IFragMsgPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new FragMsgPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_message, null);
        ButterKnife.bind(this, view);
        //获取当前默认的布局
//        presenter.loadingData(getFragmentManager(),mFragMessageFl,1);
        return view;
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
    public void setPresenter(IFragMsgContract.IFragMsgPresenter presenter) {
        this.presenter = presenter;

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

    /**
     * 为控件添加点击事件
     * @param view
     */
    @OnClick({R.id.frag_message_ib_back, R.id.frag_message_tv_friend, R.id.frag_message_tv_act, R.id.frag_person_ib_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_message_ib_back:
                break;
            case R.id.frag_message_tv_friend:
                //动态，加载好友布局(Fragment)
                presenter.loadingData(getFragmentManager(),mFragMessageFl,1);
                break;
            case R.id.frag_message_tv_act:
                //动态，加载活动布局（Fragment）
                presenter.loadingData(getFragmentManager(),mFragMessageFl,2);
                break;
            case R.id.frag_person_ib_menu:
                break;
        }
    }
}
