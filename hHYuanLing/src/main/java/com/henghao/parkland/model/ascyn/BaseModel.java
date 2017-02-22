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
package com.henghao.parkland.model.ascyn;

import java.util.ArrayList;

import org.json.JSONException;

import com.benefit.buy.library.http.query.callback.AjaxStatus;

import android.content.Context;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2013-12-10
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseModel implements BusinessResponse {

    protected BeeQuery aq;

    protected ArrayList<BusinessResponse> businessResponseArrayList = new ArrayList<BusinessResponse>();

    protected Context mContext;

    public BaseModel() {
    }

    @SuppressWarnings("rawtypes")
    public BaseModel(Context context) {
        aq = new BeeQuery(context);
        mContext = context;
    }

    protected void saveCache() {
        return;
    }

    protected void cleanCache() {
        return;
    }

    public void addResponseListener(BusinessResponse listener) {
        if (!businessResponseArrayList.contains(listener)) {
            businessResponseArrayList.add(listener);
        }
    }

    public void removeResponseListener(BusinessResponse listener) {
        businessResponseArrayList.remove(listener);
    }

    //    public void callback(String url, Object object, AjaxStatus status) {
    //        IActivitySupport mActivity = (IActivitySupport) mContext;
    //        String sid = mActivity.getLoginUserSharedPre().getString(Constant.USER_SID, "");
    //        String uid = mActivity.getLoginUserSharedPre().getString(Constant.USER_UID, "");
    //        if (ToolsKit.isEmpty(sid) || ToolsKit.isEmpty(uid)) {
    //            Intent intent = new Intent(mContext, LoginActivity.class);
    //            intent.putExtra(Constant.CLEAR_USER_INFO, Constant.CLEAR_USER_INFO_VAL);
    //            mContext.startActivity(intent);
    //        }
    //    }
    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        for (BusinessResponse iterable_element: businessResponseArrayList) {
            iterable_element.OnMessageResponse(url, jo, status);
        }
    }
}
