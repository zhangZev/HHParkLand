package com.benefit.buy.library.utils.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

public class ToolsKit {

	/**
	 * 判断字符串是否为null或空
	 * @param str
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static boolean isEmpty(String str) {
		String nutext = "null";
		return ((str == null) || (str.trim().length() == 0) || (str == "null") || (str.equals(nutext)));
	}

	/**
	 * 获取EditText的文字
	 * @param et
	 * @return
	 */
	public static String getEditText(EditText et) {
		return et.getText().toString().trim();
	}

	/**
	 * 判断集合是否为null或空
	 * @param collection
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static boolean isEmpty(@SuppressWarnings("rawtypes") Collection collection) {
		return ((collection == null) || (collection.isEmpty()));
	}

	public static boolean isEmpty(Object[] obj) {
		if ((obj == null) || (obj.length == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断Map是否为null或空
	 * @param map
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return ((map == null) || (map.isEmpty()));
	}

	/**
	 * 截取（a，b，）字符串
	 * @param buf
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static String subString(StringBuffer buf) {
		String channleStr = buf.toString();
		Log.d("@@@", "channleStr:" + channleStr);
		if (channleStr.endsWith(",")) {
			int len = channleStr.length();
			// 取得频道id字符串
			channleStr = channleStr.substring(0, len - 1);
			Log.d("@@@", "channleStr is split:" + channleStr);
		}
		return channleStr;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		// Log.e("@@@", "dip2px-->" + scale);
		return (int) ((dpValue * scale) + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) ((pxValue / scale) + 0.5f);
	}

	/**************************/
	public static boolean num(Object o) {
		int n = 0;
		try {
			n = Integer.parseInt(o.toString().trim());
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (n > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static int intIsEmpty(Object o) {
		int n = 0;
		if (o == null) {
			return n;
		}
		try {
			n = Integer.parseInt(o.toString().trim());
		}
		catch (NumberFormatException e) {
		}
		return n;
	}

	public static boolean decimal(Object o) {
		double n = 0;
		try {
			n = Double.parseDouble(o.toString().trim());
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (n > 0.0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 给JID返回用户名
	 * @param Jid
	 * @return
	 */
	public static String getUserNameByJid(String Jid) {
		if (isEmpty(Jid)) {
			return null;
		}
		if (!Jid.contains("@")) {
			return Jid;
		}
		return Jid.split("@")[0];
	}

	/**
	 * 给用户名返回JID
	 * @param jidFor 域名//如ahic.com.cn
	 * @param userName
	 * @return
	 */
	public static String getJidByName(String userName, String jidFor) {
		if (isEmpty(jidFor) || isEmpty(jidFor)) {
			return null;
		}
		return userName + "@" + jidFor;
	}

	/**
	 * 给用户名返回JID,使用默认域名ahic.com.cn
	 * @param userName
	 * @return
	 */
	public static String getJidByName(String userName) {
		String jidFor = "ahic.com.cn";
		return getJidByName(userName, jidFor);
	}

	/**
	 * 拼接url地址参数
	 * @param map
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static String getParams(Map<String, Object> map) {
		if (isEmpty(map)) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object val = entry.getValue();
			buffer.append(key).append("#x=a_").append(val).append("#x&a_");
		}
		String params = buffer.substring(0, buffer.length() - 5);
		return params;
	}

	/**
	 * 根据资源id获取图片数组 〈一句话功能简述〉 〈功能详细描述〉
	 * @param context
	 * @param resId
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static int[] getImgRes(Context context, int resId) {
		TypedArray ar = context.getResources().obtainTypedArray(resId);
		int len = ar.length();
		int[] resIds = new int[len];
		for (int i = 0; i < len; i++) {
			resIds[i] = ar.getResourceId(i, 0);
		}
		ar.recycle();
		return resIds;
	}

	/**
	 * 保留价格两位数 〈一句话功能简述〉 〈功能详细描述〉
	 * @param price
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static String stringPrice(double price) {
		if (price <= 0) {
			return price + "";
		}
		// String priceString = price + "";
		// int len = priceString.length();
		// int index = priceString.indexOf(".");
		// if (index > 0) {
		// String priceStr = priceString.substring(index, len);
		// int size = priceStr.length();
		// if (size > 2) {
		// String string = priceString.substring(0, index + 3);
		// return string;
		// }
		// else {
		// return priceString;
		// }
		// }
		// else {
		// return priceString;
		// }
		DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		return decimalFormat.format(price);// format 返回的是字符串
	}

	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = {
		        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[(byte0 >>> 4) & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}
		catch (Exception e) {
			return null;
		}
	}

	public static String getIP() {
		String IP = "127.0.0.1";
		StringBuilder IPStringBuilder = new StringBuilder();
		try {
			Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaceEnumeration.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
				Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
				while (inetAddressEnumeration.hasMoreElements()) {
					InetAddress inetAddress = inetAddressEnumeration.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
					        && inetAddress.isSiteLocalAddress()) {
						IPStringBuilder.append(inetAddress.getHostAddress().toString());
						IP = IPStringBuilder.toString();
						return IP;
					}
				}
			}
		}
		catch (SocketException ex) {
			ex.printStackTrace();
		}
		return IP;
	}

	/**
	 * 图片饱和度
	 * @param mImageView
	 * @param color
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static void drawable(ImageView mImageView, int color) {
		Drawable drawable = mImageView.getDrawable();
		if (drawable != null) {
			drawable.mutate();
			ColorMatrix cm = new ColorMatrix();
			cm.setSaturation(color);
			ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
			drawable.setColorFilter(cf);
			mImageView.setImageDrawable(drawable);
		}
	}

	/**
	 * 唯一标识符
	 * @param context
	 * @return
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
			        .getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = tm.getDeviceId();
			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
			        .getSystemService(Context.WIFI_SERVICE);
			String mac = wifi.getConnectionInfo().getMacAddress();
			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}
			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
				        android.provider.Settings.Secure.ANDROID_ID);
			}
			String networkName = tm.getNetworkOperator();// MCC+MNC(mobile country code + mobile network code)
			String net = ToolsNetwork.hasActivityNetwork(context) ? (ToolsNetwork.isWifiConnected(context) ? "wifi"
			        : (ToolsNetwork.isMobileNetworkConnected(context) ? "4G/3G/2G" : "未知网络")) : "没有网络";
			String appVer = "";
			appVer = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			//
			json.put("device", "Android");
			json.put("deviceId", device_id);
			json.put("mac", mac);
			json.put("network", networkName);
			json.put("net", net);
			json.put("ip", ToolsKit.getIP());
			json.put("sdkVersion", android.os.Build.VERSION.SDK_INT);
			json.put("model", android.os.Build.MODEL);
			json.put("appVersion", appVer);
			return json.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
