/*
 * 文件名：Constant.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland;

import android.os.Environment;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxianwen
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Constant {

	/**
	 * 本地数据的配置
	 */
	public static final String SHARED_SET = "sharedset";// 登录设置

	/**
	 * 判断应用是否是第一次启动
	 */
	public static final String APP_START_FIRST = "app_first";

	/**
	 * 启动页
	 */
	public static final String APP_SPLASH = "app_splash";

	/**
	 * 是否启动引导，false代表不启动，true代表启动
	 */
	public static final String IS_APP_START_FIRST = "is_app_first";

	/**
	 * 程序运行期间产生的文件，缓存根目录
	 */
	public static final String ROOT_DIR_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/parkLand/cache";

	/**
	 * 缓存文件保存的根目录
	 */
	public static final String CACHE_DIR_PATH_ROOT = ROOT_DIR_PATH + "/file";

	/**
	 * 错误日志
	 */
	public static final String LOG_DIR_PATH = ROOT_DIR_PATH + "/log";

	/**
	 * 数据库目录
	 */
	public static final String DB_DIR_PATH = ROOT_DIR_PATH + "/databases";

	/**
	 * 本地数据库的名称
	 */
	public static final String VINORD_DB = "parkland.db";

	/**
	 * 数据库的版本号
	 */
	public static final int DBVERSION = 1;

	/**
	 * 图片文件夹
	 */
	public static final String CACHE_DIR_PATH = CACHE_DIR_PATH_ROOT + "/images";



	/**
	 * web缓存
	 */
	public static final String CACHE_DIR_PATH_WEB = CACHE_DIR_PATH_ROOT
			+ "/webcache";

	/**
	 * 分页条数
	 */
	public static final int PAGE_SIZE = 12;

	/**
	 * 分页订单
	 */
	public static final int PAGE_SIZE_MIN = 6;

	/**
	 * 距离
	 */
	public static final int DISTANCE = 6000;

	/**
	 * 用户ID
	 */
	public static final String USERID = "userId";

	public static final String USERNAME = "user_name";

	public static final String USERPHONE = "user_login";

	public static final String USERIMG = "user_image";

	public static final String USERSTATE = "user_state";

	/**
	 * 传递Title
	 */
	public static final String INTNET_TITLE = "intent_title";
	/**
	 * 传递数据
	 */
	public static final String INTNET_DATA = "intent_data";
	/**
	 * 传递类型
	 */
	public static final String INTNET_TYPE = "intent_type";
	/**
	 * 传递id
	 */
	public static final String INTNET_ID = "intent_id";
	/**
	 * 传递url
	 */
	public static final String INTNET_URL = "intent_url";

	/********* end hc start ************/
	/**
	 * js调用android的实例化
	 */
	public static final String JS_ClALL_ANDROID = "jsBridge";

	/**
	 * js调用android的实例化 传递信息
	 */
	public static final String HC_JS_DATA = "js_data";

	public static final String HC_JS_CODE = "js_code";

	/**
	 * js调用android的实例化 传递信息
	 */
	public static final String HC_JS_DATA_TITLE = "js_data_title";

	/**
	 * 二维码
	 */
	public final static int SCANNIN_GREQUEST_CODE = 1;

	/**
	 * 返回码
	 */
	public final static int REQUEST_CODE = 109;

	/**
	 * 上传
	 */
	public final static int SCANNIN_UPLOAD_CODE = 11;

	/***** 百度推送 apikey *****/
	public static String APIKEY = "sU35AqlCV7TDOQdbHy0anRro";

	/***** 推送json *****/
	public final static String PUSH_PARSE_JSON = "push_parse_json";

	/** 系统 android */
	public static final int APP_SYS_ANDROID = 3;

	/** app没有更新 */
	public static final int APP_UPDATE_NODE = 0;

	/** app正常更新 */
	public static final int APP_UPDATE = 1;

	/** app强制更新 */
	public static final int APP_UPDATE_COMPEL = 2;

}
