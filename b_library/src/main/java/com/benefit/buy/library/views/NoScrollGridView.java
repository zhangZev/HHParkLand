package com.benefit.buy.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context paramContext) {
        super(paramContext);
    }

    public NoScrollGridView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public NoScrollGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
