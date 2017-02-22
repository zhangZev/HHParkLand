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
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BaseModel;
import com.henghao.parkland.model.ascyn.BeeCallback;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.SGWalletEntity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author 张宪文
 * @version HDMNV100R001, 2017年2月20日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectProtocol extends BaseModel {

    public ProjectProtocol(Context context) {
        super(context);
    }

    /**
     * 施工备忘提交
     *
     * @param projectName
     * @param projectDetail
     * @param uid
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void upLoadSGBW(String projectName, String projectDetail, String uid) {
        try {
            String url = ProtocolUrl.PROJECT_SGBEIWANG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("projectName", projectName);
            params.put("projectDetail", projectDetail);
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 工作备忘提交
     *
     * @param startTime
     * @param endTime
     * @param content
     * @param uid
     */
    public void upLoadWorkBW(String startTime, String endTime, String content, String uid) {
        try {
            String url = ProtocolUrl.PROJECT_WORKBEIWANG;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("content", content);
            params.put("uid", uid);
            this.mBeeCallback.url(url).type(String.class).params(params);
            this.aq.ajax(this.mBeeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 施工钱包查询
     * @param uid
     */
    public void querySGWallet(String uid) {
        try {
            String url = ProtocolUrl.PROJECT_SGWALLET;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
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
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                if (mBaseEntity.getData() == null || mBaseEntity.getData() == "null") {
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                String data = ToolsJson.toJson(mBaseEntity.getData());
                if (ToolsKit.isEmpty(data)) {
                    ProjectProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                /**** end ****/
                if (url.endsWith(ProtocolUrl.PROJECT_SGWALLET)) {
                    // 查询施工钱包信息
                    Type type = new TypeToken<List<SGWalletEntity>>() {
                    }.getType();
                    List<SGWalletEntity> homeData = ToolsJson.parseObjecta(data, type);
                    ProjectProtocol.this.OnMessageResponse(url, homeData, status);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
