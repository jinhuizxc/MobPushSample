package com.mob.demo.mobpush;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.demo.mobpush.dialog.DialogShell;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushLocalNotification;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;

import java.util.Random;

public class PageLocal extends FakeActivity implements View.OnClickListener {
	private EditText etContent;
	private TextView tvChooseOne;
	private TextView tvChooseZero;
	private TextView tvChooseTwo;
	private TextView tvChooseThree;
	private TextView tvChooseFour;
	private TextView tvChooseFive;

	private int currentChooseTime = 1;

	protected int onSetTheme(int resid, boolean atLaunch) {
		return super.onSetTheme(android.R.style.Theme_Light_NoTitleBar, atLaunch);
	}

	public void onCreate() {
		super.onCreate();
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		activity.setContentView(R.layout.page_local);

		TextView tvTitle = findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.item_local_title);
		etContent = findViewById(R.id.etContent);

		tvChooseZero = findViewById(R.id.tvChooseZero);
		tvChooseOne = findViewById(R.id.tvChooseOne);
		tvChooseTwo = findViewById(R.id.tvChooseTwo);
		tvChooseThree = findViewById(R.id.tvChooseThree);
		tvChooseFour = findViewById(R.id.tvChooseFour);
		tvChooseFive = findViewById(R.id.tvChooseFive);
		tvChooseZero.setTag(0);
		tvChooseOne.setTag(1);
		tvChooseTwo.setTag(2);
		tvChooseThree.setTag(3);
		tvChooseFour.setTag(4);
		tvChooseFive.setTag(5);

		findViewById(R.id.ivBack).setOnClickListener(this);
		findViewById(R.id.btnTest).setOnClickListener(this);
		findViewById(R.id.tvChooseOne).setOnClickListener(this);
		findViewById(R.id.tvChooseTwo).setOnClickListener(this);
		findViewById(R.id.tvChooseThree).setOnClickListener(this);
		findViewById(R.id.tvChooseFour).setOnClickListener(this);
		findViewById(R.id.tvChooseFive).setOnClickListener(this);
		findViewById(R.id.tvChooseZero).setOnClickListener(this);
		tvChooseOne.performClick();
	}

	public void onClick(View v) {
		int vId = v.getId();
		switch (vId) {
			case R.id.ivBack: {
				finish();
			} break;
			case R.id.btnTest: {
				String content = etContent.getText().toString();
				if (TextUtils.isEmpty(content)) {
					Toast.makeText(getContext(), R.string.toast_input_not_allowed_null, Toast.LENGTH_SHORT).show();
					return;
				}
				MobPushLocalNotification notification = new MobPushLocalNotification();
				String appName = DeviceHelper.getInstance(getContext()).getAppName();
				notification.setTitle(TextUtils.isEmpty(appName) ? getContext().getString(R.string.item_local) : appName);
				notification.setContent(content);
//				notification.setVoice(false);//可设置不进行声音提醒，默认声音、振动、指示灯
				notification.setNotificationId(new Random().nextInt());
				notification.setTimestamp(currentChooseTime * 60 * 1000 + System.currentTimeMillis());
				MobPush.addLocalNotification(notification);
				new DialogShell(getContext()).autoDismissDialog(R.string.toast_timing, currentChooseTime + "min", 2);
			} break;
			case R.id.tvChooseZero:
			case R.id.tvChooseOne:
			case R.id.tvChooseTwo:
			case R.id.tvChooseThree:
			case R.id.tvChooseFour:
			case R.id.tvChooseFive: {
				tvChooseZero.setSelected(false);
				tvChooseOne.setSelected(false);
				tvChooseTwo.setSelected(false);
				tvChooseThree.setSelected(false);
				tvChooseFour.setSelected(false);
				tvChooseFive.setSelected(false);
				tvChooseZero.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				tvChooseOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				tvChooseTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				tvChooseThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				tvChooseFour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				tvChooseFive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				v.setSelected(true);
				((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_choose_red, 0);
				currentChooseTime = (Integer) v.getTag();
			} break;
		}
	}
}
