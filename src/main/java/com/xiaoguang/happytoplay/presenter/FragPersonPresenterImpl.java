package com.xiaoguang.happytoplay.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.GridView;

import com.xiaoguang.happytoplay.adapter.FragPersonGridViewAdapter;
import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.contract.IFragPersonContract;
import com.xiaoguang.happytoplay.model.IcoModel;
import com.xiaoguang.happytoplay.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 个人中心操作相关
 * Created by 11655 on 2016/9/28.
 */

public class FragPersonPresenterImpl implements IFragPersonContract.IFragPersonPresenter {
    private IFragPersonContract.IFragPersonView view;
    //创建一个gradview 控件
    private GridView gv;
    //定一个数据源
    private List<String> datas;
    private IcoModel icoModel;

    //提供一个构造器
    public FragPersonPresenterImpl(IFragPersonContract.IFragPersonView view) {
        this.view = view;
        view.setPresenter(this);
        icoModel = new IcoModel();
        //进行数据的初始化操作
        start();
    }

    @Override
    public void loadingData(Object... o) {
        gv = (GridView) o[0];
        if (gv != null) {
            LogUtils.i("我正在加载数据" + gv);
        } else {
            LogUtils.i("我在加载数据 ,gv 为空");
        }
        gv.setAdapter(new FragPersonGridViewAdapter(datas));
    }

    @Override
    public void search(String searchText) {

    }

    @Override
    public void setIcoHeader(Context context, Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        Bitmap bimap = BitmapFactory.decodeFile(picturePath);
        //从本地获取用户信息
//        User user = BmobUser.getCurrentUser(User.class);
//        //为用户设置头像资源
//        user.setUserHead(new BmobFile(new File(URI.create(picturePath))));//存在问题，无法解析资源路径
        //将头像资源上传到服务器
//        upload(user);
        //加载进度条
        view.showLoading();
    }

    @Override
    public void setIconHeader() {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/icoImage.jpg");
        //为图片按钮设置照片
        User user = BmobUser.getCurrentUser(User.class);
        user.setUserHead(new BmobFile(new File(Environment.getExternalStorageDirectory() + "/icoImage.jpg")));
        view.showLoading();
        upload(user,bitmap);
        view.setIcoHeader(bitmap);
    }

    /**
     * 将头像资源上传到服务器
     *
     * @param user
     */
    private void upload(final User user, final Bitmap bitmap) {
        LogUtils.i("我正在执行文件上传");
        //调用model层的上传文件的方法  方法
        icoModel.upload(user, new IcoModel.UploadCallBack() {
            @Override
            public void success() {
                LogUtils.i("头像上传成功");
                view.showMsg("头像上传成功");
                icoModel.update(user, new IcoModel.UpdateCallBack() {
                    @Override
                    public void success() {
                        LogUtils.i("更新信息成功");
                        view.showMsg("更新信息成功");
                        //隐藏加载框
                        view.hiddenLoading();
                    }

                    @Override
                    public void error(BmobException e) {
                        LogUtils.i("更新信息失败"+e.toString());
                        view.showMsg("更新信息失败"+e.toString());
                        view.hiddenLoading();
                        view.setIcoHeader(bitmap);
                    }
                });
            }

            @Override
            public void error(BmobException e) {
                LogUtils.i("头像上传失败" + e.toString());
                view.hiddenLoading();
                view.showMsg("头像上传失败" + e.toString());
            }
        });
    }

    /**
     * 加载头像
     */
    @Override
    public void loadHeader() {
        //弹出加载框
        view.showLoading();
        //获取当前在线的用户信息
        User currentUser = BmobUser.getCurrentUser(User.class);
        BmobQuery<User> query = new BmobQuery<User>();
        //从服务器获取user 对象 根据Id
        query.getObject(currentUser.getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){//查询成功
                    //隐藏
                    view.hiddenLoading();
                    view.showMsg("刷新数据成功！");
                    //暂时只更新头像操作，不更新其他数据//暂未判断是否为空
                    BmobFile file =user.getUserHead();
                    //获取文件的url
                    String uri = file.getFileUrl();
                    //展示图片
                    LogUtils.i("文件的url为"+uri);
                    view.displayImage(uri);

                }else{//查询失败
                    view.showMsg("刷新数据失败！");
                }
            }
        });
    }

    @Override
    public void start() {
        datas = new ArrayList<String>();
        //模拟数据
        for (int i = 0; i < 6; i++) {
            datas.add("模拟的数据" + i);
        }
    }

}
