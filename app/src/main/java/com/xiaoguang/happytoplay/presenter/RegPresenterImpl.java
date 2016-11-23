package com.xiaoguang.happytoplay.presenter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.contract.IRegContract;
import com.xiaoguang.happytoplay.model.RegisterModelImpl;
import com.xiaoguang.happytoplay.utils.LogUtils;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

import static com.xiaoguang.happytoplay.application.MyApplitation.context;

/**
 * Created by 11655 on 2016/9/27.
 */
public class RegPresenterImpl implements IRegContract.IRegPresenter {
    private IRegContract.IRegView view;
    private RegisterModelImpl registerModel;
    private Context context1;

    public RegPresenterImpl(IRegContract.IRegView view, Context context) {
        this.view = view;
        view.setPresenter(this);
        this.context1 = context;
        registerModel = new RegisterModelImpl();
    }

    @Override
    public void register(Object object, String code) {
        //显示加载框
        view.showLoading();
        LogUtils.i("myTag", "填写的验证码为" + code);
        final User user = (User) object;
        String phoneNumber = user.getMobilePhoneNumber();
        LogUtils.i("myTag", "我的数据" + user.toString());
        //进行验证码
        BmobSMS.verifySmsCode(context, user.getMobilePhoneNumber(), code, new VerifySMSCodeListener() {

            @Override
            public void done(BmobException ex) {
                if (ex == null) {//短信验证码已验证成功
                    Log.i("bmob", "验证通过");
                    view.showMsg("验证码验证成功");
                    user.setMobilePhoneNumberVerified(true);
                    registerModel.register(user, new RegisterModelImpl.RegisterCallBack() {
                        @Override
                        public void onSuccess() {
                            LogUtils.i("myTag", "注册成功！");
                            view.hiddenLoading();
                            //给用户提示
                            view.showMsg("注册成功！");
                            //跳转到登陆界面
                            view.jumpActivity();
                        }

                        @Override
                        public void onError(cn.bmob.v3.exception.BmobException e) {
                            LogUtils.i("myTag", "注册失败！" + e.toString());
                            view.hiddenLoading();
                            view.showMsg("注册失败");
                            view.clean();
                        }
                    });
                } else {//验证码未通过
                    Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                    view.hiddenLoading();
                    view.showMsg("验证码验证失败，请重新获取，code" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getCode(String phoneNumber) {
        LogUtils.i("myTag", "获取验证码！！！手机号码"+phoneNumber);
        //向服务器发送请求短信验证码
        BmobSMS.requestSMSCode(context, phoneNumber, "注册模板", new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {//验证码发送成功
                    LogUtils.i("myTag", "验证码发送成功+短信id：" + smsId);//用于查询本次短信发送详情
                    view.showMsg("验证码发送成功");
                } else {
                    LogUtils.i("myTag", "验证码发送失败" + ex.toString());
                    view.showMsg("验证码发送失败");
                }
            }
        });
        //开启倒计时
        startCountdown();
    }

    /**
     * 功能：开启一个倒计时
     */
    private void startCountdown() {
        //开启一个倒计时
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setData(millisUntilFinished / 1000 + "s");
                LogUtils.i("myTag", "" + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                view.setData("重新获取");
                LogUtils.i("myTag", "倒计时完成，重新获取验证码！");
            }
        };
        //开启倒计时
        timer.start();
    }
    @Override
    public void start() {

    }
}
