/* 
 * 文件名：NSLog.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.benefit.buy.library.utils;

import android.util.Log;

/**
 * 打印日志〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-22
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NSLog {

    private static boolean onLog = false;

    public static final void e(Object obj, String logMsg) {
        if (onLog) {
            Log.e(obj.getClass().getSimpleName(), logMsg);
        }
    }

    public static final void e(Object obj, String logMsg, Exception e) {
        if (onLog) {
            Log.e(obj.getClass().getSimpleName(), logMsg, e);
        }
    }

    public static final void w(Object obj, String logMsg) {
        if (onLog) {
            Log.w(obj.getClass().getSimpleName(), logMsg);
        }
    }

    public static final void w(Object obj, String logMsg, Exception e) {
        if (onLog) {
            Log.w(obj.getClass().getSimpleName(), logMsg, e);
        }
    }

    public static final void v(Object obj, String logMsg) {
        if (onLog) {
            Log.v(obj.getClass().getSimpleName(), logMsg);
        }
    }

    public static final void v(Object obj, String logMsg, Exception e) {
        if (onLog) {
            Log.w(obj.getClass().getSimpleName(), logMsg, e);
        }
    }

    public static final void i(Object obj, String logMsg) {
        if (onLog) {
            Log.i(obj.getClass().getSimpleName(), logMsg);
        }
    }

    public static final void i(Object obj, String logMsg, Exception e) {
        if (onLog) {
            Log.w(obj.getClass().getSimpleName(), logMsg, e);
        }
    }

    public static final void d(Object obj, String logMsg) {
        if (onLog) {
            Log.i(obj.getClass().getSimpleName(), logMsg);
        }
    }

    public static final void d(Object obj, String logMsg, Exception e) {
        if (onLog) {
            Log.w(obj.getClass().getSimpleName(), logMsg, e);
        }
    }
}
