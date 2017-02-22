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

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *  签到
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年6月5日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QianDaoProtocol extends BaseModel {

    public QianDaoProtocol(Context context) {
        super(context);
    }

    /**
     * 登陆
     *
     * @param site 地址
     * @param comments 备注
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void qiandao(String uid, String site,String comments) {
        try {
            String url = ProtocolUrl.APP_QIANDAO;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", uid);
            params.put("site", site);
            params.put("comments", comments);
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
                    QianDaoProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                Object obj=ToolsJson.toJson(mBaseEntity.getData());
                if(obj==null){
                    QianDaoProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }
                String data = ToolsJson.toJson(mBaseEntity.getData());
                if (ToolsKit.isEmpty(data)) {
                    QianDaoProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                    return;
                }
                /**** end ****/
                if (url.endsWith(ProtocolUrl.APP_QIANDAO)) {
                    // 签到
                    QianDaoProtocol.this.OnMessageResponse(url, mBaseEntity, status);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
