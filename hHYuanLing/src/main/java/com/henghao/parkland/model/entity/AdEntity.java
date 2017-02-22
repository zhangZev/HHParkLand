/* 
 * 文件名：AdEntity.java
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
import com.henghao.parkland.model.IdEntity;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年5月10日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AdEntity extends IdEntity {

    /** 点击类型 */
    @Expose
    private java.lang.Integer adType;

    /** 广告名称 */
    @Expose
    private java.lang.String adName;

    /** 广告链接 */
    @Expose
    private java.lang.String adLink;

    /** 广告图片 */
    @Expose
    private java.lang.String adImage;

    /** 广告描述 */
    @Expose
    private java.lang.String adDescribe;

    @Expose
    private java.lang.String adImageUrl;

    public java.lang.String getAdImageUrl() {
        return adImageUrl;
    }

    public void setAdImageUrl(java.lang.String adImageUrl) {
        this.adImageUrl = adImageUrl;
    }

    public java.lang.Integer getAdType() {
        return adType;
    }

    public void setAdType(java.lang.Integer adType) {
        this.adType = adType;
    }

    public java.lang.String getAdName() {
        return adName;
    }

    public void setAdName(java.lang.String adName) {
        this.adName = adName;
    }

    public java.lang.String getAdLink() {
        return adLink;
    }

    public void setAdLink(java.lang.String adLink) {
        this.adLink = adLink;
    }

    public java.lang.String getAdImage() {
        return adImage;
    }

    public void setAdImage(java.lang.String adImage) {
        this.adImage = adImage;
    }

    public java.lang.String getAdDescribe() {
        return adDescribe;
    }

    public void setAdDescribe(java.lang.String adDescribe) {
        this.adDescribe = adDescribe;
    }
}
