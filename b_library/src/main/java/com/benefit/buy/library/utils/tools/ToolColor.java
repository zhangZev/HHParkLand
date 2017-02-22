package com.benefit.buy.library.utils.tools;

import android.graphics.Color;

/**
 * 颜色处理 〈一句话功能简述〉 〈功能详细描述〉
 * @author why
 * @version HDMNV100R001, 2014年9月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ToolColor {

    /**
     * 颜色渐变 〈一句话功能简述〉 〈功能详细描述〉
     * @param rfrom 初始颜色rgb
     * @param gfrom 初始颜色rgb
     * @param bfrom 初始颜色rgb
     * @param rto 要变的颜色rgb
     * @param gto 要变的颜色rgb
     * @param bto 要变的颜色rgb
     * @param change 变化率(百分比)
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static int getChangeColor(int rfrom, int gfrom, int bfrom, int rto, int gto, int bto,
            float change) {
        int rDif = rto - rfrom;
        int gDif = gto - gfrom;
        int bDif = bto - bfrom;
        int rCur, gCur, bCur;
        if (change != 0) {
            rCur = rfrom + (short) (rDif * change);
            gCur = gfrom + (short) (gDif * change);
            bCur = bfrom + (short) (bDif * change);
            return Color.rgb(rCur, gCur, bCur);
        }
        else {
            return Color.rgb(rfrom, gfrom, bfrom);
        }
    }
}
