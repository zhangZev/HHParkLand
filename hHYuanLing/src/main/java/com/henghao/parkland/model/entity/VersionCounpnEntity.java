package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionCounpnEntity {
    //    {
    //        isCounpn = 1;
    //        version =     {
    //            appName = "\U514d\U8d39\U88c5";
    //            compVersion = 2;
    //            msg = "\U514d\U8d39\U88c5";
    //            state = 1;
    //            url = "";
    //            version = 2;
    //        };
    //    }

    @Expose
    private int isCounpn;

    @Expose
    @SerializedName("version")
    private AppVersionEntity versionEntity;

    public int getIsCounpn() {
        return isCounpn;
    }

    public void setIsCounpn(int isCounpn) {
        this.isCounpn = isCounpn;
    }

    public AppVersionEntity getVersionEntity() {
        return versionEntity;
    }

    public void setVersionEntity(AppVersionEntity versionEntity) {
        this.versionEntity = versionEntity;
    }
}
