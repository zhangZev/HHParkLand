/* 
 * 文件名：UploadProtocol.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.protocol;

import java.util.HashMap;
import java.util.Map;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.NSLog;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsSecret;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BaseModel;
import com.henghao.parkland.model.ascyn.BeeCallback;
import com.henghao.parkland.model.entity.AppGuideEntity;
import com.henghao.parkland.model.entity.AppStartEntity;
import com.henghao.parkland.model.entity.AppVersionEntity;
import com.henghao.parkland.model.entity.BaseEntity;

import android.content.Context;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SystemProtocol extends BaseModel {

    public SystemProtocol(Context context) {
        super(context);
    }

    /**
     * app启动页面 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    @SuppressWarnings("unchecked")
    public void appStart() {
        try {
            String url = ProtocolUrl.APP_START;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("startType", Constant.APP_SYS_ANDROID);
            String str = ToolsKit.getParams(params);
            String paramVal = ToolsSecret.encode(str);
            HashMap<String, String> postParams = new HashMap<String, String>();
            postParams.put("params", paramVal);
            mBeeCallback.url(url).type(String.class).params(postParams);
            aq.ajax(mBeeCallback);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * app引导页面 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    @SuppressWarnings("unchecked")
    public void appGuide() {
        try {
            String url = ProtocolUrl.APP_GUIDE;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("guideType", Constant.APP_SYS_ANDROID + "");
            String str = ToolsKit.getParams(params);
            String paramVal = ToolsSecret.encode(str);
            HashMap<String, String> postParams = new HashMap<String, String>();
            postParams.put("params", paramVal);
            mBeeCallback.url(url).type(String.class).params(postParams);
            aq.ajax(mBeeCallback);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * app版本更新
     * @param sysType
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    @SuppressWarnings("unchecked")
    public void appSystemUpdate(String deviceInfo) {
        try {
            String url = ProtocolUrl.APP_SYS_UPDATE;
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("deviceInfo", deviceInfo);
            String str = ToolsKit.getParams(params);
            String paramVal = ToolsSecret.encode(str);
            HashMap<String, String> postParams = new HashMap<String, String>();
            postParams.put("params", paramVal);
            mBeeCallback.url(url).type(String.class).params(postParams);
            aq.ajax(mBeeCallback);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BeeCallback<String> mBeeCallback = new BeeCallback<String>() {

        @Override
        public void callback(String url, String object, AjaxStatus status) {
            try {
                /**** start ****/
                BaseEntity mBaseEntity = ToolsJson.parseObjecta(object, BaseEntity.class);
                if (mBaseEntity == null) {
                    SystemProtocol.this.OnMessageResponse(url, null, status);
                    return;
                }
                Object obj = mBaseEntity.getData();
                if(obj==null){
                    SystemProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                String data=obj.toString();
                if (ToolsKit.isEmpty(data)) {
                    SystemProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                /**** end ****/
                if (url.endsWith(ProtocolUrl.APP_START)) {
                    String dataConfig = ToolsSecret.decode(data);
                    AppStartEntity appStart = ToolsJson.parseObjecta(dataConfig, AppStartEntity.class);
                    SystemProtocol.this.OnMessageResponse(url, appStart, status);
                }
                if (url.endsWith(ProtocolUrl.APP_GUIDE)) {
                    String dataConfig = ToolsSecret.decode(data);
                    AppGuideEntity appStart = ToolsJson.parseObjecta(dataConfig, AppGuideEntity.class);
                    SystemProtocol.this.OnMessageResponse(url, appStart, status);
                }
                else if (url.endsWith(ProtocolUrl.APP_SYS_UPDATE)) {
                    String dataConfig = ToolsSecret.decode(data);
                    AppVersionEntity appVersion = ToolsJson.parseObjecta(dataConfig, AppVersionEntity.class);
                    SystemProtocol.this.OnMessageResponse(url, appVersion, status);
                }
            }
            catch (Exception e) {
                NSLog.e(this, e.getMessage());
            }
        }
    };
}
