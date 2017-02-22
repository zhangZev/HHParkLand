package com.benefit.buy.library.views.dialog;

import android.content.Context;

import com.benefit.buy.library.R;
import com.benefit.buy.library.views.FlippingImageView;
import com.benefit.buy.library.views.HandyTextView;

/**
 * 弹出提示框
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-10
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FlippingLoadingDialog extends BaseDialog {

    private FlippingImageView mFivIcon;

    private HandyTextView mHtvText;

    private String mText;

    public FlippingLoadingDialog(Context context, String text) {
        super(context);
        mText = text;
        init();
    }

    private void init() {
        setContentView(R.layout.common_flipping_loading_diloag);
        mFivIcon = (FlippingImageView) findViewById(R.id.loadingdialog_fiv_icon);
        mHtvText = (HandyTextView) findViewById(R.id.loadingdialog_htv_text);
        mFivIcon.startAnimation();
        mHtvText.setText(mText);
    }

    public void setText(String text) {
        mText = text;
        mHtvText.setText(mText);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}
