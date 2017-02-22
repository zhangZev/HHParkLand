package com.henghao.parkland.views.popwindow;

import org.json.JSONException;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.views.dialog.BaseDialog;
import com.henghao.parkland.R;
import com.henghao.parkland.model.ascyn.BusinessResponse;
import com.henghao.parkland.model.protocol.LoginProtocol;

/**
 * 提示是否打电话的对话框 〈一句话功能简述〉 〈功能详细描述〉
 * @author why
 * @version HDMNV100R001, 2014年11月26日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CallDialog implements BusinessResponse {

	Context mContext;

	String mTel;

	private LoginProtocol mLoginProtocol;

	private String mCompanyId;

	public CallDialog(Context context, String tel) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mTel = tel;
	}

	public CallDialog(Context context, String tel, String companyId) {
		this.mContext = context;
		this.mTel = tel;
		this.mCompanyId = companyId;
		this.mLoginProtocol = new LoginProtocol(this.mContext);
		this.mLoginProtocol.addResponseListener(this);
	}

	public void show() {
		Resources mResources = this.mContext.getResources();
		String title = mResources.getString(R.string.account_cancel_hint);
		String message = mResources.getString(R.string.call_affirm);
		String message_last = mResources.getString(R.string.call_affirm_last);
		String ok = mResources.getString(R.string.ok);
		String cancel = mResources.getString(R.string.cancel);
		BaseDialog.getDialog(this.mContext, title, message + this.mTel + message_last, ok, this.callListener, cancel,
		        this.cancelListener).show();
	}

	DialogInterface.OnClickListener callListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + CallDialog.this.mTel));
			CallDialog.this.mContext.startActivity(intent);
			dialog.dismiss();
		}
	};

	DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			dialog.dismiss();
		}
	};

	@Override
	public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
		// TODO Auto-generated method stub
	}
}
