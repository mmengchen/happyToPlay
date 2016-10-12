package com.xiaoguang.happytoplay.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 活动发布后的提示界面
 * Created by 11655 on 2016/10/12.
 */

public class PublishTipActivity extends BaseActivity {

    //获取控件
    @BindView(R.id.act_pub_tip_tv_title)
    TextView mActPubTipTvTitle;
    @BindView(R.id.act_pub_tip_tv_finish)
    TextView mActPubTipTvFinish;
    @BindView(R.id.act_pub_tip_iv_share)
    ImageView mActPubTipIvShare;
    @BindView(R.id.act_pub_tip_btn_share)
    Button mActPubTipBtnShare;
    @BindView(R.id.act_pub_tip_btn_select)
    Button mActPubTipBtnSelect;
    @BindView(R.id.act_pub_tip_btn_to_scratch_cards)
    Button mActPubTipBtnToScratchCards;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_tip);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.act_pub_tip_tv_finish, R.id.act_pub_tip_btn_share, R.id.act_pub_tip_btn_select, R.id.act_pub_tip_btn_to_scratch_cards})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_pub_tip_tv_finish:
                break;
            case R.id.act_pub_tip_btn_share:
                break;
            case R.id.act_pub_tip_btn_select:
                break;
            case R.id.act_pub_tip_btn_to_scratch_cards:
                break;
        }
    }
}
