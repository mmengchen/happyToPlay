package com.xiaoguang.happytoplay.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import c.b.BP;
import c.b.PListener;

/**
 * 支付功能的Activity，暂时用于支付测试,存在没有自动安装支付插件的功能
 */
public class PayActivity extends AppCompatActivity {

    @BindView(R.id.act_pay_tv_money)
    TextView mActPayTvMoney;
    @BindView(R.id.act_pay_btn_weixin)
    Button mActPayBtnWeixin;
    @BindView(R.id.act_pay_alipay)
    Button mActPayAlipay;
    // 此为支付插件的官方最新版本号,请在更新时留意更新说明
    private int PLUGINVERSION = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay);
        ButterKnife.bind(this);
        //获取当前插件版本
        int pluginVersion = BP.getPluginVersion();
        if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件, 否则就是支付插件的版本低于官方最新版
            Toast.makeText(
                    this,
                    pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                            : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)", Toast.LENGTH_SHORT).show();
            installBmobPayPlugin("bp.db");
        }

    }
    @OnClick({R.id.act_pay_btn_weixin, R.id.act_pay_alipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_pay_btn_weixin:
                mActPayTvMoney.getText();
                //false 为为微信
                pay(false);
                break;
            case R.id.act_pay_alipay:
                mActPayTvMoney.getText();
                //true 为支付宝
                pay(true);
                break;
        }
    }

    /**
     * 支付的方法
     * @param alipayOrWechatPay
     */
    void pay(final boolean alipayOrWechatPay){
        BP.pay("乐玩旅行_支付数据测试", "乐玩旅行_数据测试", 0.02, alipayOrWechatPay, new PListener() {
            @Override
            public void orderId(String s) {
                LogUtils.i("订单编号"+s);
            }

            @Override
            public void succeed() {
                LogUtils.i("支付成功");
            }

            @Override
            public void fail(int code, String reason) {
                LogUtils.i("支付失败");

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
//                    Toast.makeText(
//                            Act.this,
//                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
//                            0).show();
                    LogUtils.i("监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付");
                    installBmobPayPlugin("bp.db");
                } else {
//                    Toast.makeText(Act.this, "支付中断!", Toast.LENGTH_SHORT)
//                            .show();
                    LogUtils.i("支付中断!");
                }
            }

            @Override
            public void unknow() {

            }
        });
    }
    // 执行订单查询
//    void query() {
//        LogUtils.i("正在查询订单");
//        final String orderId = getOrder();
//
//        BP.query(orderId, new QListener() {
//
//            @Override
//            public void succeed(String status) {
//                Toast.makeText(Act.this, "查询成功!该订单状态为 : " + status,
//                        Toast.LENGTH_SHORT).show();
//                tv.append("pay status of" + orderId + " is " + status + "\n\n");
////                hideDialog();
//            }

//            @Override
//            public void fail(int code, String reson) {
//                Toast.makeText(PayActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
//                tv.append("query order fail, error code is " + code
//                        + " ,reason is \n" + reason + "\n\n");
////                hideDialog();
//            }
//        });
//    }
    /**
     * 安装支付插件的方法
     *
     * @param fileName
     */
    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
