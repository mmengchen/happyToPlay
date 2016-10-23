package com.xiaoguang.happytoplay.model;

import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登陆的数据模型
 * Created by 11655 on 2016/9/27.
 */

public class LoginModelImpl implements IBaseModel {

    public void login(User user, final ILoginCallBack loginCallBack){
        user.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){//登陆成功
//                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
//                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                    User user  =BmobUser.getCurrentUser(User.class);
                    //将信息保存到本地
                    savaUserInfo(user);

                    loginCallBack.onSuccess(bmobUser);
                }else{
                    loginCallBack.onError(e);
                }
            }
        });
    }

    /**
     * 将登陆成功后的用户信息保存到本地
     * @param user 用户信息
     */
    private void savaUserInfo(User user) {
        //保存到缓存中
        MyApplitation.user = user;

//        //获取SharedPreferences 对象
//        SharedPreferences prefrences = MyApplitation.context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//        //获取编辑器对象
//        SharedPreferences.Editor editor = prefrences.edit();
//        editor.putBoolean("logined",true);
//        editor.putString("objectId",user.getObjectId());
//        editor.putString("userName",user.getUsername());
//        editor.putString("nickName",user.getNickName());
//        editor.commit();
        /*
            2016.10.5修改，由于BmobUser 提供将登陆后的信息自动保存的方法，本人将不再使用此方法，减少代码行数
         */
        //获取当前登陆的对象
//        User currentUser = BmobUser.getCurrentUser(User.class);
    }
    /**
     *    登陆回调接口及回调方法
     */public interface ILoginCallBack {
        void onSuccess(BmobUser user);
        void onError(BmobException e);
    }
}
