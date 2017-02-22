/* 
 * 文件名：AppSystemInfoEntity.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.Constant;
import com.henghao.parkland.model.IdEntity;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015-4-28
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppSystemInfoEntity extends IdEntity {

    /** ip地址 */
    @Expose
    @SerializedName("ip")
    private java.lang.String ip;

    /** 浏览器 */
    @Expose
    @SerializedName("browse")
    private java.lang.String browse;

    /** 推送token */
    @Expose
    @SerializedName("token")
    private java.lang.String token;

    /** mac地址 */
    @Expose
    @SerializedName("mac")
    private java.lang.String mac;

    /** 唯一标识 */
    @Expose
    @SerializedName("deviceId")
    private java.lang.String deviceId;

    /** 运营商 */
    @Expose
    @SerializedName("network")
    private java.lang.String network;

    /** 网络环境 */
    @Expose
    @SerializedName("net")
    private java.lang.String net;

    /** sdk版本 */
    @Expose
    @SerializedName("sdkVersion")
    private java.lang.String sdkVersion;

    /** 机型 */
    @Expose
    @SerializedName("model")
    private java.lang.String model;

    /** app版本 */
    @Expose
    @SerializedName("appVersion")
    private java.lang.String appVersion;

    /** 兼容版本号 */
    @Expose
    @SerializedName("compVersion")
    private java.lang.Integer compVersion;

    /**
     * 系统类型 1、android
     */
    @Expose
    @SerializedName("type")
    private java.lang.Integer type = Constant.APP_SYS_ANDROID;

    public java.lang.String getIp() {
        return ip;
    }

    public void setIp(java.lang.String ip) {
        this.ip = ip;
    }

    public java.lang.String getBrowse() {
        return browse;
    }

    public void setBrowse(java.lang.String browse) {
        this.browse = browse;
    }

    public java.lang.String getToken() {
        return token;
    }

    public void setToken(java.lang.String token) {
        this.token = token;
    }

    public java.lang.String getMac() {
        return mac;
    }

    public void setMac(java.lang.String mac) {
        this.mac = mac;
    }

    public java.lang.String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(java.lang.String deviceId) {
        this.deviceId = deviceId;
    }

    public java.lang.String getNetwork() {
        return network;
    }

    public void setNetwork(java.lang.String network) {
        this.network = network;
    }

    public java.lang.String getNet() {
        return net;
    }

    public void setNet(java.lang.String net) {
        this.net = net;
    }

    public java.lang.String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(java.lang.String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public java.lang.String getModel() {
        return model;
    }

    public void setModel(java.lang.String model) {
        this.model = model;
    }

    public java.lang.String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(java.lang.String appVersion) {
        this.appVersion = appVersion;
    }

    public java.lang.Integer getCompVersion() {
        return compVersion;
    }

    public void setCompVersion(java.lang.Integer compVersion) {
        this.compVersion = compVersion;
    }

    public java.lang.Integer getType() {
        return type;
    }

    public void setType(java.lang.Integer type) {
        this.type = type;
    }
}
