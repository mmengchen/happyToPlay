package com.xiaoguang.happytoplay.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.contract.IRegContract;
import com.xiaoguang.happytoplay.presenter.RegPresenterImpl;
import com.xiaoguang.happytoplay.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册界面：注册用户和验证码获取功能
 * Created by 11655 on 2016/9/27.
 */

public class RegisterActivity extends BaseActivity implements IRegContract.IRegView {
    /**
     * 初始化控件
     */
    @BindView(R.id.act_register_et_phone)
    EditText mEtPhone;
    @BindView(R.id.act_register_et_code)
    EditText mEtCode;
    @BindView(R.id.act_register_btn_getcode)
    Button mBtnGetcode;
    @BindView(R.id.act_register_et_nickname)
    EditText mEtNickname;
    @BindView(R.id.act_register_et_pwd)
    EditText mEtPwd;
    @BindView(R.id.act_register_et_pwd2)
    EditText mEtPwd2;
    @BindView(R.id.act_register_tv_xy)
    TextView mTvXy;
    @BindView(R.id.act_register_btn_reg)
    Button mBtnReg;
    @BindView(R.id.act_register_btn_login)
    Button mActRegisterBtnLogin;

    private IRegContract.IRegPresenter presenter;
    private String phone, code, pwd, pwd2, nickNmae;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        ButterKnife.bind(this);
        new RegPresenterImpl(this,this);
//        857643
    }

    @Override
    public void getData() {
        phone = mEtPhone.getText().toString().trim();
        code = mEtCode.getText().toString().trim();
        pwd = mEtPwd.getText().toString().trim();
        pwd2 = mEtPwd2.getText().toString().trim();
        nickNmae = mEtNickname.getText().toString().trim();
        LogUtils.i("myTag","获取到的信息为：phone:"+phone+",,,,code,"+code+",,,pwd:"+pwd+",,,,,,,,nickName:"+nickNmae);

    }

    /**
     * 实现倒计时功能，为按钮设置数据
     *
     * @param object
     */
    @Override
    public void setData(Object object) {
        mBtnGetcode.setText((String) object + "");
    }

    @Override
    public void clean() {
        code = "";
        pwd = "";
        pwd2 = "";
//        nickNmae = "";
    }

    @Override
    public void setPresenter(IRegContract.IRegPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {
        finish();
    }

    @Override
    public void showLoading() {
        super.showProcessDialog("注册中","努力注册中....",false);
    }

    @Override
    public void hiddenLoading() {
        super.dismissProcessDialog();
    }

    @Override
    public void showMsg(String str) {
        Toast.makeText(this, "" + str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 按钮的点击事件
     *
     * @param view
     */
    @OnClick({R.id.act_register_btn_getcode, R.id.act_register_tv_xy, R.id.act_register_btn_reg, R.id.act_register_btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_register_btn_getcode://获取验证码
                phone = mEtPhone.getText().toString();
                if (phone.isEmpty()) {
                    showMsg("请输入手机号码！！！");
                    return;
                }
                if (phone.length() != 11) {
                    showMsg("请输入有效的手机号码！！！");
                    return;
                }
                presenter.getCode(phone);
                break;
            case R.id.act_register_tv_xy:
                break;
            case R.id.act_register_btn_reg:
                //获取文本框中的数据
                getData();
                if (!isDataEmpty()) {
                    User user = new User();
                    user.setUsername(phone);
                    user.setMobilePhoneNumber(phone);
                    user.setPassword(pwd);
                    user.setNickName(mEtNickname.getText().toString());
                    //进行用户注册
                    presenter.register(user,code);
                }
                break;
            case R.id.act_register_btn_login:
                finish();
                break;
        }
    }

    /**
     * 判断数据是否为空
     *
     * @return true 数据存在空值 false  数据不为空
     */
    private boolean isDataEmpty() {
        if (phone.isEmpty()) {
            showMsg("请输入手机号码！！！");
            return true;
        } else if (phone.length() != 11) {
            showMsg("请输入11位有效的手机号码！！！");
            return true;
        } else if (code.isEmpty()) {
            showMsg("请输入验证码！！！");
            return true;
        } else if (pwd.isEmpty()) {
            showMsg("请输入密码！！！");
            return true;
        } else if (pwd2.isEmpty()) {
            showMsg("请重复输入密码！！！");
            return true;
        } else if (!pwd.equals(pwd2)) {
            showMsg("两次密码不一致！！！");
            return true;
        } else if (nickNmae.isEmpty()) {
            showMsg("请输入昵称！！！");
            return true;
        }
        return false;
    }
}
