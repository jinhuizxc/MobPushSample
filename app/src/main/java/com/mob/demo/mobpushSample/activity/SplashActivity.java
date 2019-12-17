package com.mob.demo.mobpushSample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.mob.demo.mobpushSample.R;
import com.mob.demo.mobpushSample.utils.PlayloadDelegate;


public class SplashActivity extends AppCompatActivity {

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Intent intent  = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_splash);
		handler.sendEmptyMessageDelayed(0, 3000);

		dealPushResponse(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		dealPushResponse(getIntent());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeMessages(0);
	}

	private void dealPushResponse(Intent intent) {
		Bundle bundle = null;
		if (intent != null) {
			bundle = intent.getExtras();
			new PlayloadDelegate().playload(this, bundle);
		}
	}
}
