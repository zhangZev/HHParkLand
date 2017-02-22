/* 
 * 文件名：BusinessResponse.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.ascyn;

import org.json.JSONException;

import com.benefit.buy.library.http.query.callback.AjaxStatus;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2013-12-10
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface BusinessResponse {

    public abstract void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException;
}
