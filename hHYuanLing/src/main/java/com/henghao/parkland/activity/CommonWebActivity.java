/*
 * 文件名：LoginActivity.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.benefit.buy.library.utils.NSLog;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.utils.JsCalllAndroid;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * webview 基类activity
 * @author zhangxianwen
 * @version HDMNV100R001, 2015-5-14
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommonWebActivity extends ActivityFragmentSupport implements JsCalllAndroid {

	@ViewInject(R.id.webview)
	protected WebView mWebView;

	private String url;

	private String customHtml;

	private WebSettings settings;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivityFragmentView.viewMain(R.layout.common_tecentwebview);
		this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
		this.mActivityFragmentView.viewEmptyGone();
		this.mActivityFragmentView.viewLoading(View.VISIBLE);
		this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
		setContentView(this.mActivityFragmentView);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);// （这个对宿主没什么影响，建议声明）
		getWindow().setSoftInputMode(
		        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
		                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		com.lidroid.xutils.ViewUtils.inject(this);
		initData();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		String title = getIntent().getStringExtra(Constant.INTNET_TITLE);
		initTitle(title);
		this.url = getIntent().getStringExtra(Constant.INTNET_URL);
		this.customHtml = getIntent().getStringExtra(Constant.INTNET_DATA);
		if (ToolsKit.isEmpty(this.url) && ToolsKit.isEmpty(this.customHtml)) {
			initWidget();
		}
		else {
			urlOrData();
		}
	}

	private void initTitle(String title) {
		// TODO Auto-generated method stub
		initWithBar();
		// initWithCenterBar();
		this.mLeftTextView.setVisibility(View.VISIBLE);
		// mCenterTextView.setVisibility(View.GONE);
		this.mLeftTextView.setText(title);
		/** 导航栏 */
	}

	@Override
	public void initWidget() {
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (getIntent().getIntExtra("ONBACKFLAG", 0) == 120) {
			Intent _intent = new Intent();
			boolean isFirst = this.mSharedPreferences.getBoolean(Constant.APP_START_FIRST, false);
			if (!isFirst) {
				// 第一次启动应用,进入引导页
				_intent.setClass(this, GuideActivity.class);
			}
			else {// 进入主页
				_intent.setClass(this, MainActivity.class);
			}
			this.startActivity(_intent);
			finishDelayed();
		}
	}

	public void urlOrData() {
		if (ToolsKit.isEmpty(this.url)) {
			this.mWebView.loadDataWithBaseURL(null, this.customHtml, "text/html", "utf-8", null);
			this.mActivityFragmentView.viewLoading(View.GONE);
		}
		else {
			this.mWebView.loadUrl(this.url);
			initWithContent();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && this.mWebView.canGoBack()) {
			this.mWebView.goBack();// 返回前一个页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint({
	        "JavascriptInterface", "SetJavaScriptEnabled", "NewApi"
	})
	private void initWithContent() {
		this.mWebView.getSettings().setJavaScriptEnabled(true);
		if (Build.VERSION.SDK_INT >= 19) {
			this.mWebView.getSettings().setLoadsImagesAutomatically(true);
		}
		else {
			this.mWebView.getSettings().setLoadsImagesAutomatically(false);
		}
		// mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		this.mWebView.requestFocus();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			this.mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		//
		WebSettings webSettings = this.mWebView.getSettings();
		// 开启Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 启用localStorage 和 essionStorage
		webSettings.setDomStorageEnabled(true);
		// 开启应用程序缓存
		webSettings.setAppCacheEnabled(true);
		webSettings.setAppCachePath(Constant.CACHE_DIR_PATH_WEB);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setAppCacheMaxSize(1024 * 1024 * 50);// 设置缓冲大小，我设的是10M
		webSettings.setAllowFileAccess(true);
		// 启用Webdatabase数据库
		webSettings.setDatabaseEnabled(true);
		webSettings.setDatabasePath(Constant.DB_DIR_PATH);// 设置数据库路径
		// 启用地理定位
		webSettings.setGeolocationEnabled(true);
		// 设置定位的数据库路径
		webSettings.setGeolocationDatabasePath(Constant.DB_DIR_PATH);
		// 开启插件（对flash的支持）
		// webSettings.setPluginsEnabled(true);
		webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setDefaultTextEncodingName("UTF-8");// 设置字符编码
		this.mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);// 滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
		this.mWebView.setWebChromeClient(this.mChromeClient);
		this.mWebView.setWebViewClient(this.mWebViewClient);
		// 增加接口方法,让html页面调用
		this.mWebView.addJavascriptInterface(this, Constant.JS_ClALL_ANDROID);
		// mWebView.setBackgroundColor(0); // 设置背景色
		// mWebView.setBackgroundResource(R.drawable.background);
		this.mWebView.setBackgroundColor(getResources().getColor(R.color.app_bg));
		webSettings.setBuiltInZoomControls(true);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setSavePassword(true);
		webSettings.setSaveFormData(true);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setSupportZoom(false);
		webSettings.setDisplayZoomControls(false);
		// webSettings.setTextSize(WebSettings.TextSize.LARGER);
	}

	private final WebViewClient mWebViewClient = new WebViewClient() {

		// 处理页面导航
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			CommonWebActivity.this.mWebView.loadUrl(url);
			// 记得消耗掉这个事件。给不知道的朋友再解释一下，
			// Android中返回True的意思就是到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (!view.getSettings().getLoadsImagesAutomatically()) {
				view.getSettings().setLoadsImagesAutomatically(true);
			}
			// 加载完成
			CommonWebActivity.this.mActivityFragmentView.viewLoading(View.GONE);
			CommonWebActivity.this.mActivityFragmentView.viewEmptyGone();
			CookieManager cookieManager = CookieManager.getInstance();
			String cookieStr = cookieManager.getCookie(url);
			getLoginUserSharedPre().edit().putString("Cookie", cookieStr).commit();
			NSLog.e(this, "CookieStr:" + cookieStr);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {// 加载失败
			super.onReceivedError(view, errorCode, description, failingUrl);
			CommonWebActivity.this.mActivityFragmentView.viewLoading(View.GONE);
			CommonWebActivity.this.mActivityFragmentView.viewMainGone();
			NSLog.e(this, "errorCode:" + errorCode);
		}
	};

	private WebChromeClient mChromeClient = new WebChromeClient() {

		private View myView = null;

		private CustomViewCallback myCallback = null;

		// // 配置权限 （在WebChromeClinet中实现）
		// @Override
		// public void onGeolocationPermissionsShowPrompt(String origin,
		// GeolocationPermissions.Callback callback) {
		// callback.invoke(origin, true, false);
		// super.onGeolocationPermissionsShowPrompt(origin, callback);
		// }

		// 扩充数据库的容量（在WebChromeClinet中实现）
		@SuppressWarnings("deprecation")
		@Override
		public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota,
		        long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
			quotaUpdater.updateQuota(estimatedSize * 2);
		}

		// 扩充缓存的容量
		@SuppressWarnings("deprecation")
		@Override
		public void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
			quotaUpdater.updateQuota(spaceNeeded * 2);
		}

		// Android 使WebView支持HTML5 Video（全屏）播放的方法
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			if (this.myCallback != null) {
				this.myCallback.onCustomViewHidden();
				this.myCallback = null;
				return;
			}
			ViewGroup parent = (ViewGroup) CommonWebActivity.this.mWebView.getParent();
			parent.removeView(CommonWebActivity.this.mWebView);
			parent.addView(view);
			this.myView = view;
			this.myCallback = callback;
			CommonWebActivity.this.mChromeClient = this;
		}

		@Override
		public void onHideCustomView() {
			if (this.myView != null) {
				if (this.myCallback != null) {
					this.myCallback.onCustomViewHidden();
					this.myCallback = null;
				}
				ViewGroup parent = (ViewGroup) this.myView.getParent();
				parent.removeView(this.myView);
				parent.addView(CommonWebActivity.this.mWebView);
				this.myView = null;
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.mWebView.removeAllViews();
		this.mWebView.destroy();
	}

	@Override
	@JavascriptInterface
	public void send(String data, Object callBack) {
		NSLog.e(this, "home data:" + data + ";callBack:" + callBack);
		if (!ToolsKit.isEmpty(data)) {
			String[] urlString = data.split("#");
			if (!ToolsKit.isEmpty(urlString)) {
				if (urlString.length > 1) {
					String back = urlString[1];
					if (!ToolsKit.isEmpty(back) && "back".equals(back)) {
						this.mHandler.sendEmptyMessage(102);
					}
				}
				if ("PageDetail".equals(urlString[0])) {
					Message msg = new Message();
					msg.what = 103;
					msg.obj = urlString;
					this.mHandler.sendMessage(msg);
				}
				else if ("SearchSuccess".equals(urlString[0])) {
					Message msg = new Message();
					msg.what = 104;
					msg.obj = urlString;
					this.mHandler.sendMessage(msg);
				}
				else {
					Message msg = new Message();
					msg.what = 101;
					msg.obj = urlString;
					this.mHandler.sendMessage(msg);
				}
			}
		}
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			Intent intent = new Intent();
			switch (msg.what) {
				case 101:
					String[] urlString = (String[]) msg.obj;
					try {
						String clazzString = "com.freemode.shopping.activity." + urlString[0] + "Activity";
						Class<?> clazz = Class.forName(clazzString);
						intent.setClass(CommonWebActivity.this, clazz);
						if (urlString.length > 1) {
							intent.putExtra(Constant.HC_JS_DATA_TITLE, urlString[1]);
						}
						if (urlString.length > 2) {
							// 传递数据
							intent.putExtra(Constant.HC_JS_DATA, urlString[2]);
						}
						startActivity(intent);
					}
					catch (ClassNotFoundException e) {
						NSLog.e(this, e.getMessage());
						msg("加载出错");
					}
					break;
				case 102:
					finish();
					break;
				case 103:
					// //向服务端请求得到商品model和店铺model,然后跳转到商品详情
					// String[] urlStrings = (String[]) msg.obj;
					// intent.setClass(CommonWebActivity.this,
					// PageDetailActivity.class);
					// if (urlStrings.length > 1) {
					// intent.putExtra(Constant.HC_JS_DATA_TITLE, urlStrings[1]);
					// }
					// if (urlStrings.length > 2) {
					// intent.putExtra(Constant.HC_JS_DATA, urlStrings[2]);
					// }
					// startActivityForResult(intent,
					// Constant.SCANNIN_GREQUEST_CODE);
					break;
				case 104:
					// 传统分类id,跳转到搜索分类页面
					// //向服务端请求得到商品model和店铺model,然后跳转到商品详情
					// String[] classString = (String[]) msg.obj;
					// intent.setClass(CommonWebActivity.this,
					// SearchSuccessActivity.class);
					// if (classString.length > 1) {
					// intent.putExtra(Constant.HC_JS_DATA_TITLE, classString[1]);
					// }
					// if (classString.length > 2) {
					// intent.putExtra(Constant.HC_JS_DATA, classString[2]);
					// }
					// intent.putExtra("FLAG", 120);//类别查询
					// startActivityForResult(intent,
					// Constant.SCANNIN_GREQUEST_CODE);
					break;
				case 105:
					break;
				default:
					break;
			}
		};
	};

}
