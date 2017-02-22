/* 
 * 文件名：ToolsToast.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.benefit.buy.library.utils.tools;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.benefit.buy.library.R;
import com.benefit.buy.library.views.HandyTextView;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-10
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ToolsToast {

    /**
     * 短暂的toast 自定义字符
     * @param context
     * @param content
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static void show(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 线程 短暂的toast 自定义字符
     * @param context
     * @param content
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static void show(final Activity context, final String content) {
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 短暂的toast 资源文件
     * @param context
     * @param content
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static void show(Context context, int resId) {
        Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 线程 短暂的toast 资源文件
     * @param context
     * @param content
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static void show(final Activity context, final int resId) {
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** 长时间显示Toast提示(来自res) **/
    protected void showLong(Context context, int resId) {
        Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_LONG).show();
    }

    /** 长时间显示Toast提示(来自res) **/
    protected void showLong(final Activity context, final int resId) {
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_LONG).show();
            }
        });
    }

    /** 长时间显示Toast提示(来自String) **/
    protected void showLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /** 长时间显示Toast提示(来自String) **/
    protected void showLong(final Activity context, final String text) {
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 显示自定义Toast提示(来自res)
     * @param resId
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    protected void showCustom(Activity context, int resId) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
        ((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(context.getResources().getString(resId));
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    /**
     * 显示自定义Toast提示(来自String)
     * @param context
     * @param text
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    protected void showCustomToast(Activity context, String text) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
        ((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }
}
