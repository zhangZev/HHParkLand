package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by Administrator on 2017/2/19 0019.
 */

public class YHDataEntity extends IdEntity {
    /*"gid":3,
            "treeId":"m280DWBfovMCdH-1",
            "yhSite":"中国贵州省贵阳市乌当区G75(兰海高速)",
            "yhWorker":"无",
            "yhDetails":"无",
            "yhTime":"2017-02-19 14:48",
            "yhQuestion":"无",
            "yhClean":"好",
            "treeGrowup":"好",
            "yhComment":"无"*/


    @Expose
    @SerializedName("treeGrowup")
    private String treeGrowup;

    @Expose
    @SerializedName("yhClean")
    private String yhClean;

    @Expose
    @SerializedName("treeId")
    private String treeId;

    @Expose
    @SerializedName("yhSite")
    private String yhSite;

    @Expose
    @SerializedName("yhWorker")
    private String yhWorker;

    @Expose
    @SerializedName("yhDetails")
    private String yhDetails;

    @Expose
    @SerializedName("yhTime")
    private String yhTime;

    @Expose
    @SerializedName("yhQuestion")
    private String yhQuestion;

    @Expose
    @SerializedName("yhComment")
    private String yhComment;

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getYhSite() {
        return yhSite;
    }

    public void setYhSite(String yhSite) {
        this.yhSite = yhSite;
    }

    public String getYhWorker() {
        return yhWorker;
    }

    public void setYhWorker(String yhWorker) {
        this.yhWorker = yhWorker;
    }

    public String getYhDetails() {
        return yhDetails;
    }

    public void setYhDetails(String yhDetails) {
        this.yhDetails = yhDetails;
    }

    public String getYhTime() {
        return yhTime;
    }

    public void setYhTime(String yhTime) {
        this.yhTime = yhTime;
    }

    public String getYhQuestion() {
        return yhQuestion;
    }

    public void setYhQuestion(String yhQuestion) {
        this.yhQuestion = yhQuestion;
    }

    public String getYhComment() {
        return yhComment;
    }

    public void setYhComment(String yhComment) {
        this.yhComment = yhComment;
    }

    public String getTreeGrowup() {
        return treeGrowup;
    }

    public void setTreeGrowup(String treeGrowup) {
        this.treeGrowup = treeGrowup;
    }

    public String getYhClean() {
        return yhClean;
    }

    public void setYhClean(String yhClean) {
        this.yhClean = yhClean;
    }
}
