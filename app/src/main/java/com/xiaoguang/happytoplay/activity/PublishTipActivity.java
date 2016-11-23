package com.xiaoguang.happytoplay.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.contract.IPublishTipContract;
import com.xiaoguang.happytoplay.presenter.PubTipPresenterImpl;
import com.xiaoguang.happytoplay.view.BottomPopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 活动发布后的提示界面，当前页面暂未使用MVp模式，以后将更改，现阶段进行分享测试
 * Created by 11655 on 2016/10/12.
 */

public class PublishTipActivity extends BaseActivity implements IPublishTipContract.IPubTipView {

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
    //底部弹出框
    private BottomPopView bottomPopView;
    private IPublishTipContract.IPubTipPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_tip);
        ButterKnife.bind(this);
        new PubTipPresenterImpl(this);
    }

    @OnClick({R.id.act_pub_tip_iv_share, R.id.act_pub_tip_tv_finish, R.id.act_pub_tip_btn_share, R.id.act_pub_tip_btn_select, R.id.act_pub_tip_btn_to_scratch_cards})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_pub_tip_iv_share:
                onCreateBottomMenu(view);
                break;
            case R.id.act_pub_tip_tv_finish:
                //结束当前activity
                finish();
                break;
            case R.id.act_pub_tip_btn_share:
                onCreateBottomMenu(view);
                break;
            case R.id.act_pub_tip_btn_select:
                break;
            case R.id.act_pub_tip_btn_to_scratch_cards:
                break;
        }
    }

    /**
     * 创建view
     * @param v
     */
    public void onCreateBottomMenu(View v) {
        if (bottomPopView==null){
            bottomPopView = new BottomPopView(this, v) {
                @Override
                public void onTopButtonClick() {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.act_person_details_zhanghu_msg);
                    //分享好友列表
                    presenter.shareWXAPP(0,bitmap);
                    //点击之后隐藏
                    bottomPopView.dismiss();
                }

                @Override
                public void onBottomButtonClick() {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.act_person_details_zhanghu_msg);
                    //分享到朋友圈
                    presenter.shareWXAPP(1,bitmap);
                    bottomPopView.dismiss();
                }
            };
            bottomPopView.setTopText("分享到好友列表");
            bottomPopView.setBottomText("分享到朋友圈");
            bottomPopView.show();
        }else {
            bottomPopView.setTopText("分享到好友列表");
            bottomPopView.setBottomText("分享到朋友圈");
            bottomPopView.show();
        }

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
    public void setPresenter(IPublishTipContract.IPubTipPresenter presenter) {
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
    public void setData(Object object) {

    }

    @Override
    public void getData() {

    }
}
