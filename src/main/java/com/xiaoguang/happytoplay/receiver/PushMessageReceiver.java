package com.xiaoguang.happytoplay.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.push.PushConstants;

/**
 * 推送消息的广播接收者
 * Created by 11655 on 2016/10/9.
 */

public class PushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Log.d("bmob", "BmobPushDemo收到消息：" +
                    intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
            Toast.makeText(context, "BmobPushDemo收到消息：" +
                            intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
