package com.henghao.parkland.model.entity;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagList implements Serializable {
    //    "id":"1",
    //    "name":"灯",
    //    "isHot":1,
    //    "icon":null,
    //    "color":"#FFB6C1",
    //    "work":null

    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private String icon;

    @Expose
    private String color;

    @Expose
    @SerializedName("work")
    private List<TagList> work;

    @Expose
    private int isHot;//热搜

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TagList> getWork() {
        return this.work;
    }

    public void setWork(List<TagList> work) {
        this.work = work;
    }

    public int getIsHot() {
        return this.isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
