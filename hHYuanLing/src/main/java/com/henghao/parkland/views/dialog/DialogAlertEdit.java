package com.henghao.parkland.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.R;

public class DialogAlertEdit extends Dialog implements OnClickListener {
	private TextView dialog_ok;
	private TextView dialog_cancel;
	private final DialogAlertListener listener;
	private final String title;
	private String description;
	private Object param;
	private String s_cancel;
	private String s_ok;

	public interface DialogAlertListener {
		public void onDialogCreate(Dialog dlg, Object param);

		public void onDialogOk(Dialog dlg, Object param);

		public void onDialogCancel(Dialog dlg, Object param);
	}

	public DialogAlertEdit(Context context, DialogAlertListener listener, String title, String description,
	        String s_cancel, String s_ok) {
		super(context, R.style.dialog_alert);
		this.listener = listener;
		this.title = title;
		this.description = description;
		this.s_cancel = s_cancel;
		this.s_ok = s_ok;
	}

	public DialogAlertEdit(Context context, DialogAlertListener listener, String title, Object param) {
		super(context, R.style.dialog_alert);
		this.listener = listener;
		this.title = title;
		this.param = param;
	}

	private EditText m_edit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_alert_edit);
		setCanceledOnTouchOutside(false);
		setCancelable(false);

		this.dialog_ok = (TextView) findViewById(R.id.dialog_ok);
		this.dialog_ok.setOnClickListener(this);
		this.dialog_cancel = (TextView) findViewById(R.id.dialog_cancel);
		this.dialog_cancel.setOnClickListener(this);

		TextView txtTitle = (TextView) findViewById(R.id.dialog_title);
		txtTitle.setText(this.title);
		this.m_edit = (EditText) findViewById(R.id.m_edit);

		if (null != this.s_ok) {
			this.dialog_ok.setText(this.s_ok);
		}
		if (null != this.s_cancel) {
			this.dialog_cancel.setText(this.s_cancel);
		}

		if (this.listener != null) {
			this.listener.onDialogCreate(this, this.param);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == this.dialog_ok) {
			onBtnOk();
		}
		else if (v == this.dialog_cancel) {
			onBtnCancel();
		}
	}

	private void onBtnOk() {
		// cancel();
		if (this.listener != null) {
			this.listener.onDialogOk(this, ToolsKit.getEditText(this.m_edit));
		}
	}

	private void onBtnCancel() {
		cancel();
		if (this.listener != null) {
			this.listener.onDialogCancel(this, this.param);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBtnCancel();
		}
		return super.onKeyDown(keyCode, event);
	}
}
