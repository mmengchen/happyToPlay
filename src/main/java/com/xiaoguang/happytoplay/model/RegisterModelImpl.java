package com.xiaoguang.happytoplay.model;

import com.xiaoguang.happytoplay.bean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 11655 on 2016/9/27.
 */

public class RegisterModelImpl implements IBaseModel {
    public void register(User user, final RegisterCallBack callback){
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User userBean, BmobException e) {
                if(e==null){
                    //注册成功
                    callback.onSuccess();
                }else{
                    //注册失败
                    callback.onError(e);
                }
            }
        });
    }
   public interface RegisterCallBack{
        void onSuccess();
        void onError(BmobException e);
    }
}
