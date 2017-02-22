package com.henghao.parkland.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

/**
 * Created by ZedLi on 2015/12/1.
 */
public class PhoneUtil {

	private static int PHONE_WIDTH;

	private static int PHONE_HEIGHT;

	private static String ROOT_PATH; // app项目根目录

	private static String AD_PATH;// app存放广告的目录

	private static String VIDEO_PATH;// app存放video目录

	private static String VOICE_PATH;// app存放Voice目录

	public static void init(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		PHONE_WIDTH = dm.widthPixels;
		PHONE_HEIGHT = dm.heightPixels;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + context.getPackageName() + "/file/";
		} else {
			ROOT_PATH = context.getFilesDir().getPath().toString() + "/";
		}
		AD_PATH = ROOT_PATH + "ad/";
	}

	public static int getPhoneWidth() {
		return PHONE_WIDTH;
	}

	public static int getPhoneHeight() {
		return PHONE_HEIGHT;
	}

	// file目录
	public static String getAppFilePath() {
		File file = new File(ROOT_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return ROOT_PATH;
	}

	public static String getAdPath() {
		File file = new File(AD_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return AD_PATH;
	}

	public static boolean isFileExit(String path) {
		File file = new File(path);
		return file.exists();
	}

}
