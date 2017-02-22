package com.henghao.parkland.model.entity;

/**
 * Created by 晏琦云 on 2017/2/13.
 */

public class YhBean {
    private int id;//养护信息ID
    private String treeId;//植物二维码
    private String yhStatusname;//养护行为
    private String yhStatustime;//养护时间
    private String yhStatussite;//养护地点
    private String status;

    // 管护信息状态
    private Integer isNo;

    public Integer getIsNo() {
        return isNo;
    }

    public void setIsNo(Integer isNo) {
        this.isNo = isNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getYhStatusname() {
        return yhStatusname;
    }

    public void setYhStatusname(String yhStatusname) {
        this.yhStatusname = yhStatusname;
    }

    public String getYhStatustime() {
        return yhStatustime;
    }

    public void setYhStatustime(String yhStatustime) {
        this.yhStatustime = yhStatustime;
    }

    public String getYhStatussite() {
        return yhStatussite;
    }

    public void setYhStatussite(String yhStatussite) {
        this.yhStatussite = yhStatussite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
