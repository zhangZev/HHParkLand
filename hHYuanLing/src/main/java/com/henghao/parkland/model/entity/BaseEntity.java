/* 
 * 文件名：BaseModel.java
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

import java.io.Serializable;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author qyl
 * @version HDMNV100R001, 2015-4-23
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3440061414071692254L;
    @Expose
    @SerializedName("result")
    private String msg;

    @Expose
    @SerializedName("error")
    private int error;

    @Expose
    @SerializedName("data")
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
