/*
 * 文件名：SplashActivity.java
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
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 开机启动或广告页
 * @author zhangxianwen
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SplashActivity extends ActivityFragmentSupport {

	/**
	 * 广告
	 */
	@ViewInject(R.id.imageview_splash)
	private ImageView mImageview;

	/**
	 * 启动页图片
	 */
	@ViewInject(R.id.splash_img)
	private ImageView mImageView;

	/**
	 * 标题
	 */
	@ViewInject(R.id.splash_title)
	private TextView mTitleTextView;

	@ViewInject(R.id.welcome)
	private LinearLayout welcomeLayout;

	@ViewInject(R.id.relativelayout)
	private RelativeLayout relativelayout;

	@ViewInject(R.id.rl_view)
	private RelativeLayout rl_view;

	@ViewInject(R.id.ll_time)
	private LinearLayout ll_time;// 广告提示

	@ViewInject(R.id.tv_time)
	private TextView mTvTime;

	private int count = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_activity_splash);
		com.lidroid.xutils.ViewUtils.inject(this);
		// showAd();
		// toMain();
		initData();
	}

	/**
	 * 倒计时 〈一句话功能简述〉 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void getCount() {
		this.count--;
		if (this.count != 0) {
			this.mTvTime.setText("跳过：" + this.count);
		}

	}

	@Override
	public void initData() {
		this.mTvTime.setText("跳过：" + this.count);
		// 默认是本地图片
		// 当服务端要求更改信息，以服务端为准
		boolean isSplash = getLoginUserSharedPre().getBoolean(Constant.APP_SPLASH, false);
		if (!isSplash) {// 取得本地启动页面画面
			postDelayed(1000);
		}
		else {
		}
	}

	@OnClick({
		R.id.ll_time
	})
	public void viewClick(View v) {
		switch (v.getId()) {
			case R.id.ll_time:
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, MainActivity.class);
				SplashActivity.this.startActivity(intent);
				finishDelayed();
				break;
			default:
				break;
		}
	}

	@SuppressLint("HandlerLeak")
	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 101:
					Intent _intent = new Intent();
					boolean isFirst = SplashActivity.this.mSharedPreferences
					        .getBoolean(Constant.APP_START_FIRST, false);
//					if (!isFirst) {
//						// 第一次启动应用,进入引导页
//						_intent.setClass(SplashActivity.this, GuideActivity.class);
//					}
//					else {// 进入主页
					_intent.setClass(SplashActivity.this, MainActivity.class);

//					}
					SplashActivity.this.startActivity(_intent);
					finishDelayed();
					break;
				case 100:
					getCount();
					if (SplashActivity.this.count != 0) {
						SplashActivity.this.mHandler.sendEmptyMessageDelayed(100, 1000);
					}
					else {
						SplashActivity.this.mHandler.sendEmptyMessageDelayed(101, 1000);
					}
					break;
				default:
					break;
			}
		};
	};

	/**
	 * 延迟
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	private void postDelayed(final int time) {
		this.mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				SplashActivity.this.mHandler.sendEmptyMessage(101);
			}
		}, time);
	}

}
