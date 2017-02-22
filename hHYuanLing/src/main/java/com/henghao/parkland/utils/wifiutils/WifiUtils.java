/*
 * 文件名：WifiAdmin.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.utils.wifiutils;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016-11-16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WifiUtils {
	// 定义WifiManager对象
	private final WifiManager mWifiManager;
	// 定义WifiInfo对象
	private final WifiInfo mWifiInfo;
	// 扫描出的网络连接列表
	private List<ScanResult> mWifiList;
	// 网络连接列表
	private List<WifiConfiguration> mWifiConfiguration;
	// 定义一个WifiLock
	WifiLock mWifiLock;

	// 构造器
	public WifiUtils(Context context) {
		// 取得WifiManager对象
		this.mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// 取得WifiInfo对象
		this.mWifiInfo = this.mWifiManager.getConnectionInfo();
	}

	// 打开WIFI
	public void openWifi() {
		if (!this.mWifiManager.isWifiEnabled()) {
			this.mWifiManager.setWifiEnabled(true);
		}
	}

	// 关闭WIFI
	public void closeWifi() {
		if (this.mWifiManager.isWifiEnabled()) {
			this.mWifiManager.setWifiEnabled(false);
		}
	}

	// 检查当前WIFI状态
	public int checkState() {
		return this.mWifiManager.getWifiState();
	}

	// 锁定WifiLock
	public void acquireWifiLock() {
		this.mWifiLock.acquire();
	}

	// 解锁WifiLock
	public void releaseWifiLock() {
		// 判断时候锁定
		if (this.mWifiLock.isHeld()) {
			this.mWifiLock.acquire();
		}
	}

	// 创建一个WifiLock
	public void creatWifiLock() {
		this.mWifiLock = this.mWifiManager.createWifiLock("Test");
	}

	// 得到配置好的网络
	public List<WifiConfiguration> getConfiguration() {
		return this.mWifiConfiguration;
	}

	// 指定配置好的网络进行连接
	public void connectConfiguration(int index) {
		// 索引大于配置好的网络索引返回
		if (index > this.mWifiConfiguration.size()) {
			return;
		}
		// 连接配置好的指定ID的网络
		this.mWifiManager.enableNetwork(this.mWifiConfiguration.get(index).networkId, true);
	}

	public void startScan() {
		this.mWifiManager.startScan();
		// 得到扫描结果
		this.mWifiList = this.mWifiManager.getScanResults();
		// 得到配置好的网络连接
		this.mWifiConfiguration = this.mWifiManager.getConfiguredNetworks();
	}

	// 得到网络列表
	public List<ScanResult> getWifiList() {
		return this.mWifiList;
	}

	// 查看扫描结果
	public StringBuilder lookUpScan() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < this.mWifiList.size(); i++) {
			stringBuilder.append("Index_" + new Integer(i + 1).toString() + ":");
			// 将ScanResult信息转换成一个字符串包
			// 其中把包括：BSSID、SSID、capabilities、frequency、level
			stringBuilder.append((this.mWifiList.get(i)).toString());
			stringBuilder.append("/n");
		}
		return stringBuilder;
	}

	// 得到MAC地址
	public String getMacAddress() {
		return (this.mWifiInfo == null) ? "NULL" : this.mWifiInfo.getMacAddress();
	}

	// 得到接入点的BSSID
	public String getBSSID() {
		return (this.mWifiInfo == null) ? "NULL" : this.mWifiInfo.getBSSID();
	}

	// 得到IP地址
	public int getIPAddress() {
		return (this.mWifiInfo == null) ? 0 : this.mWifiInfo.getIpAddress();
	}

	// 得到连接的ID
	public int getNetworkId() {
		return (this.mWifiInfo == null) ? 0 : this.mWifiInfo.getNetworkId();
	}

	// 得到WifiInfo的所有信息包
	public String getWifiInfo() {
		return (this.mWifiInfo == null) ? "NULL" : this.mWifiInfo.toString();
	}

	// 添加一个网络并连接
	public void addNetwork(WifiConfiguration wcg) {
		int wcgID = this.mWifiManager.addNetwork(wcg);
		boolean b = this.mWifiManager.enableNetwork(wcgID, true);
		System.out.println("a--" + wcgID);
		System.out.println("b--" + b);
	}

	// 断开指定ID的网络
	public void disconnectWifi(int netId) {
		this.mWifiManager.disableNetwork(netId);
		this.mWifiManager.disconnect();
	}

	// 然后是一个实际应用方法，只验证过没有密码的情况：
	public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type) {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";

		WifiConfiguration tempConfig = this.IsExsits(SSID);
		if (tempConfig != null) {
			this.mWifiManager.removeNetwork(tempConfig.networkId);
		}

		if (Type == 1) // WIFICIPHER_NOPASS
		{
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == 2) // WIFICIPHER_WEP
		{
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + Password + "\"";
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == 3) // WIFICIPHER_WPA
		{
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		return config;
	}

	private WifiConfiguration IsExsits(String SSID) {
		List<WifiConfiguration> existingConfigs = this.mWifiManager.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				return existingConfig;
			}
		}
		return null;
	}

}
