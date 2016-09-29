package com.xiaoguang.happytoplay.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.base.BaseFragment;
import com.xiaoguang.happytoplay.contract.IFragPersonContract;
import com.xiaoguang.happytoplay.presenter.FragPersonPresenterImpl;
import com.xiaoguang.happytoplay.utils.ImageLoaderutils;
import com.xiaoguang.happytoplay.utils.LogUtils;
import com.xiaoguang.happytoplay.utils.ToastUtils;
import com.xiaoguang.happytoplay.view.BottomPopView;
import com.xiaoguang.happytoplay.view.CircleImageView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 11655 on 2016/9/28.
 */

public class PersonFragment extends BaseFragment implements IFragPersonContract.IFragPersonView {
    /**
     * 获取控件
     */
    @BindView(R.id.frag_person_ib_back)
    ImageButton mFragPersonIbBack;
    @BindView(R.id.frag_person_ib_menu)
    ImageButton mFragPersonIbMenu;
    @BindView(R.id.frag_person_circle_iv_head)
    CircleImageView mFragPersonIvHead;
    @BindView(R.id.frag_person_tv_nickname)
    TextView mFragPersonTvNickname;
    @BindView(R.id.frag_person_tv_phone)
    TextView mFragPersonTvPhone;
    @BindView(R.id.frag_person_tv_sex)
    TextView mFragPersonTvSex;
    @BindView(R.id.frag_person_tv_age)
    TextView mFragPersonTvAge;
    @BindView(R.id.frag_person_tv_location)
    TextView mFragPersonTvLocation;
    @BindView(R.id.frag_person_gv)
    GridView mFragPersonGv;
    @BindView(R.id.frag_person_v)
    View v;
    private IFragPersonContract.IFragPersonPresenter presenter;
    private BottomPopView bottomPopView;
    private Activity activtiy;

    //拍照
    public static final int TAKE_PHOTO = 1;

    //裁剪
    public static final int CROP_PHOTO = 2;

    //从图库获取
    public static final int GET_PHOTO = 3;
    private Uri imageUri;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activtiy = activity;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FragPersonPresenterImpl(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_person, null);
        ButterKnife.bind(this, view);
        //加载数据（不是很合理，会造成卡顿现象）
        setData();
        presenter.loadingData(mFragPersonGv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String msg) {
        super.showProcessDialog(null, msg+"...", true);
    }

    @Override
    public void hiddenLoading() {
        super.dismissProcessDialog();
    }

    @Override
    public void setPresenter(IFragPersonContract.IFragPersonPresenter presenter) {
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
        //显示数据到控件上
        mFragPersonTvNickname.setText(MyApplitation.user.getNickName());
        mFragPersonTvPhone.setText("账号：" + MyApplitation.user.getUsername());
        //以下数据暂时固定
        mFragPersonTvSex.setText("男");
        mFragPersonTvAge.setText("22岁");
        mFragPersonTvLocation.setText("北京昌平");
        //加载头像
        presenter.loadHeader();
    }

    @Override
    public void getData() {

    }

    //点击头像
    @OnClick({R.id.frag_person_circle_iv_head})
    public void onClick(View view) {
        LogUtils.i("我被点击了呢");
        //显示弹出式对话框
        onCreatePopMenu(v);
    }

    /**
     * 弹出式菜单
     */
    protected void onCreatePopMenu(final View view) {
        //底部弹出的布局 照相和选择图片
        bottomPopView = new BottomPopView(activtiy, view) {
            @Override
            public void onTopButtonClick() {
                //拍照
                takePhoto();
                LogUtils.i("我在点击拍照");
                //点击之后隐藏
                bottomPopView.dismiss();
            }

            @Override
            public void onBottomButtonClick() {
                //选择本地图片
                LogUtils.i("我在点击照片");
                //点击之后隐藏
                bottomPopView.dismiss();
                choosePhoto();
            }
        };
        bottomPopView.setTopText("拍照");
        bottomPopView.setBottomText("选择图片");
        // 显示底部菜单
        bottomPopView.show();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        //初始化图片的Uri
        imageUri = getImageUri();
        if (isHasSdcard()) {//判断SD卡的状态
            Intent takePhotoIntent = new Intent(
                    "android.media.action.IMAGE_CAPTURE");
            takePhotoIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    imageUri);
            startActivityForResult(takePhotoIntent,
                    TAKE_PHOTO);
        } else {
            ToastUtils.toastShort("未检测到sd卡");
        }
    }

    /**
     * 从系统图库中选择照片
     */
    private void choosePhoto() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GET_PHOTO);
    }

    /**
     * 获取图片的路径
     */
    private Uri getImageUri() {
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "icoImage.jpg");
        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(outputImage);
    }

    ;

    /**
     * 判断sd卡是否存在
     */
    public static boolean isHasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    //获取图片  ,存在问题：1.拍照时无法进行图片的裁剪（不断加载）（解决）  2.从图库中找图片时，出现空指针异常(解决)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                presenter.setIcoHeader(getContext(), data);
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {//从系统相册/裁剪之后返回
                    LogUtils.i("我是裁剪相册返回的");
                    presenter.setIconHeader();
                }
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
     * 设置头像
     *
     * @param bitmap
     */
    @Override
    public void setIcoHeader(Bitmap bitmap) {
        mFragPersonIvHead.setImageBitmap(bitmap);
    }

    @Override
    public void displayImage(String uri) {
        ImageLoader loader = ImageLoaderutils.getInstance(MyApplitation.context);
        loader.displayImage(uri, mFragPersonIvHead);
    }
}
