package com.mob.demo.mobpushSample;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.mob.MobApplication;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;


public class DemoApplication extends MobApplication {

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        MobPush.addPushReceiver(new MobPushReceiver() {
            @Override
            public void onCustomMessageReceive(Context context, MobPushCustomMessage message) {
                //接收自定义消息(透传)
                System.out.println("onCustomMessageReceive:" + message.toString());
            }

            @Override
            public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
                //接收通知消息
                System.out.println("MobPush onNotifyMessageReceive:" + message.toString());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "Message Receive:" + message.toString();
                handler.sendMessage(msg);

            }

            @Override
            public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage message) {
                //接收通知消息被点击事件
                System.out.println("MobPush onNotifyMessageOpenedReceive:" + message.toString());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "Click Message:" + message.toString();
                handler.sendMessage(msg);
            }

            @Override
            public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
                //接收tags的增改删查操作
                System.out.println("onTagsCallback:" + operation + "  " + errorCode);
            }

            @Override
            public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
                //接收alias的增改删查操作
                System.out.println("onAliasCallback:" + alias + "  " + operation + "  " + errorCode);
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    //当其它dialog未关闭的时候，再次显示dialog，会造成其他dialog无法dismiss的现象，建议使用toast
//                    if (PushDeviceHelper.getInstance().isNotificationEnabled()) {
//                        Toast.makeText(MainActivity.this, "回调信息\n" + (String) msg.OBJ, Toast.LENGTH_SHORT).show();
//                    } else {//当做比通知栏后，toast是无法显示的
//                        new DialogShell(MainActivity.this).autoDismissDialog(0, "回调信息\n" + (String) msg.OBJ, 2);
//                    }
                    System.out.println("Callback Data:" + msg.obj);
                }
                return false;
            }
        });

    }
}
