/*
 * 文件名：LoginFilfterProtocol.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.protocol;

import android.content.Context;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BaseModel;
import com.henghao.parkland.model.ascyn.BeeCallback;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.UserLoginEntity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author qyl
 * @version HDMNV100R001, 2015年6月5日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LoginProtocol extends BaseModel {

    public LoginProtocol(Context context) {
        super(context);
    }

    /**
     * 登陆
     *
     * @param userName
     * @param password
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void login(String userName, String password) {
        try {
            String url = ProtocolUrl.APP_LOGIN;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("username", userName);
            params.put("password", password);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册
     *
     * @param userName
     * @param password
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void reg_user(String userName, String password, String phone) {
        try {
            String url = ProtocolUrl.APP_REG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("username", userName);
            params.put("password", password);
            params.put("tel", phone);
            /*String str = ToolsKit.getParams(params);
			String paramVal = ToolsSecret.encode(str);
			HashMap<String, String> postParams = new HashMap<String, String>();
			postParams.put("params", paramVal);*/
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final BeeCallback<String> mBeeCallback = new BeeCallback<String>() {

        @Override
        public void callback(String url, String object, AjaxStatus status) {
            try {
                /**** start ****/
                BaseEntity mBaseEntity = ToolsJson.parseObjecta(object, BaseEntity.class);
                if (mBaseEntity == null) {
                    LoginProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                if(mBaseEntity.getData()==null || mBaseEntity.getData()=="null"){
                    LoginProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                String data = ToolsJson.toJson(mBaseEntity.getData());
                if (ToolsKit.isEmpty(data)) {
                    LoginProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                /**** end ****/
                if (url.endsWith(ProtocolUrl.APP_LOGIN)) {
                    // 登录
                    JSONObject mJson=new JSONObject(object);
                    JSONObject mData=mJson.getJSONObject("data");
                    UserLoginEntity loginEntity = ToolsJson.parseObjecta(mData.toString(), UserLoginEntity.class);
                    LoginProtocol.this.OnMessageResponse(url, loginEntity, status);
                }
                if (url.endsWith(ProtocolUrl.APP_REG)) {
                    // 登录
                    LoginProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
