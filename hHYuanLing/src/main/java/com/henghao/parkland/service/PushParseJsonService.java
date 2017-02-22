/*
 * 文件名：PushMessageService.java
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
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;

import com.benefit.buy.library.utils.NSLog;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.AppGuideEntity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.HttpHandler.State;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年9月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PushParseJsonService extends Service {

	private ImageView mImageView;

	private BitmapUtils mBitmapUtils;

	@Override
	public void onCreate() {
		super.onCreate();
		this.mImageView = new ImageView(this);
		this.mBitmapUtils = new BitmapUtils(this, Constant.CACHE_DIR_PATH);
		this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_default_big);
		this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String json = intent.getStringExtra(Constant.PUSH_PARSE_JSON);
		String data = null;
		try {
			JSONObject jsObj = new JSONObject(json);
			data = jsObj.getString("custom_content");
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public void downGuideFile(AppGuideEntity appGuideEntity) {
		ImageView mImageView = new ImageView(this);
		BitmapUtils mBitmapUtils = new BitmapUtils(this, Constant.CACHE_DIR_PATH);
		mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_default_big);
		mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
		// qin ProtocolUrl.APP_SYSTEM_IMG_URL +
		mBitmapUtils.display(mImageView, appGuideEntity.getGuideImg(), null, new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View container, String uri, Bitmap bitmap, BitmapDisplayConfig config,
			        BitmapLoadFrom from) {
				NSLog.e(this, "onLoadCompleted");
				getSharedPreferences(Constant.SHARED_SET, 0).edit().putBoolean(Constant.APP_START_FIRST, false)
				        .commit();
				getSharedPreferences(Constant.SHARED_SET, 0).edit().putBoolean(Constant.IS_APP_START_FIRST, true)
				        .commit();
			}

			@Override
			public void onLoadFailed(View container, String uri, Drawable drawable) {
				NSLog.e(this, "onLoadFailed");
				getSharedPreferences(Constant.SHARED_SET, 0).edit().putBoolean(Constant.APP_START_FIRST, true).commit();
				getSharedPreferences(Constant.SHARED_SET, 0).edit().putBoolean(Constant.IS_APP_START_FIRST, false)
				        .commit();
			}
		});
	}

	public boolean downFile(HttpUtils http, String imageFile) {
		boolean isDownFile = false;
		// qin ProtocolUrl.APP_SYSTEM_IMG_URL +
		HttpHandler<File> httpHandler = http.download(imageFile, Constant.CACHE_DIR_PATH + File.separator
		        + imageFile, true, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
		        new RequestCallBack<File>() {

			        @Override
			        public void onStart() {
				        NSLog.e(this, "conn...");
			        }

			        @Override
			        public void onLoading(long total, long current, boolean isUploading) {
				        NSLog.e(this, current + "/" + total);
			        }

			        @Override
			        public void onSuccess(ResponseInfo<File> responseInfo) {
				        NSLog.e(this, "downloaded:" + responseInfo.result.getPath());
			        }

			        @Override
			        public void onFailure(HttpException error, String msg) {
				        NSLog.e(this, error.getMessage());
			        }
		        });
		State state = httpHandler.getState();
		if (state == State.SUCCESS) {
			isDownFile = true;
		}
		return isDownFile;
	}
}
