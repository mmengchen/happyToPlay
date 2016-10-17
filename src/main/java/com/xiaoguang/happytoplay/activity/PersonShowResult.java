package com.xiaoguang.happytoplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.contract.IShowContract;
import com.xiaoguang.happytoplay.view.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人中心功能查询的结果页面
 * Created by 11655 on 2016/10/17.
 */

public class PersonShowResult extends BaseActivity implements IShowContract.IShowView {

    //获取控件
    IShowContract.IShowPresenter presenter;
    @BindView(R.id.act_show_result_tv)
    TextView mActShowResultTv;
    @BindView(R.id.frag_message_rl_header)
    RelativeLayout mFragMessageRlHeader;
    @BindView(R.id.act_show_result_xlv)
    XListView mActShowResultXlv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_result);
        ButterKnife.bind(this);
        intiData();
    }

    /**
     * 初始化数据
     */
    private void intiData() {
        Intent intent = getIntent();
        int  type = intent.getIntExtra("type",0);
        List datas = new ArrayList();
        switch (type){
            case 3: //订单查询的显示数据
                datas = MyApplitation.user.getOrderIds();
                break;
            case 4://参加活动的数据
                datas = MyApplitation.user.getJoinGratherIds();
                break;
            default:break;
        }
        //设置适配器
        mActShowResultXlv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas));
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
        return null;
    }
}
