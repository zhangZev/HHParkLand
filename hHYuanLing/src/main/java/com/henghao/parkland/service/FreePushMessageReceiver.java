/*
 * 文件名：PushMessageReceiver.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.benefit.buy.library.utils.NSLog;
import com.henghao.parkland.Constant;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年9月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FreePushMessageReceiver extends PushMessageReceiver {

	/** TAG to Log */
	public static final String TAG = FreePushMessageReceiver.class.getSimpleName();

	public static String mChannelId;

	/**
	 * 调用PushManager.startWork后，sdk将对push server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel id和user
	 * id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
	 * @param context BroadcastReceiver的执行Context
	 * @param errorCode 绑定接口返回值，0 - 成功
	 * @param appid 应用id。errorCode非0时为null
	 * @param userId 应用user id。errorCode非0时为null
	 * @param channelId 应用channel id。errorCode非0时为null
	 * @param requestId 向服务端发起的请求id。在追查问题时有用；
	 * @return none
	 */
	@Override
	public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {
		String responseString = "onBind errorCode=" + errorCode + " appid=" + appid + " userId=" + userId
		        + " channelId=" + channelId + " requestId=" + requestId;
		Log.d(TAG, responseString);
		mChannelId = channelId;
		if (errorCode == 0) {
			// 绑定成功
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		updateContent(context, responseString);
	}

	/**
	 * 接收透传消息的函数。
	 * @param context 上下文
	 * @param message 推送的消息
	 * @param customContentString 自定义内容,为空或者json字符串
	 */
	@Override
	public void onMessage(Context context, String message, String customContentString) {
		String messageString = " message=\"" + message + "\" customContentString=" + customContentString;
		Log.d(TAG, messageString);
		// 自定义内容获取方式，mykey和myvalue对应透传消息推送时自定义内容中设置的键和值
		if (!TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (!customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
					NSLog.e(this, "onMessage key:" + myvalue);
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
		onParseJsonMessage(context, message);
	}

	/**
	 * 启动服务
	 * @param context
	 * @param message
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public void onParseJsonMessage(Context context, String message) {
		Intent intent = new Intent(context, PushParseJsonService.class);
		intent.putExtra(Constant.PUSH_PARSE_JSON, message);
		context.startService(intent);
	}

	/**
	 * 接收通知点击的函数。
	 * @param context 上下文
	 * @param title 推送的通知的标题
	 * @param description 推送的通知的描述
	 * @param customContentString 自定义内容，为空或者json字符串
	 */
	@Override
	public void onNotificationClicked(Context context, String title, String description, String customContentString) {
		String notifyString = "通知点击 title=\"" + title + "\" description=\"" + description + "\" customContent="
		        + customContentString;
		Log.d(TAG, notifyString);
		// 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
		if (!TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				int mType = 0;
				// {"push_type":"1","userId":"8a233f3354142b6201541473146e003e"}
				if (!customJson.isNull("push_type")) {
					mType = customJson.getInt("push_type");// 1消息列表
				}
				// String userId = null;
				// if (!customJson.isNull("userId")) {
				// userId = customJson.getString("userId");
				// }
				switch (mType) {
					case 1:
						// 消息列表
//                        if (SystemUtils.isAppAlive(context, "com.freemode.shopping")) {
//                            //如果存活的话，就直接启动MessageActivity，但要考虑一种情况，就是app的进程虽然仍然在
//                            //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
//                            //MessageActivity,再按Back键就不会返回MainActivity了。所以在启动
//                            //MessageActivity前，要先启动MainActivity。
//                            Log.i("NotificationReceiver", "the app process is alive");
//                            Intent mainIntent = new Intent(context, MainActivity.class);
//                            //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
//                            //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
//                            //如果Task栈不存在MainActivity实例，则在栈顶创建
//                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            Intent detailIntent = new Intent(context, MessageActivity.class);
//                            Intent[] intents = {mainIntent, detailIntent};
//                            context.startActivities(intents);
//                        }
//                        else {
//                            //如果app进程已经被杀死，先重新启动app，将MessageActivity的启动参数传入Intent中，参数经过
//                            //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入             
//                            //参数跳转到MessageActivity中去了
//                            Intent launchIntent = context.getPackageManager()
//                                    .getLaunchIntentForPackage("com.freemode.shopping");
//                            launchIntent.setFlags(
//                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                            launchIntent.setAction("android.intent.action.VIEW");
//                            Bundle args = new Bundle();
//                            args.putInt("push_type", mType);
//                            launchIntent.putExtra(Constant.STARTFLAG, args);
//                            context.startActivity(launchIntent);
//                        }
						break;
					default:
						break;
				}
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		updateContent(context, notifyString);
	}

	/**
	 * 接收通知到达的函数。
	 * @param context 上下文
	 * @param title 推送的通知的标题
	 * @param description 推送的通知的描述
	 * @param customContentString 自定义内容，为空或者json字符串
	 */
	@Override
	public void onNotificationArrived(Context context, String title, String description, String customContentString) {
		String notifyString = "onNotificationArrived  title=\"" + title + "\" description=\"" + description
		        + "\" customContent=" + customContentString;
		Log.d(TAG, notifyString);
		// 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
		if (!TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (!customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
				}
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// 你可以參考 onNotificationClicked中的提示从自定义内容获取具体值
		updateContent(context, notifyString);
	}

	/**
	 * setTags() 的回调函数。
	 * @param context 上下文
	 * @param errorCode 错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
	 * @param successTags 设置成功的tag
	 * @param failTags 设置失败的tag
	 * @param requestId 分配给对云推送的请求的id
	 */
	@Override
	public void onSetTags(Context context, int errorCode, List<String> sucessTags, List<String> failTags,
	        String requestId) {
		String responseString = "onSetTags errorCode=" + errorCode + " sucessTags=" + sucessTags + " failTags="
		        + failTags + " requestId=" + requestId;
		Log.d(TAG, responseString);
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		updateContent(context, responseString);
	}

	/**
	 * delTags() 的回调函数。
	 * @param context 上下文
	 * @param errorCode 错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
	 * @param successTags 成功删除的tag
	 * @param failTags 删除失败的tag
	 * @param requestId 分配给对云推送的请求的id
	 */
	@Override
	public void onDelTags(Context context, int errorCode, List<String> sucessTags, List<String> failTags,
	        String requestId) {
		String responseString = "onDelTags errorCode=" + errorCode + " sucessTags=" + sucessTags + " failTags="
		        + failTags + " requestId=" + requestId;
		Log.d(TAG, responseString);
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		updateContent(context, responseString);
	}

	/**
	 * listTags() 的回调函数。
	 * @param context 上下文
	 * @param errorCode 错误码。0表示列举tag成功；非0表示失败。
	 * @param tags 当前应用设置的所有tag。
	 * @param requestId 分配给对云推送的请求的id
	 */
	@Override
	public void onListTags(Context context, int errorCode, List<String> tags, String requestId) {
		String responseString = "onListTags errorCode=" + errorCode + " tags=" + tags;
		Log.d(TAG, responseString);
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑\
		updateContent(context, responseString);
	}

	/**
	 * PushManager.stopWork() 的回调函数。
	 * @param context 上下文
	 * @param errorCode 错误码。0表示从云推送解绑定成功；非0表示失败。
	 * @param requestId 分配给对云推送的请求的id
	 */
	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		String responseString = "onUnbind errorCode=" + errorCode + " requestId = " + requestId;
		Log.d(TAG, responseString);
		if (errorCode == 0) {
			// 解绑定成功
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		updateContent(context, responseString);
	}

	private void updateContent(Context context, String content) {
		Log.d(TAG, "updateContent");
		// String logText = "" + Utils.logStringCache;
		// if (!logText.equals("")) {
		// logText += "\n";
		// }
		// SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss");
		// logText += sDateFormat.format(new Date()) + ": ";
		// logText += content;
		// Utils.logStringCache = logText;
		// Intent intent = new Intent();
		// intent.setClass(context.getApplicationContext(), PushDemoActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.getApplicationContext().startActivity(intent);
	}
}
