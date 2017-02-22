/* 
 * 文件名：AppVersionEntity.java
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
import com.henghao.parkland.model.IdEntity;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年9月19日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppVersionEntity extends IdEntity {

    /** 版本号 */
    @Expose
    @SerializedName("version")
    private java.lang.String version;

    /** 应用名称 */
    @Expose
    @SerializedName("appName")
    private java.lang.String appName;

    /** 下载地址 */
    @Expose
    @SerializedName("url")
    private java.lang.String url;

    /** 更新状态 */
    @Expose
    @SerializedName("state")
    private java.lang.Integer state;

    /** 更新描述 */
    @Expose
    @SerializedName("msg")
    private java.lang.String msg;

    public java.lang.String getVersion() {
        return version;
    }

    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    public java.lang.String getAppName() {
        return appName;
    }

    public void setAppName(java.lang.String appName) {
        this.appName = appName;
    }

    public java.lang.String getUrl() {
        return url;
    }

    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    public java.lang.Integer getState() {
        return state;
    }

    public void setState(java.lang.Integer state) {
        this.state = state;
    }

    public java.lang.String getMsg() {
        return msg;
    }

    public void setMsg(java.lang.String msg) {
        this.msg = msg;
    }
}
