/* 
 * 文件名：IdEntity.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lidroid.xutils.db.annotation.Column;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015-4-23
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class IdEntity implements java.io.Serializable {

    @Expose
    @SerializedName("id")
    @Column(column = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
