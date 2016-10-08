package com.xiaoguang.happytoplay.model;

import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.utils.LogUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 对头像操作的类
 * Created by 11655 on 2016/9/29.
 */

public class IcoModel {
    /**
     * 上传用户头像的方法
     * @param user   需要上传的Uer用户
     * @param uploadCallBack  上传的回调方法
     */
    public void upload(final User user, final UploadCallBack uploadCallBack) {
        user.getUserHead().uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {//上传成功
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    String uri = user.getUserHead().getFileUrl();
                    LogUtils.i("上传成功");
                    uploadCallBack.success(uri);
                } else {//上传失败
                    LogUtils.i("上传失败");
                    uploadCallBack.error(e);
                }

            }
        });
    }

    /**
     * 更新用户信息
     * @param objectId 用户唯一标识字段用户ID
     * @param user  需要更新的用户
     * @param updateCallBack  更新的回调的方法
     */
    public void update(String objectId,User user, final UpdateCallBack updateCallBack){
        user.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){//更新用户信息成功
                    LogUtils.i("更新用户信息成功！");
                    updateCallBack.success();
                }
                else {
                    updateCallBack.error(e);
                    LogUtils.i("更新用户信息失败");
                }
            }
        });
    }
    public interface UploadCallBack {
        void success(String uri);
        void error(BmobException e);
    }
    public interface UpdateCallBack{
        void success();
        void error(BmobException e);
    }
}
