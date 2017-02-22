package com.henghao.parkland.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationUtils {

	private static String city;

	private static String address;

	private static double lat;

	private static double lng;

	/**
	 * 区域编码
	 */
	private static String cityCode;

	/**
	 * 定位
	 */
	public static void Location(Context mContext) {
		LocationClient client = new LocationClient(mContext);
		// 设置定位的一些参数
		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll");
		option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
		client.setLocOption(option);
		// 设置定位成功之后的监听
		client.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation bdLocation) {
				address = bdLocation.getAddrStr();
				lat = bdLocation.getLatitude();
				lng = bdLocation.getLongitude();
				cityCode = bdLocation.getCityCode();
				city = bdLocation.getCity();
			}
		});
		client.start();
	}

	public static String getCity() {
		return city;
	}

	public static double getLat() {
		return lat;
	}

	public static double getLng() {
		return lng;
	}

	public static String getCityCode() {
		return cityCode;
	}

	public static String getAddress() {
		return address;
	}

}
