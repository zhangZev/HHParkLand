/*
 * 文件名：ActivityFragmentSupport.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsFile;
import com.benefit.buy.library.views.dialog.FlippingLoadingDialog;
import com.henghao.parkland.model.ascyn.BusinessResponse;
import com.henghao.parkland.service.ReConnectService;
import com.henghao.parkland.views.ActivityFragmentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;



/**
 * Activity 父类
 * @author qyl
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ActivityFragmentSupport extends FragmentActivity implements IActivitySupport, BusinessResponse {

	protected Context context = null;

	protected SharedPreferences mSharedPreferences;

	protected FMApplication mApplication;

	protected NotificationManager notificationManager;

	/**
	 * 屏幕的宽度、高度、密度
	 */
	public int mScreenWidth;

	public int mScreenHeight;

	public float mDensity;

	protected ActivityFragmentView mActivityFragmentView;

	@ViewInject(R.id.bar_left)
	protected LinearLayout mLeftLinearLayout;

	@ViewInject(R.id.bar_center)
	protected LinearLayout mCenterLinearLayout;

	@ViewInject(R.id.bar_right)
	protected LinearLayout mRightLinearLayout;

	// public SystemBarTintManager mTintManager;
	protected ImageView mLeftImageView;

	protected ImageView mLeftImageViewTips;

	protected TextView mLeftTextView;

	/** 导航栏中间文字 **/
	protected TextView mCenterTextView;

	/** 导航栏中间图标 **/
	protected ImageView mCenterImageView;

	/** 导航栏右边文字 **/
	protected TextView mRightTextView;

	/** 导航栏右边图标 **/
	protected ImageView mRightImageView;

	/** 导航栏右边提示图标 **/
	protected ImageView mRightImageViewTips;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		this.mSharedPreferences = getSharedPreferences(Constant.SHARED_SET, 0);
		this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.mApplication = (FMApplication) getApplication();
		this.mApplication.addActivity(this);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		this.mScreenWidth = metric.widthPixels;
		this.mScreenHeight = metric.heightPixels;
		this.mDensity = metric.density;
		// msg("mDensity:"+mDensity+";mScreenWidth:"+mScreenWidth+"mScreenHeight:"+mScreenHeight);
		this.mActivityFragmentView = (ActivityFragmentView) getLayoutInflater().inflate(R.layout.common_activity_views,
		        null);
		this.mLeftLinearLayout = (LinearLayout) this.mActivityFragmentView.findViewById(R.id.bar_left);
		this.mCenterLinearLayout = (LinearLayout) this.mActivityFragmentView.findViewById(R.id.bar_center);
		this.mRightLinearLayout = (LinearLayout) this.mActivityFragmentView.findViewById(R.id.bar_right);
		// SystemBarTintManager mTintManager = new SystemBarTintManager(this);
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// setTranslucentStatus(true);
		// }
	}

	/**
	 * 公共的返回按钮
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public void initWithBar() {
		/** 导航栏左边 **/
		View viewLeft = LayoutInflater.from(this).inflate(R.layout.common_view_bar_left, this.mLeftLinearLayout);
		viewLeft.setPadding(getResources().getDimensionPixelSize(R.dimen.padding_normal), 0, getResources()
		        .getDimensionPixelSize(R.dimen.padding_normal), 0);
		// viewLeft.setBackgroundColor(getResources().getColor(R.color.blue));
		this.mLeftImageView = (ImageView) viewLeft.findViewById(R.id.bar_left_img);
		this.mLeftTextView = (TextView) viewLeft.findViewById(R.id.bar_left_title);
		this.mLeftTextView.setText(getResources().getString(R.string.back));
		this.mLeftTextView.setVisibility(View.GONE);
		this.mLeftImageView.setImageResource(R.drawable.btn_back);
		this.mLeftLinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ActivityFragmentSupport.this.isBackPressed) {
					onBackPressed();
				}
				else {
					onActivityBackPressed();
				}
			}
		});
	}

	protected boolean isBackPressed = true;

	protected void onActivityBackPressed() {
	}

	/**
	 * 导航栏中间〈一句话功能简述〉 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public View initWithCenterBar() {
		/** 导航栏中间 **/
		View viewCenter = getLayoutInflater().inflate(R.layout.common_view_bar_center, this.mCenterLinearLayout);
		ImageView mcenterImageView = (ImageView) viewCenter.findViewById(R.id.bar_center_img);
		mcenterImageView.setVisibility(View.GONE);
		this.mCenterTextView = (TextView) viewCenter.findViewById(R.id.bar_center_title);
		this.mCenterTextView.setText(getResources().getString(R.string.hc_home));
		// mcenterImageView.setImageResource(R.drawable.nav_logo);
		return viewCenter;
	}

	/**
	 * 导航栏右边
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public View initWithRightBar() {
		View viewRight = getLayoutInflater().inflate(R.layout.common_view_bar_right, this.mRightLinearLayout);
		viewRight.setPadding(getResources().getDimensionPixelSize(R.dimen.padding_normal), 0, getResources()
		        .getDimensionPixelSize(R.dimen.padding_normal), 0);
		// viewRight.setBackgroundColor(getResources().getColor(R.color.blue));
		this.mRightImageView = (ImageView) viewRight.findViewById(R.id.bar_right_img);
		this.mRightTextView = (TextView) viewRight.findViewById(R.id.bar_right_title);
		this.mRightTextView.setText("back");
		this.mRightTextView.setVisibility(View.GONE);
		// mRightImageView.setImageResource(R.drawable.nav_logo);
		return viewRight;
	}

	/**
	 * 导航栏上图标下文字
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public View initWithUpDownRightBar() {
		View viewRight = getLayoutInflater().inflate(R.layout.common_view_bar_upright, this.mRightLinearLayout);
		viewRight.setPadding(getResources().getDimensionPixelSize(R.dimen.padding_normal), 0, getResources()
		        .getDimensionPixelSize(R.dimen.padding_normal), 0);
		// viewRight.setBackgroundColor(getResources().getColor(R.color.blue));
		this.mRightImageView = (ImageView) viewRight.findViewById(R.id.bar_right_img);
		this.mRightImageViewTips = (ImageView) viewRight.findViewById(R.id.bar_right_imgtips);
		this.mRightTextView = (TextView) viewRight.findViewById(R.id.bar_right_title);
		this.mRightTextView.setText("back");
		this.mRightTextView.setVisibility(View.GONE);
		this.mRightImageViewTips.setVisibility(View.GONE);
		// mRightImageView.setImageResource(R.drawable.nav_logo);
		return viewRight;
	}

	/**
	 * 导航栏上图标下文字
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public View initWithUpDownLeftBar() {
		View viewLeft = getLayoutInflater().inflate(R.layout.common_view_bar_upright, this.mLeftLinearLayout);
		viewLeft.setPadding(getResources().getDimensionPixelSize(R.dimen.padding_normal), 0, getResources()
		        .getDimensionPixelSize(R.dimen.padding_normal), 0);
		// viewRight.setBackgroundColor(getResources().getColor(R.color.blue));
		this.mLeftImageView = (ImageView) viewLeft.findViewById(R.id.bar_right_img);
		this.mLeftTextView = (TextView) viewLeft.findViewById(R.id.bar_right_title);
		this.mLeftImageViewTips = (ImageView) viewLeft.findViewById(R.id.bar_right_imgtips);
		this.mLeftTextView.setText("back");
		this.mLeftTextView.setVisibility(View.GONE);
		this.mLeftImageViewTips.setVisibility(View.GONE);
		// mRightImageView.setImageResource(R.drawable.nav_logo);
		return viewLeft;
	}

	public void setViewMainGone() {
		if (validateInternetmain()) {
			this.mActivityFragmentView.viewMainGone();
		}
	}

	/**
	 * 百度推送 〈一句话功能简述〉 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	protected void bdPushManager() {
		// Resources resource = this.getResources();
		// String pkgName = this.getPackageName();
		// PushManager.startWork(getApplicationContext(),
		// PushConstants.LOGIN_TYPE_API_KEY, Constant.APIKEY);
		// // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
		// // PushManager.enableLbs(getApplicationContext());
		// // Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// // 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// // 与下方代码中 PushManager.setNotificationBuilder(this, 1,
		// cBuilder)中的第二个参数对应
		// CustomPushNotificationBuilder cBuilder = new
		// CustomPushNotificationBuilder(
		// resource.getIdentifier("notification_custom_builder", "layout",
		// pkgName),
		// resource.getIdentifier("notification_icon", "id", pkgName),
		// resource.getIdentifier("notification_title", "id", pkgName),
		// resource.getIdentifier("notification_text", "id", pkgName));
		// cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		// cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
		// cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
		// cBuilder.setLayoutDrawable(resource.getIdentifier("simple_notification_icon",
		// "drawable", pkgName));
		// cBuilder.setNotificationSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI,
		// "6").toString());
		// // 推送高级设置，通知栏样式设置为下面的ID
		// PushManager.setNotificationBuilder(this, 1, cBuilder);
	}

	// public void statusBar(boolean status, int color) {
	// mTintManager.setStatusBarTintEnabled(status);
	// // mTintManager.setNavigationBarTintEnabled(true);
	// mTintManager.setStatusBarTintResource(color);
	// }
	//
	// public void statusBar(boolean status) {
	// statusBar(status, R.color.header);
	// }
	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		}
		else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	public FMApplication getFMApplication() {
		return this.mApplication;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// mTintManager.setTintColor(getResources().getColor(R.color.header));
		savedInstanceState.putString("exception", "exception");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("exception", "exception");
	}

	@Override
	public void stopService() {
		Intent reConnectService = new Intent(this.context, ReConnectService.class);
		this.context.stopService(reConnectService);
	}

	@Override
	public void startService() {
		// 自动恢复连接服务
		Intent reConnectService = new Intent(this.context, ReConnectService.class);
		this.context.startService(reConnectService);
	}

	@Override
	public boolean hasInternetConnected() {
		ConnectivityManager manager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo network = manager.getActiveNetworkInfo();
			if ((network != null) && network.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validateInternet() {
		ConnectivityManager manager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			openWirelessSet();
			return false;
		}
		else {
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if (info != null) {
				for (NetworkInfo element : info) {
					if (element.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		openWirelessSet();
		return false;
	}

	public boolean validateInternetmain() {
		ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		else {
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if (info != null) {
				for (NetworkInfo element : info) {
					if (element.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 设置网络dialog
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public void openWirelessSet() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.context);
		dialogBuilder.setTitle(R.string.prompt).setMessage(this.context.getString(R.string.check_connection))
		        .setPositiveButton(R.string.menu_settings, new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
				        dialog.cancel();
				        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				        ActivityFragmentSupport.this.context.startActivity(intent);
			        }
		        }).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int whichButton) {
				        dialog.cancel();
			        }
		        });
		dialogBuilder.show();
	}

	@Override
	public void isExit() {
		// TODO Auto-generated method stub\
	}

	@Override
	public boolean hasLocationGPS() {
		LocationManager manager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
		if (manager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasLocationNetWork() {
		LocationManager manager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
		if (manager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void checkMemoryCard() {
		if (!ToolsFile.isSdcardExist()) {
			new AlertDialog.Builder(this.context).setTitle(R.string.prompt).setMessage("请检查内存卡")
			        .setPositiveButton(R.string.menu_settings, new DialogInterface.OnClickListener() {

				        @Override
				        public void onClick(DialogInterface dialog, int which) {
					        dialog.cancel();
					        Intent intent = new Intent(Settings.ACTION_SETTINGS);
					        ActivityFragmentSupport.this.context.startActivity(intent);
				        }
			        }).setNegativeButton("退出", new DialogInterface.OnClickListener() {

				        @Override
				        public void onClick(DialogInterface dialog, int which) {
					        dialog.cancel();
					        ActivityFragmentSupport.this.mApplication.exit();
				        }
			        }).create().show();
		}
	}

	@Override
	public Context getContext() {
		return this.context;
	}

	@Override
	public SharedPreferences getLoginUserSharedPre() {
		return this.mSharedPreferences;
	}

	@Override
	public String getLoginUser() {
		return this.mSharedPreferences.getString(Constant.USERID, null);
	}

	@Override
	public String getLoginState() {
		return this.mSharedPreferences.getString(Constant.USERSTATE, "0");
	}

	@Override
	public String getLoginUid() {
		return this.mSharedPreferences.getString(Constant.USERID, "0");
	}

	@Override
	public String getLoginUserPhone() {
		return this.mSharedPreferences.getString(Constant.USERNAME, null);
	}

	@Override
	public void closeKeyBoard(EditText mEdit) {
		mEdit.clearFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEdit.getWindowToken(), 0);
	}

	@Override
	public void closeInput() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if ((inputMethodManager != null) && (getCurrentFocus() != null)) {
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
			        InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	// @Override
	// public void startActivity(Intent intent) {
	// super.startActivity(intent);
	// ComponentName com = intent.getComponent();
	// if (com != null) {
	// String className = com.getClassName();
	// if (MobUIShell.class.getName().equals(className)) {
	// return;
	// }
	// }
	// overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	// }
	//
	// @Override
	// public void startActivityForResult(Intent intent, int requestCode) {
	// super.startActivityForResult(intent, requestCode);
	// ComponentName com = intent.getComponent();
	// if (com != null) {
	// String className = com.getClassName();
	// if (MobUIShell.class.getName().equals(className)) {
	// return;
	// }
	// }
	// overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	// }
	@Override
	public void finish() {
		this.mApplication.removeActivity(this);
		super.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	@Override
	public void initWidget() {
	}

	@Override
	public void initData() {
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (isFastDoubleClick()) {
				return true;
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	private static long lastClickTime;

	/**
	 * 防止连续点击
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if ((timeD >= 0) && (timeD <= 400)) {
			return true;
		}
		else {
			lastClickTime = time;
			return false;
		}
	}

	public void msg(String msg) {
		msg(msg, 3000);
	}

	public void msg(String msg, int delayed) {
		msg(msg, delayed, null);
	}

	public void msg(String msg, int delayed, final MessageCallBack mMessageCallBack) {
		try {
			final FlippingLoadingDialog mloading = new FlippingLoadingDialog(this, msg);
			boolean isShowing = mloading.isShowing();
			if (!isShowing) {
				if (!isFinishing()) {
					mloading.show();
				}
			}
			new Handler() {

				@Override
				public void handleMessage(android.os.Message msg) {
					if (!isFinishing()) {
						mloading.dismiss();
					}
					if (mMessageCallBack != null) {
						mMessageCallBack.onMessageCall();
					}
				};
			}.sendEmptyMessageDelayed(0, delayed);
		}
		catch (Exception e) {
		}
	}

	public interface MessageCallBack {

		public void onMessageCall();
	}

	@Override
	public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
		this.mActivityFragmentView.viewLoading(View.GONE);
		if (jo == null) {
			return;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void finishDelayed() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				finish();
			}
		}, 1200);
	}
}
