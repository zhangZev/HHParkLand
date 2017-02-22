/* 
 * 文件名：AppStartEntity.java
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

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年9月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppStartEntity extends PushEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 101L;

    /** 启动标题 */
    @Expose
    @SerializedName("startTitle")
    private java.lang.String startTitle;

    /** 启动背景 */
    @Expose
    @SerializedName("startBg")
    private java.lang.String startBg;

    /** 启动图片 */
    @Expose
    @SerializedName("startImg")
    private java.lang.String startImg;

    /** 引导内容 */
    @Expose
    @SerializedName("startInfo")
    private java.lang.String startInfo;

    /** 扩展信息 */
    @Expose
    @SerializedName("startExtend")
    private java.lang.String startExtend;

    public java.lang.String getStartTitle() {
        return startTitle;
    }

    public void setStartTitle(java.lang.String startTitle) {
        this.startTitle = startTitle;
    }

    public java.lang.String getStartBg() {
        return startBg;
    }

    public void setStartBg(java.lang.String startBg) {
        this.startBg = startBg;
    }

    public java.lang.String getStartImg() {
        return startImg;
    }

    public void setStartImg(java.lang.String startImg) {
        this.startImg = startImg;
    }

    public java.lang.String getStartInfo() {
        return startInfo;
    }

    public void setStartInfo(java.lang.String startInfo) {
        this.startInfo = startInfo;
    }

    public java.lang.String getStartExtend() {
        return startExtend;
    }

    public void setStartExtend(java.lang.String startExtend) {
        this.startExtend = startExtend;
    }
}
