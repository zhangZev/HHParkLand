package com.henghao.parkland.utils;

import com.benefit.buy.library.utils.NSLog;
import com.henghao.parkland.ActivityFragmentSupport;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;

public class ImageUpLoad {
	public static String uploadUserHeader(ActivityFragmentSupport mActivity,File imageFile) {
		RequestParams params = new RequestParams(); // 默认编码UTF-8
		params.addBodyParameter("userId", mActivity.getLoginUser());
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, "地址", params, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				// super.onStart();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				if (isUploading) {
					NSLog.e(this, "upload: " + current + "/" + total);
				} else {
					NSLog.e(this, "reply: " + current + "/" + total);
				}
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				NSLog.e(this, "onFailure" + msg + ";error:" + error);
				// msg(msg);
			}
		});
		return null;
	}
}
