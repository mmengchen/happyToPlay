package com.xiaoguang.happytoplay.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiaoguang.happytoplay.utils.LogUtils;

import cn.bmob.push.PushConstants;

/**
 * 推送消息的广播接收者
 * Created by 11655 on 2016/10/9.
 */

public class PushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            LogUtils.i("客户端收到推送内容："+intent.getStringExtra("msg"));
        }
    }
}
