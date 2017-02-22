package com.benefit.buy.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自动计算高度的listview 〈一句话功能简述〉 〈功能详细描述〉
 * @author why
 * @version HDMNV100R001, 2014年9月29日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NoScrollListView extends ListView {

    public NoScrollListView(Context paramContext) {
        super(paramContext);
    }

    public NoScrollListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public NoScrollListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
