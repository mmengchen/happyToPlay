package com.xiaoguang.happytoplay.presenter;

import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.contract.ILoginContract;
import com.xiaoguang.happytoplay.model.LoginModelImpl;
import com.xiaoguang.happytoplay.utils.LogUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * 登陆处理的Presenter
 * Created by 11655 on 2016/9/27.
 */

public class LoginPresenterImpl implements ILoginContract.ILoginPresenter {

    private ILoginContract.ILoginView view;
    private LoginModelImpl loginModel;

    public LoginPresenterImpl(ILoginContract.ILoginView view) {
        this.view = view;
        view.setPresenter(this);
        loginModel = new LoginModelImpl();
    }

    @Override
    public void login(Object object) {
        view.showLoading();
        //获取user对象
        User user = (User) object;
        //登陆
        loginModel.login(user, new LoginModelImpl.ILoginCallBack() {
            @Override
            public void onSuccess(BmobUser user) {
                view.showMsg("登陆成功");
                LogUtils.i("登陆成功");
                view.jumpActivity();
                view.hiddenLoading();
            }

            @Override
            public void onError(BmobException e) {
                view.showMsg("登陆失败");
                view.hiddenLoading();
                LogUtils.i("登陆失败" + e);
                view.clean();
            }
        });
    }

    @Override
    public void start() {

    }
}
