package com.henghao.parkland.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.henghao.parkland.R;

public class DialogYanghu extends Dialog implements OnClickListener {
    private TextView dialog_ok;
    private TextView dialog_cancel;
    private final DialogAlertListener listener;

    public interface DialogAlertListener {
        public void onDialogCreate(Dialog dlg);

        public void onDialogOk(Dialog dlg);

        public void onDialogCancel(Dialog dlg);
    }

    public DialogYanghu(Context context, DialogAlertListener listener) {
        super(context, R.style.dialog_alert);
        this.listener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert_yanghu);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        this.dialog_ok = (TextView) findViewById(R.id.dialog_ok);
        this.dialog_ok.setOnClickListener(this);
        this.dialog_cancel = (TextView) findViewById(R.id.dialog_cancel);
        this.dialog_cancel.setOnClickListener(this);

        TextView txtTitle = (TextView) findViewById(R.id.dialog_title);

        if (this.listener != null) {
            this.listener.onDialogCreate(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == this.dialog_ok) {
            onBtnOk();
            //养护
        } else if (v == this.dialog_cancel) {
            onBtnCancel();
            //录入
        }
    }

    private void onBtnOk() {
        // cancel();
        if (this.listener != null) {
            this.listener.onDialogOk(this);
        }
    }

    private void onBtnCancel() {
        cancel();
        if (this.listener != null) {
            this.listener.onDialogCancel(this);
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
