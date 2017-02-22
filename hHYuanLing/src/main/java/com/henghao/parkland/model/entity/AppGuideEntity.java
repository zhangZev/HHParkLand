/* 
 * 文件名：AppGuideEntity.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.entity;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015-4-23
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppGuideEntity extends PushEntity {

    /** 引导标题 */
    @Expose
    @SerializedName("guideTitle")
    private java.lang.String guideTitle;

    /** 引导一页 */
    @Expose
    @SerializedName("guideImg")
    private java.lang.String guideImg;

    /** 引导内容 */
    @Expose
    @SerializedName("guideInfo")
    private java.lang.String guideInfo;

    /** 扩展信息 */
    @Expose
    @SerializedName("guideExtend")
    private java.lang.String guideExtend;

    @Expose
    private List<AppGuideEntity> guideList;//子级

    public java.lang.String getGuideTitle() {
        return guideTitle;
    }

    public void setGuideTitle(java.lang.String guideTitle) {
        this.guideTitle = guideTitle;
    }

    public java.lang.String getGuideImg() {
        return guideImg;
    }

    public void setGuideImg(java.lang.String guideImg) {
        this.guideImg = guideImg;
    }

    public java.lang.String getGuideInfo() {
        return guideInfo;
    }

    public void setGuideInfo(java.lang.String guideInfo) {
        this.guideInfo = guideInfo;
    }

    public java.lang.String getGuideExtend() {
        return guideExtend;
    }

    public void setGuideExtend(java.lang.String guideExtend) {
        this.guideExtend = guideExtend;
    }

    public List<AppGuideEntity> getGuideList() {
        return guideList;
    }

    public void setGuideList(List<AppGuideEntity> guideList) {
        this.guideList = guideList;
    }
}
