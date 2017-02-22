package com.henghao.parkland.utils;

import android.content.Context;

import com.henghao.parkland.R;

public class GetImageDrawable {

	/**
	 * 获取图片
	 * @param pic 图片名称
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public static int getImage(Context context, String pic) {
		if (pic == null || pic.trim().equals("")) {
			return R.drawable.img_loading_default_big;// 返回默认图
		}
		try {
			int resID = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + pic, null, null);
			return resID;
		}
		catch (SecurityException e) {
			return R.drawable.img_loading_default_big;
		}
		catch (IllegalArgumentException e) {
			return R.drawable.img_loading_default_big;
		}
	}
}
