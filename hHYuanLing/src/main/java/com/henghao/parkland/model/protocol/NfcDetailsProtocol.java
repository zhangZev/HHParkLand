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

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.ascyn.BaseModel;
import com.henghao.parkland.model.ascyn.BeeCallback;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年6月5日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NfcDetailsProtocol extends BaseModel {

	public NfcDetailsProtocol(Context context) {
		super(context);
	}

	/**
	 * 获取NFC标签信息
	 * @param nfcId
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public void getNfcById(String nfcId) {
		try {
			String url = ProtocolUrl.APP_GET_NFCBYID;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nfcId", nfcId);
			this.mBeeCallback.url(url).type(String.class).params(params);
			this.aq.ajax(this.mBeeCallback);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final BeeCallback<String> mBeeCallback = new BeeCallback<String>() {

		@Override
		public void callback(String url, String object, AjaxStatus status) {
			try {
				/**** start ****/
//				BaseEntity mBaseEntity = ToolsJson.parseObjecta(object, BaseEntity.class);
//				if (mBaseEntity == null) {
//					NfcDetailsProtocol.this.OnMessageResponse(url, mBaseEntity, status);
//					return;
//				}
//				String data =object;
//				if (ToolsKit.isEmpty(data)) {
//					NfcDetailsProtocol.this.OnMessageResponse(url, mBaseEntity, status);
//					return;
//				}
				/**** end ****/
				if (url.endsWith(ProtocolUrl.APP_GET_NFCBYID)) {
//					String dataConfig = ToolsSecret.decode(data);
					NfcDetailsProtocol.this.OnMessageResponse(url, object, status);
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
