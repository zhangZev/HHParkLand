package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * 提示 〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年4月1日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TipsEntity extends IdEntity {
    //    "id":"2",
    //    "title":"2016-4-15正式上线！！",
    //    "icon":"http://192.168.3.198:8080/file/resource/images/l/111.png",
    //    "subhead":"上线注册有礼哦！！",
    //    "subIcon":"http://192.168.3.198:8080/file/resource/images/l/111.png",
    //    "clickType":"0",
    //    "clickUrl":null,
    //    "objId":null,
    //    "btnType":null,
    //    "btnText":null,
    //    "subState":1,
    //    "startTime":"2016-04-13 15:37:25:000",
    //    "endTime":"2016-04-29 15:37:28:000",
    //    "tipHtml":null

    @Expose
    private String title;

    @Expose
    private String icon;

    @Expose
    private String subhead;

    @Expose
    private String subIcon;

    @Expose
    private int subState;//1显示 0 不显示

    @Expose
    private int clickType;//点击类型：0=点击无效 1=web类型 2=单商品 4=多商品5=店铺  6=url

    @Expose
    private String clickUrl;

    @Expose
    private String objId;

    @Expose
    private int btnType;//Button类型： 0=没有点击 1=button 2=箭头

    @Expose
    private String btnText;

    @Expose
    private String state;

    @Expose
    private String startTime;

    @Expose
    private String endTime;

    @Expose
    private String tipHtml;

    public String getTitle() {
        return this.title;
    }

    public int getSubState() {
        return this.subState;
    }

    public void setSubState(int subState) {
        this.subState = subState;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSubhead() {
        return this.subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getSubIcon() {
        return this.subIcon;
    }

    public void setSubIcon(String subIcon) {
        this.subIcon = subIcon;
    }

    public int getClickType() {
        return this.clickType;
    }

    public void setClickType(int clickType) {
        this.clickType = clickType;
    }

    public String getClickUrl() {
        return this.clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getObjId() {
        return this.objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public int getBtnType() {
        return this.btnType;
    }

    public void setBtnType(int btnType) {
        this.btnType = btnType;
    }

    public String getBtnText() {
        return this.btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTipHtml() {
        return this.tipHtml;
    }

    public void setTipHtml(String tipHtml) {
        this.tipHtml = tipHtml;
    }
}
