package com.xiaoguang.happytoplay.activity;

import android.content.Intent;
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
import com.xiaoguang.happytoplay.contract.ILoginContract;
import com.xiaoguang.happytoplay.presenter.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆的Activity
 * Created by 11655 on 2016/9/27.
 */

public class LoginActivity extends BaseActivity implements ILoginContract.ILoginView {

    @BindView(R.id.act_login_et_name)
    EditText mEtName;
    @BindView(R.id.act_login_et_pwd)
    EditText mEtPwd;
    @BindView(R.id.act_login_tv_forget)
    TextView mTvForget;
    @BindView(R.id.act_login_btn_login)
    Button mBtnLogin;
    @BindView(R.id.act_login_btn_reg)
    Button mBtnReg;
    private ILoginContract.ILoginPresenter presenter;
    private String phone;
    private String pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        new LoginPresenterImpl(this);
    }

    @Override
    public void getData() {
        phone = mEtName.getText().toString();
        pwd = mEtPwd.getText().toString();
    }

    @Override
    public void setData(Object obj) {

    }

    @Override
    public void setPresenter(ILoginContract.ILoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void jumpActivity() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void showLoading() {
        super.showProcessDialog(null, "登录中...", false);
    }

    @Override
    public void hiddenLoading() {
        super.dismissProcessDialog();
    }

    @Override
    public void showMsg(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clean() {
        //将密码置为空
        pwd = "";
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.act_login_tv_forget, R.id.act_login_btn_login, R.id.act_login_btn_reg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_login_tv_forget://忘记密码
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.act_login_btn_login: //登陆操作
                //调用Presenter的login方法执行登陆操作
                getData();
                if (!isDataEmpty()) {
                    User user = new User();
                    user.setUsername(phone);
                    user.setPassword(pwd);
                    presenter.login(user);
                }
                break;
            case R.id.act_login_btn_reg: //注册操作
                //跳转到注册界面进行处理
                startActivity(new Intent(this, RegisterActivity.class));
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
        } else if (pwd.isEmpty()) {
            showMsg("请输入密码！！！");
            return true;
        }
        return false;
    }
}
