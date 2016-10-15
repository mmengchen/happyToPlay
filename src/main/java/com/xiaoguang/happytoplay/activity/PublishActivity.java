package com.xiaoguang.happytoplay.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.contract.IPubContract;
import com.xiaoguang.happytoplay.presenter.PublishPresenterImpl;
import com.xiaoguang.happytoplay.utils.ImageChooseUtils;
import com.xiaoguang.happytoplay.utils.LogUtils;
import com.xiaoguang.happytoplay.utils.ToastUtils;
import com.xiaoguang.happytoplay.view.BottomPopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

import static com.xiaoguang.happytoplay.fragment.PersonFragment.CROP_PHOTO;
import static com.xiaoguang.happytoplay.fragment.PersonFragment.GET_PHOTO;
import static com.xiaoguang.happytoplay.fragment.PersonFragment.TAKE_PHOTO;

/**
 * 发布活动的Activity
 * Created by 11655 on 2016/9/30.
 */

public class PublishActivity extends BaseActivity implements IPubContract.IPubView {

    //获取控件
    @BindView(R.id.act_publish_ib_back)
    ImageButton mIbBack;
    @BindView(R.id.act_publish_ib_menu)
    ImageButton mIbMenu;
    @BindView(R.id.act_pub_et_act_name)
    EditText mEtActName;
    @BindView(R.id.act_pub_tv_time)
    TextView mTvTime;
    @BindView(R.id.act_pub_sp)
    Spinner mSp;
    @BindView(R.id.act_pub_et_content)
    EditText mEtContent;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.act_pub_gv)
    GridView mGv;
    @BindView(R.id.act_pub_tv_location)
    TextView mTvLocation;
    @BindView(R.id.act_pub_et_money)
    EditText mEtMoney;
    @BindView(R.id.act_pub_btn_submit)
    Button mBtnSubmit;
    private IPubContract.IPubPresenter presenter;
    //定义下弹对话框
    private BottomPopView bottomPopView;
    //请求百度地图的请求码
    public static final int REQUEST_MAP_CODE = 5;
    //获取图片的Uri
    private Uri imageUri = ImageChooseUtils.getImageUri();;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish);
        ButterKnife.bind(this);
        new PublishPresenterImpl(this);
        //设置在activity启动的时候输入法默认是不开启的
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //调用p层的loadData方法进行设置中间的和GradView添加适配器操作
        presenter.loadingData(mSp,mGv);

    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.toastShort(msg);
    }

    @Override
    public void showLoading(String title, String msg, boolean flag) {
        super.showProcessDialog(title, msg, flag);
    }

    @Override
    public void hiddenLoading() {
        super.dismissProcessDialog();
    }

    @Override
    public void setPresenter(IPubContract.IPubPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {
        //发布成功后跳转到提示界面
        startActivity(new Intent(this,PublishTipActivity.class));
        //结束掉自己
        finish();
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
        //设置时间
        mTvTime.setText((String)object);
    }

    @Override
    public Grather getData() {
        //实例化Grather对象
        Grather grather = new Grather();
        //设置数据（暂未进行非空认证）
        grather.setGratherName(mEtActName.getText().toString());//设置活动标题
        grather.setGratherType(mSp.getSelectedItem().toString());//设置活动类型
        grather.setGratherContent(mEtContent.getText().toString());//设置活动内容
        grather.setGraterDataTime(mTvTime.getText().toString());//设置活动时间
        PoiInfo poiInfo =(PoiInfo)MyApplitation.getDatas("location",false);
        grather.setGratherGeoPoint(new BmobGeoPoint(poiInfo.location.longitude,poiInfo.location.latitude));//设置活动经纬度
        grather.setAddress(poiInfo.address);//设置活动地址
        grather.setGratherMoney(Float.parseFloat(mEtMoney.getText().toString()));//设置活动金额
        grather.setGratherOriginator(BmobUser.getCurrentUser(User.class).getObjectId());//设置活动发起人
        return grather;
    }

    public void onCreateBottomMenu(View v) {
        //底部弹出的布局 照相和选择图片
        bottomPopView = new BottomPopView(this,v ) {
            @Override
            public void onTopButtonClick() {
                //拍照
                Intent intent = ImageChooseUtils.takePhoto();
                startActivityForResult(intent,TAKE_PHOTO);
                LogUtils.i("我在点击拍照");
                //点击之后隐藏
                bottomPopView.dismiss();
            }

            @Override
            public void onBottomButtonClick() {
                //选择本地图片
                LogUtils.i("我在点击照片");
                Intent intent = ImageChooseUtils.choosePhoto();
                startActivityForResult(intent,GET_PHOTO);
                //点击之后隐藏
                bottomPopView.dismiss();
            }
        };
        bottomPopView.setTopText("拍照");
        bottomPopView.setBottomText("选择图片");
        // 显示底部菜单
        bottomPopView.show();
    }

    /**
     * 按钮的点击事件
     * @param view
     */
    @OnClick({R.id.act_publish_ib_back, R.id.act_publish_ib_menu,R.id.act_pub_tv_time, R.id.act_pub_btn_submit,R.id.act_pub_tv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_publish_ib_back:
                isStopPublish();
                break;
            case R.id.act_publish_ib_menu:
                break;
            case R.id.act_pub_btn_submit:
                //调用P层进行 发布活动
                presenter.submit(getData());
                break;
            case R.id.act_pub_tv_time:
                presenter.chooseTime(this);
                break;
            case R.id.act_pub_tv_location:
                //跳转到地图
                startActivityForResult(new Intent(PublishActivity.this,MapActivity.class),REQUEST_MAP_CODE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO://拍照返回
                LogUtils.i("我是从系统相机返回的");
                if (resultCode == RESULT_OK) {
                    //跳转到裁剪页面
                    jumpCropActivity();
                }
                break;
            case GET_PHOTO://获取图片返回
                //调用P层设置图标
                presenter.setIcoHeader(MyApplitation.context, data);
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {//从系统相册/裁剪之后返回
                    LogUtils.i("我是裁剪相册返回的");
                    presenter.setIconHeader();
                }
                break;
            case REQUEST_MAP_CODE://进行百度地图回调处理
                //获取内存中的位置信息
                String address = ((PoiInfo)MyApplitation.getDatas("location",false)).address;
                //显示到控件上
                mTvLocation.setText(address);
                break;
            default:
                break;
        }
    }
    /**
     * 跳转到裁剪的页面
     */
    private void jumpCropActivity() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);
    }

    /**
     * 返回按钮，是否取消发布
     */
    private void isStopPublish() {
        final AlertDialog.Builder builder = showAlertDialog("提示","是否取消发布活动",false);
        builder.setNegativeButton("我要取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setPositiveButton("不，我要继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消提示框
                PublishActivity.super.dismissAlertDialog(alertDialog);
            }
        });
        alertDialog = builder.show();
    }

}
