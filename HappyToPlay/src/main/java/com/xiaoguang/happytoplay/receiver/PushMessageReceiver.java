package com.xiaoguang.happytoplay.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * 接收推送信息
 * Created by 11655 on 2016/10/17.
 */

public class PushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            LogUtils.i("bmob", "BmobPushDemo收到消息：" + intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
            //定义接收到的字符串
            String receivedStr = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            String msg = "";
            //解析接收到的数据
            try {
                JSONObject object = new JSONObject(receivedStr);
                msg = object.getString("alert");
            } catch (JSONException e) {
                msg = e.getLocalizedMessage();
            }
            //构造一个通知栏构建者对象
            Notification.Builder builder = new Notification.Builder(context);
            // 通知栏
            builder.setSmallIcon(R.drawable.app_icon)// 设置消息的图标
                    .setTicker("您有一条新的消息")// //设置消息在Activity显示的标记(标题)
                    .setContentTitle("乐玩旅行")// 设置消息的标题
                    .setContentText(msg)// 设置消息的内容
                    .setWhen(System.currentTimeMillis())// 设置系统时间
                    // 震动,声音,闪光灯....
                    .setDefaults(Notification.DEFAULT_ALL);
            // 未来意图
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), // 上下文
                    1, // 请求码
                    intent, // 意图
                    0//
            );
            // 当用户点击消息时,需要触发未来意图
            builder.setContentIntent(pendingIntent);
            builder.setFullScreenIntent(pendingIntent, true);// 悬挂
            Notification notification = builder.build();
            // 设置消息的清除方式
            notification.flags = Notification.FLAG_AUTO_CANCEL;// 不能清除
            // 获取通知栏管理器
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            /**
             * 当前通知的唯一标识 为了更好的管理通知
             */
            manager.notify(1, notification);
        }
    }
}
