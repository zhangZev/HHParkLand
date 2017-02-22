package com.benefit.buy.library.views;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 点击链接事件 改超链接颜色
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-9
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class IntentSpan extends ClickableSpan {

    private final OnClickListener mOnClickListener;

    public IntentSpan(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        mOnClickListener.onClick(view);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(true);
    }
}
