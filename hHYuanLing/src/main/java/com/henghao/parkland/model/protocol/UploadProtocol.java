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

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.NSLog;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsSecret;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BaseModel;
import com.henghao.parkland.model.ascyn.BeeCallback;

import android.content.Context;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UploadProtocol extends BaseModel {

    public UploadProtocol(Context context) {
        super(context);
    }

    /**
     * 上传错误日志到服务器
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    @SuppressWarnings("unchecked")
    public void uploadWithErrorServer(String str) {
        try {
            String url = ProtocolUrl.UPLOAD_ERROR_SERVER;
            HashMap<String, String> postParams = new HashMap<String, String>();
            if (!ToolsKit.isEmpty(str)) {
                String paramVal = ToolsSecret.encode(str);
                postParams.put("params", paramVal);
            }
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
                if (url.endsWith(ProtocolUrl.UPLOAD_ERROR_SERVER)) {
                    //首页模块数据
                    //                    UploadProtocol.this.OnMessageResponse(url, mTemplateConfig, status);
                }
            }
            catch (Exception e) {
                NSLog.e(this, e.getMessage());
            }
        }
    };
}
