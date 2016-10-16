package com.xiaoguang.happytoplay.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
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
import cn.bmob.v3.listener.UpdateListener;

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
    //头像默认的Url
    private String URI_DEFAULT = "http://bmob-cdn-6590.b0.upaiyun.com/2016/10/16/f8bd4e9c40174c49805921fbe68b6745.png";

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
        //设置点击事件的监听
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        FragPersonPresenterImpl.this.view.jumpActivity(0);
                        break;
                    case 1:
                        FragPersonPresenterImpl.this.view.jumpActivity(1);
                        break;
                    case 2:
                        FragPersonPresenterImpl.this.view.jumpActivity(2);
                        break;
                    case 3:
                        FragPersonPresenterImpl.this.view.jumpActivity(3);
                        break;
                    case 4://暂时跳转支付界面，用于进行支付测试
                        FragPersonPresenterImpl.this.view.jumpActivity(4);
                        break;
                    case 5://跳转到个人信息设置
                        FragPersonPresenterImpl.this.view.jumpActivity(5);
                        break;
                }
            }
        });
    }

    @Override
    public void search(String searchText) {

    }

    /**
     * 从图库中选择（存在相关问题）
     *
     * @param context 上下文
     * @param data    从系统图库返回的Intent
     */
    @Override
    public void setIcoHeader(Context context, Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        //2016.10.5更新操作
        //将图片资源上传到服务器
        User user = new User();
        user.setUserHead(new BmobFile(new File(picturePath)));
        upload(user, picturePath);
        //加载进度条
        view.showLoading();
    }

    @Override
    public void setIconHeader() {
        //显示一个对话框
        view.showLoading();
        //声明并实例化BmobFile对象
        BmobFile bmobFile = new BmobFile(new File(Environment.getExternalStorageDirectory() + "/icoImage.jpg"));
        User newUser = new User();
        newUser.setUserHead(bmobFile);
        //获取当前图片uri
        String uri = bmobFile.getFileUrl();
        //上传图片
        upload(newUser, uri);
    }

    /**
     * 将头像资源上传到服务器
     *
     * @param user
     */
    private void upload(final User user, final String uri) {
        LogUtils.i("我正在执行文件上传");
        //调用model层的上传文件的方法
        icoModel.upload(user, new IcoModel.UploadCallBack() {
            @Override
            public void success(final String uri) {
                LogUtils.i("头像上传成功");
                view.showMsg("头像上传成功");
                icoModel.update(BmobUser.getCurrentUser(User.class).getObjectId(), user, new IcoModel.UpdateCallBack() {
                    @Override
                    public void success() {
                        LogUtils.i("更新信息成功");
                        //进行图片的显示
                        view.displayImage(uri);
                        //隐藏加载框
                        view.hiddenLoading();
                        view.showMsg("更新信息成功");
                    }

                    @Override
                    public void error(BmobException e) {
                        LogUtils.i("更新信息失败" + e.toString());
                        view.showMsg("更新信息失败" + e.toString());
                        view.hiddenLoading();
                        //显示默认头像,将文件删除
                        view.displayImage(URI_DEFAULT);
                        BmobFile file = new BmobFile();
                        file.setUrl(uri);//此url是上传文件成功之后通过bmobFile.getUrl()方法获取的。
                        file.delete(new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    LogUtils.i("文件删除成功");
                                }else{
                                    LogUtils.i("文件删除失败"+e.toString());
                                }
                            }
                        });
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
        view.showLoading("加载头像中....");
        LogUtils.i("myUtils-------开始加载头像");
        //获取当前在线的用户信息
        User currentUser = BmobUser.getCurrentUser(User.class);
        //判断当前头像是否为空
        if (currentUser.getUserHead().getUrl()!= null) {
            //获取当前头像的uri
            String uri = currentUser.getUserHead().getFileUrl();
            //展示图片
            LogUtils.i("我是在本地的文件的url为" + uri);
            view.displayImage(uri);
            //取消显示框
            view.hiddenLoading();
            return;
        }
        BmobQuery<User> query = new BmobQuery<User>();
        //从服务器获取user 对象 根据Id
        query.getObject(currentUser.getObjectId(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {//查询成功
                    //隐藏
                    view.hiddenLoading();
                    view.showMsg("刷新数据成功！");
                    //获取文件的url
                    String uri = "";
                    if (user.getUserHead().getUrl()==null){//如果不存在头像，则显示默认头像
                        uri = URI_DEFAULT;
                    }else {
                        uri = user.getUserHead().getUrl();
                    }
                    //展示图片
                    LogUtils.i("文件的url为" + uri);
                    view.displayImage(uri);

                } else {//查询失败
                    view.showMsg("刷新数据失败！" + e.toString());
                    LogUtils.i("刷新数据失败" + e.toString());
                    view.displayImage(URI_DEFAULT);
                    view.hiddenLoading();
                    //显示默认头像
                    view.displayImage(URI_DEFAULT);
                }
            }
        });
    }

    @Override
    public void start() {
        datas = new ArrayList<String>();
        datas.add("优 惠");
        datas.add("收 藏");
        datas.add("发 起");
        datas.add("订 单");
        datas.add("活 动");
        datas.add("设 置");
    }

}
