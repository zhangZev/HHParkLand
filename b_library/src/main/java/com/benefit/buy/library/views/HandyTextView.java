package com.benefit.buy.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * textview 空指针异常处理
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-9
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HandyTextView extends TextView {

    public HandyTextView(Context context) {
        super(context);
    }

    public HandyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HandyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text == null) {
            text = "";
        }
        super.setText(text, type);
    }
}
