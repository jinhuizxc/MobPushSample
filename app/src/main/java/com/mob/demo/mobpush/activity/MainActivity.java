package com.mob.demo.mobpush.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mob.demo.mobpush.PageAppNotify;
import com.mob.demo.mobpush.PageLocal;
import com.mob.demo.mobpush.PageNotify;
import com.mob.demo.mobpush.PageOpenAct;
import com.mob.demo.mobpush.PageOpenUrl;
import com.mob.demo.mobpush.PageTiming;
import com.mob.demo.mobpush.R;
import com.mob.demo.mobpush.utils.NotificationUtils;
import com.mob.demo.mobpush.utils.PlayloadDelegate;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCallback;
import com.mylhyl.circledialog.CircleDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.llAppNotify).setOnClickListener(this);
		findViewById(R.id.llNotify).setOnClickListener(this);
		findViewById(R.id.llTiming).setOnClickListener(this);
		findViewById(R.id.llLocal).setOnClickListener(this);
		findViewById(R.id.llMedia).setOnClickListener(this);
		findViewById(R.id.llOpenAct).setOnClickListener(this);

        checkOpenNotification();

		//获取注册id
		MobPush.getRegistrationId(new MobPushCallback<String>() {
			@Override
			public void onCallback(String data) {
				System.out.println(Thread.currentThread().getId() + " RegistrationId:" + data);
			}
		});

		MobPush.setAlias("oppo");

		MobPush.addTags(new String[]{"push", "oppo"});

		if (Build.VERSION.SDK_INT >= 21) {
			MobPush.setNotifyIcon(R.mipmap.default_ic_launcher);
		} else {
			MobPush.setNotifyIcon(R.mipmap.ic_launcher);
		}

		dealPushResponse(getIntent());
	}

    private void checkOpenNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!NotificationUtils.isNotificationEnabled(this)) {
                new CircleDialog.Builder()
                        .setTitle("您还未开启系统通知，可能会影响消息的接收，要去开启吗？")
                        .setTitleColor(getResources().getColor(R.color.color_black))
                        .setWidth(0.8f)

                        .setCancelable(false)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 跳转权限设置
                                NotificationUtils.gotoSet(MainActivity.this);
                            }
                        })
                        .setNegative("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show(getSupportFragmentManager());
            } else {
            }
        }
    }

    @Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		//需要调用setIntent方法，不然后面获取到的getIntent都试上一次传的数据
		setIntent(intent);
		dealPushResponse(intent);
	}

	private void dealPushResponse(Intent intent) {
		Bundle bundle = null;
		if (intent != null) {
			bundle = intent.getExtras();
			new PlayloadDelegate().playload(this, bundle);
		}
	}

	public void onClick(View v) {
		int vId = v.getId();
		switch (vId) {
			case R.id.llAppNotify: {
				new PageAppNotify().show(this, null);
			}
			break;

			case R.id.llNotify: {
				new PageNotify().show(this, null);
			}
			break;

			case R.id.llTiming: {
				new PageTiming().show(this, null);
			}
			break;

			case R.id.llLocal: {
				new PageLocal().show(this, null);
			}
			break;

			case R.id.llMedia: {
				new PageOpenUrl().show(this, null);
			}
			break;

			case R.id.llOpenAct: {
				new PageOpenAct().show(this, null);
			}
			break;
		}
	}
}
