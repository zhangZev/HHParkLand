/*
 * 文件名：BlueToothUtil.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.utils.bluetoothutils;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016-11-16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressLint("NewApi")
public class BlueToothUtil {

	// UniversalBluetoothLE
	public static BlueToothUtil universalBluetoothLE;

	private final Context context;
	// BluetoothAdapter
	private final BluetoothAdapter mBluetoothAdapter;
	// BluetoothManager
	private final BluetoothManager bluetoothManager;

	// 打开蓝牙的请求码
	public static final int REQUEST_ENABLE_BLUETOOTH = 10010;

	// 是否正在扫描蓝牙设备
	private boolean mScanning;
	// 设置扫描时长
	private static final long SCAN_PERIOD = 10000;

	// 蓝牙扫描的返回
	BluetoothAdapter.LeScanCallback leScanCallback;
	// 蓝牙设别的list
	List<BluetoothDevice> bluetoothDeviceList = new ArrayList<BluetoothDevice>();

	Handler mHandler = new Handler();

	LeScanListenter leScanListenter;

	private BlueToothUtil(Context context) {
		this.context = context;
		// 得到BluetoothManager
		this.bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
		// 得到BluetoothAdapter
		this.mBluetoothAdapter = this.bluetoothManager.getAdapter();

		// 蓝牙搜索的回调
		this.leScanCallback = new BluetoothAdapter.LeScanCallback() {

			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
				BlueToothUtil.this.bluetoothDeviceList.add(device);

				// 返回所有列表
				BlueToothUtil.this.leScanListenter.leScanCallBack(BlueToothUtil.this.bluetoothDeviceList);

			}
		};
	}

	/**
	 * 获得到UniversalBluetoothLE对象
	 * @param context
	 * @return
	 */
	public static BlueToothUtil inistance(Context context) {
		if (universalBluetoothLE == null) {
			universalBluetoothLE = new BlueToothUtil(context);
		}
		return universalBluetoothLE;
	}

	/**
	 * 检查蓝牙是否打开并且启动打开蓝牙的方法
	 */
	public void openBbletooth() {
		// 判断蓝牙是否开启
		if (this.mBluetoothAdapter == null || !this.mBluetoothAdapter.isEnabled()) {
			// 打开蓝牙
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			this.context.startActivity(enableIntent);
		}
	}

	/**
	 * 开始（true）或结束（false）蓝牙扫描
	 * @param enable
	 */
	private void scanLeDevice(final boolean enable) {
		if (enable && this.mScanning == false) {
			this.mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					BlueToothUtil.this.mScanning = false;
					BlueToothUtil.this.mBluetoothAdapter.stopLeScan(BlueToothUtil.this.leScanCallback);
				}
			}, SCAN_PERIOD);

			this.mScanning = true;
			this.mBluetoothAdapter.startLeScan(this.leScanCallback);
		}
		else {
			this.mScanning = false;
			this.mBluetoothAdapter.stopLeScan(this.leScanCallback);
		}
	}

	/**
	 * 开始搜索蓝牙设备
	 * @param leScanListenter 搜索蓝牙设备的回调（返回设备列表）
	 */
	public void startScanLeDevice(final LeScanListenter leScanListenter) {
		this.bluetoothDeviceList.clear();
		this.leScanListenter = leScanListenter;
		scanLeDevice(true);
	}

	/**
	 * 停止搜索设备
	 */
	public void stopScanLeDevice() {
		if (this.leScanCallback == null) {
			return;
		}
		scanLeDevice(false);
	}

	/**
	 * 搜索蓝牙的回调
	 */
	public interface LeScanListenter {
		void leScanCallBack(List<BluetoothDevice> bluetoothDeviceList);
	}

	/**
	 * 得到BluetoothGatt
	 * @param device 设备
	 * @param autoConnect 是否自动链接
	 * @param bluetoothGattCallback 回调
	 */
	public BluetoothGatt getConnectGatt(BluetoothDevice device, boolean autoConnect,
	        BluetoothGattCallback bluetoothGattCallback) {
		return device.connectGatt(this.context, autoConnect, bluetoothGattCallback);
	}

}
