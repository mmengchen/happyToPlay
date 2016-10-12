package com.xiaoguang.happytoplay.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.contract.IFragPersonContract;
import com.xiaoguang.happytoplay.presenter.FragPersonPresenterImpl;
import com.xiaoguang.happytoplay.utils.ImageLoaderutils;
import com.xiaoguang.happytoplay.utils.QrCodeUtils;
import com.xiaoguang.happytoplay.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/10/11.
 */

public class PersonDetailsActivity extends BaseActivity implements IFragPersonContract.IFragPersonView {

    //获取控件
    @BindView(R.id.act_person_details_ib_back)
    ImageButton mActPersonDetailsIbBack;
    @BindView(R.id.act_person_details_ib_menu)
    ImageButton mActPersonDetailsIbMenu;
    @BindView(R.id.act_person_details_civ_head)
    CircleImageView mActPersonDetailsCivHead;
    @BindView(R.id.act_person_details_tv_nickname)
    TextView mActPersonDetailsTvNickname;
    @BindView(R.id.act_person_details_tv_age)
    TextView mActPersonDetailsTvAge;
    @BindView(R.id.act_person_details_tv_location)
    TextView mActPersonDetailsTvLocation;
    @BindView(R.id.act_person_details_iv_code)
    ImageView mActPersonDetailsIvCode;
    @BindView(R.id.act_person_details_tv_phone)
    TextView mActPersonDetailsTvPhone;
    @BindView(R.id.act_person_details_tv_say)
    TextView mActPersonDetailsTvSay;
    @BindView(R.id.act_person_details_tv_shoucang)
    TextView mActPersonDetailsTvShoucang;
    @BindView(R.id.act_person_details_tv_guanzhu)
    TextView mActPersonDetailsTvGuanzhu;
    @BindView(R.id.act_person_details_tv_msg)
    TextView mActPersonDetailsTvMsg;
    @BindView(R.id.act_person_details_tv_zhanghu_msg)
    TextView mActPersonDetailsTvZhanghuMsg;

    private IFragPersonContract.IFragPersonPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_person_details);
        ButterKnife.bind(this);
        new FragPersonPresenterImpl(this);
        setData();
        //获取控件设置数据
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void setPresenter(IFragPersonContract.IFragPersonPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hiddenLoading() {

    }

    @Override
    public void jumpActivity(int type) {

    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public void setData(Object... object) {
        //显示数据到控件上
        mActPersonDetailsTvNickname.setText(MyApplitation.user.getNickName());
        mActPersonDetailsTvPhone.setText(MyApplitation.user.getUsername());
        mActPersonDetailsTvAge.setText(String.valueOf(MyApplitation.user.getAge())+" 岁");
        mActPersonDetailsTvLocation.setText(MyApplitation.user.getAddress());
        //设置二维码
        mActPersonDetailsIvCode.setImageBitmap(QrCodeUtils.createQrCode(MyApplitation.user));
        //加载头像
        presenter.loadHeader();
    }

    @Override
    public void getData() {

    }

    @Override
    public void displayImage(String uri) {
        //设置图片加载器的参数
        DisplayImageOptions options = ImageLoaderutils.myGetOpt(R.drawable.loading, R.drawable.logding_error);
        //获取图片加载器对象
        ImageLoader loader = ImageLoaderutils.getInstance(MyApplitation.context);
        //展示图片
        loader.displayImage(uri, mActPersonDetailsCivHead, options);
    }

    @OnClick({R.id.act_person_details_ib_back, R.id.act_person_details_ib_menu, R.id.act_person_details_tv_shoucang, R.id.act_person_details_tv_guanzhu, R.id.act_person_details_tv_msg, R.id.act_person_details_tv_zhanghu_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_person_details_ib_back:
                break;
            case R.id.act_person_details_ib_menu:
                break;
            case R.id.act_person_details_tv_shoucang:
                break;
            case R.id.act_person_details_tv_guanzhu:
                break;
            case R.id.act_person_details_tv_msg:
                break;
            case R.id.act_person_details_tv_zhanghu_msg:
                break;
        }
    }
}
