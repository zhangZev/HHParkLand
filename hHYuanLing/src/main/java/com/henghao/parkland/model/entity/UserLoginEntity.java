package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

public class UserLoginEntity extends IdEntity {

    /*"uid":1,
            "username":"user1",
            "password":"1234",
            "tel":"12345678977"*/
    @Expose
    private String uid;

    @Expose
    private String username;

    @Expose
    private String password;

    @Expose
    private String tel;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
