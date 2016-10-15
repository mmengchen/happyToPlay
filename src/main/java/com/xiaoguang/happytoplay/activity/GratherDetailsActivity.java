package com.xiaoguang.happytoplay.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.contract.IGratherDetailsContract;
import com.xiaoguang.happytoplay.presenter.GratherDetailsPresenterImpl;
import com.xiaoguang.happytoplay.utils.LogUtils;
import com.xiaoguang.happytoplay.utils.ToastUtils;
import com.xiaoguang.happytoplay.view.XListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 显示活动详情的页面
 * Created by 11655 on 2016/10/12.
 */

public class GratherDetailsActivity extends BaseActivity implements IGratherDetailsContract.IGratherDetailsView {

    //通过注解形式获取控件
    @BindView(R.id.frag_message_ib_back)
    ImageButton mFragMessageIbBack;
    @BindView(R.id.frag_person_ib_menu)
    ImageButton mFragPersonIbMenu;
    @BindView(R.id.act_grather_detials_tv_grather_title)
    TextView mActGratherDetialsTvGratherTitle;
    @BindView(R.id.act_grather_detials_tv_location)
    TextView mActGratherDetialsTvLocation;
    @BindView(R.id.act_grather_detials_tv_time)
    TextView mActGratherDetialsTvTime;
    @BindView(R.id.act_grather_detials_tv_type)
    TextView mActGratherDetialsTvType;
    @BindView(R.id.act_grather_detials_tv_money)
    TextView mActGratherDetialsTvMoney;
    @BindView(R.id.act_grather_detials_tv_grather_content)
    TextView mActGratherDetialsTvGratherContent;
    @BindView(R.id.act_grather_detials_gv)
    GridView mActGratherDetialsGv;
    @BindView(R.id.act_grather_detials_btn_join)
    Button mActGratherDetialsBtnJoin;
    @BindView(R.id.act_grather_detials_xlv)
    XListView mActGratherDetialsXlv;
    @BindView(R.id.act_grather_detials_iv_pl)
    ImageView mActGratherDetialsIvPl;
    private IGratherDetailsContract.IGratherDetailsPresenter presenter;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Grather grather;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_grather_detials);
        ButterKnife.bind(this);
        new GratherDetailsPresenterImpl(this);
        initData();
    }

    /**
     * 初始化控件数据
     */
    private void initData() {
        //从内存中获取选中的item项，并删除
        grather = (Grather) MyApplitation.getDatas("selectItemData", true);
        //显示数据
        mActGratherDetialsTvGratherTitle.setText(grather.getGratherName());
        mActGratherDetialsTvGratherContent.setText(grather.getGratherContent());
        mActGratherDetialsTvLocation.setText(grather.getAddress());
        mActGratherDetialsTvMoney.setText(grather.getGratherMoney() + "");
        mActGratherDetialsTvTime.setText(grather.getGraterDataTime());
        mActGratherDetialsTvType.setText(grather.getGratherType());
        //获取图片
        List<BmobFile> imgFiles = grather.getGratherImageFiles();
        //显示图片
        presenter.setImages(imgFiles, mActGratherDetialsGv);
        //获取评论,需要查询，然后显示
        presenter.showDisscuss(grather.getDiscussIds(), mActGratherDetialsXlv);
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.toastShort(msg);
    }

    @Override
    public void showLoading(String title, String msg, boolean flag) {
        super.showProcessDialog(title,msg,flag);
    }

    @Override
    public void hiddenLoading() {
        super.dismissProcessDialog();
    }

    @Override
    public void setPresenter(IGratherDetailsContract.IGratherDetailsPresenter presenter) {
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

    @Override
    public void setData(Object object) {

    }

    @Override
    public void getData() {

    }

    @OnClick({R.id.frag_message_ib_back, R.id.frag_person_ib_menu, R.id.act_grather_detials_btn_join, R.id.act_grather_detials_iv_pl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_message_ib_back://返回
                finish();
                break;
            case R.id.frag_person_ib_menu://菜单
                break;
            case R.id.act_grather_detials_btn_join://参加活动
                break;
            case R.id.act_grather_detials_iv_pl://评论
                builder = super.showAlertDialog(null, null, true);
                View v = LayoutInflater.from(this).inflate(R.layout.write_dicuss, null);
                builder.setView(v);
                alertDialog = builder.show();
                //获取控件
                final EditText mDiscussEr = (EditText) v.findViewById(R.id.write_et_content);
                Button mSendBtn = (Button) v.findViewById(R.id.write_btn_send);
                //给按钮设置点击事件
                mSendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //隐藏评论框
                        GratherDetailsActivity.this.dismissAlertDialog(alertDialog);
                        String text = mDiscussEr.getText().toString();
                       LogUtils.i("我点击了发送评论的按钮，文本内容为"+text);
                        presenter.sendDiscuss(grather.getObjectId(),text,MyApplitation.user.getObjectId());
                    }
                });
                break;
        }
    }
}
