/*
 * 文件名：AppInfoService.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.service;

import java.io.File;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsNetwork;
import com.benefit.buy.library.views.ToastView;
import com.benefit.buy.library.views.dialog.BaseDialog;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.model.ascyn.BusinessResponse;
import com.henghao.parkland.model.entity.AppSystemInfoEntity;
import com.henghao.parkland.model.entity.AppVersionEntity;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.VersionCounpnEntity;
import com.henghao.parkland.model.protocol.SystemProtocol;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015-4-28
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppInfoService extends Service implements BusinessResponse {

	private SystemProtocol mSystemProtocol;

	public String DOWNLOAD_FOLDER_NAME = "freemode";

	public String DOWNLOAD_FILE_NAME = "freemode";

	@Override
	public void onCreate() {
		super.onCreate();
		this.mSystemProtocol = new SystemProtocol(this);
		this.mSystemProtocol.addResponseListener(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String deviceInfo = getDeviceInfo(this);
		this.mSystemProtocol.appSystemUpdate(deviceInfo);
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 唯一标识符
	 * @param context
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private String getDeviceInfo(Context context) {
		try {
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
			        .getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = tm.getDeviceId();
			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
			        .getSystemService(Context.WIFI_SERVICE);
			String mac = wifi.getConnectionInfo().getMacAddress();
			if (TextUtils.isEmpty(device_id)) {
				if (!TextUtils.isEmpty(mac) && !"00:00:00:00:00:00".equals(mac)) {// 00:00:00:00:00
					device_id = mac;
				}
			}
			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
				        android.provider.Settings.Secure.ANDROID_ID);
			}
			String networkName = tm.getNetworkOperator();// MCC+MNC(mobile country code + mobile network code)
			String net = ToolsNetwork.hasActivityNetwork(context) ? (ToolsNetwork.isWifiConnected(context) ? "wifi"
			        : (ToolsNetwork.isMobileNetworkConnected(context) ? "4G/3G/2G" : "未知网络")) : "没有网络";
			String appVer = "";
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			appVer = pi.versionName;
			int appCode = pi.versionCode;
			AppSystemInfoEntity systemInfo = new AppSystemInfoEntity();
			systemInfo.setDeviceId(device_id);
			systemInfo.setMac(mac);
			systemInfo.setNetwork(networkName);
			systemInfo.setNet(net);
			systemInfo.setIp(ToolsKit.getIP());
			systemInfo.setSdkVersion(android.os.Build.VERSION.SDK_INT + "");
			systemInfo.setAppVersion(appVer);
			systemInfo.setCompVersion(appCode);
			systemInfo.setModel(android.os.Build.MODEL);
			systemInfo.setType(Constant.APP_SYS_ANDROID);
			return ToolsJson.toJson(systemInfo);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
		if (jo == null) {
			// 提示
			return;
		}
		BaseDialog mBaseDialog;
		String title = getString(R.string.prompt);
		String button1 = getString(R.string.update_btn_ok);
		String button2 = getString(R.string.update_btn_cancel);
		if (url.endsWith(ProtocolUrl.APP_SYS_UPDATE)) {
			if (jo instanceof BaseEntity) {
				BaseEntity base = (BaseEntity) jo;
				return;
			}
			VersionCounpnEntity versionCounpnEntity = (VersionCounpnEntity) jo;
			AppVersionEntity appVersion = versionCounpnEntity.getVersionEntity();
			if (appVersion == null) {
				return;
			}
			String message = appVersion.getMsg().replace(" ", "\n");
			mBaseDialog = BaseDialog.getDialog(getApplicationContext(), title, message, button1,
			        new OnKeyListenerUpdate(appVersion.getUrl(), appVersion.getState()), button2,
			        new OnKeyListenerCancel(appVersion.getState()));
			mBaseDialog.setOnKeyListener(this.mOnKeyListenerKeyBack);
			mBaseDialog.setCanceledOnTouchOutside(false);
			ToastView.makeText(this, message).show();
			if (appVersion.getState() == Constant.APP_UPDATE) {
				// 代表不是最新版本，需要更新
				mBaseDialog.show();
			}
			else if (appVersion.getState() == Constant.APP_UPDATE_COMPEL) {
				// 代表不是最新版本，需要强制更新
				mBaseDialog.show();
			}
		}
	}

	DialogInterface.OnKeyListener mOnKeyListenerKeyBack = new DialogInterface.OnKeyListener() {

		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				return true;
			}
			else {
				return false; // 默认返回 false
			}
		}
	};

	// 马上更新
	private class OnKeyListenerUpdate implements DialogInterface.OnClickListener {

		private final String downloadUrl;

		private final int update;

		public OnKeyListenerUpdate(String url, int staus) {
			this.downloadUrl = url;
			this.update = staus;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			beginning(this.downloadUrl, this.update);
			dialog.dismiss();
		}
	}

	// 马上更新
	private class OnKeyListenerCancel implements DialogInterface.OnClickListener {

		private final int update;

		public OnKeyListenerCancel(int staus) {
			this.update = staus;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (this.update == 2) {
				dialog.dismiss();
			}
			else if (this.update == 3) {
				// 强制更新
			}
		}
	}

	private DownloadManager downloadManager;

	public static AlertDialog dialog;

	@SuppressLint("NewApi")
	public void beginning(String url, int flag) {
		File folder = Environment.getExternalStoragePublicDirectory(this.DOWNLOAD_FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
		this.downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setDestinationInExternalPublicDir(this.DOWNLOAD_FOLDER_NAME, this.DOWNLOAD_FILE_NAME);
		request.setTitle(getString(R.string.app_name));
		request.setDescription(getString(R.string.updateing_hint));
		// 设置文件类型
		request.setMimeType("application/vnd.android.package-archive");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			request.allowScanningByMediaScanner();
		}
		request.setVisibleInDownloadsUi(true);
		long downloadId = this.downloadManager.enqueue(request);
		SharedPreferences sPreferences = getSharedPreferences("downloadcomplete", 0);
		sPreferences.edit().putLong("downloadId", downloadId).commit();
		sendBroadcast(new Intent(this, UpdataBroadcastReceiver.class));
		// Intent intent = new Intent(context, VersionService.class);
		// context.startService(intent);
		if (flag == 1) {
			Builder builder = new Builder(this);
			builder.setTitle(getString(R.string.update_title));
			builder.setMessage(getString(R.string.update_msg));
			builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					// ((Activity) context).finish();
				}
			});
			dialog = builder.show();
			dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					if (keyCode == KeyEvent.KEYCODE_BACK) {// 监听返回按键
						// ((Activity) context).finish();
					}
					return false;
				}
			});
			dialog.setCanceledOnTouchOutside(false);
		}
	}
}
