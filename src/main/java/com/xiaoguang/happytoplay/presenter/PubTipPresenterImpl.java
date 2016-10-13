package com.xiaoguang.happytoplay.presenter;

import android.graphics.Bitmap;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.xiaoguang.happytoplay.contract.IPublishTipContract;
import com.xiaoguang.happytoplay.utils.ImageUtils;

import static com.xiaoguang.happytoplay.application.MyApplitation.context;

/**
 * 活动提示的界面
 * Created by 11655 on 2016/10/13.
 */

public class PubTipPresenterImpl implements IPublishTipContract.IPubTipPresenter {
    IPublishTipContract.IPubTipView view;
    //微信分享的APP_ID
    private static final String APP_ID = "wxbedb546e61fa2cfd";
    private IWXAPI api;

    public PubTipPresenterImpl(IPublishTipContract.IPubTipView view) {
        this.view = view;//通过工厂类，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, APP_ID, false);
        //将应用APP_ID注册到微信
        api.registerApp(APP_ID);
        view.setPresenter(this);
    }

    @Override
    public void loadingData(Object o) {

    }

    @Override
    public void shareWXAPP(int i, Object data) {
//        //初始化WXTextObject 对象
//        WXTextObject textObject = new WXTextObject();
//        //设置文本分享内容
//        textObject.text = "乐玩旅行";
//
//        //初始化WXMdeiaMessage对象
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObject;
//        msg.description = "乐玩旅行APP_测试";
//
//        //构造一个Request
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        //设置唯一标识
//        req.transaction = System.currentTimeMillis() + "";
//        req.message = msg;
//        //发送到好友列表
//        req.scene = i;
//        //调用api接口发送数据到微信
//        api.sendReq(req);
        //存在url获取的问题和数据问题
        String url = "http://www.baidu.com";//收到分享的好友点击信息会跳转到这个地址去
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = url;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
        localWXMediaMessage.title = "乐玩APP功能测试";//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
        localWXMediaMessage.description = "分享功能测试";
        Bitmap bitmap =(Bitmap)data;
        localWXMediaMessage.thumbData = ImageUtils.getBitmapBytes(bitmap, false);
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene = i;
        api.sendReq(localReq);
    }


    @Override
    public void start() {

    }
}
