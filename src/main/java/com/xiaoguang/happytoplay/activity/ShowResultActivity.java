package com.xiaoguang.happytoplay.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.contract.IShowContract;
import com.xiaoguang.happytoplay.presenter.ShowResultPresenterIMpl;
import com.xiaoguang.happytoplay.view.XListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示搜索结果页面
 * Created by 11655 on 2016/10/14.
 */

public class ShowResultActivity extends BaseActivity implements IShowContract.IShowView {

    //通过注解获取控件
    @BindView(R.id.act_show_result_xlv)
    XListView mActShowResultXlv;
    @BindView(R.id.act_show_result_btn)
    Button mActShowResultBtn;
    private IShowContract.IShowPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_result);
        ButterKnife.bind(this);
        new ShowResultPresenterIMpl(this);
        //获取数据,并设置数据
        presenter.setData(getData(), mActShowResultXlv);
    }

    @Override
    public void setPresenter(IShowContract.IShowPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hiddenLoading() {

    }

    @Override
    public void showMsg(String string) {

    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public void setData(Object object) {

    }

    @Override
    public List getData() {
        return (List) MyApplitation.getDatas("queryDatas", false);
    }

    @OnClick(R.id.act_show_result_btn)
    public void onClick() {
        finish();
    }
}
