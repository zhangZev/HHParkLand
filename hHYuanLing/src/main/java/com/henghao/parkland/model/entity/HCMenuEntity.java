/**
 * 
 */
package com.henghao.parkland.model.entity;

/**
 * @author qinyulun
 */
public class HCMenuEntity {

    private int mId;

    private String title;

    private int icon;

    private String clazz;

    //-1不显示,0、正常显示，1，更多
    private int status;

    public HCMenuEntity() {
        super();
    }

    public HCMenuEntity(int mId, String title, int icon, String clazz) {
        super();
        this.mId = mId;
        this.title = title;
        this.icon = icon;
        this.clazz = clazz;
        status = 0;
    }

    public HCMenuEntity(int mId, String title, int icon, String clazz, int status) {
        super();
        this.mId = mId;
        this.title = title;
        this.icon = icon;
        this.clazz = clazz;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
