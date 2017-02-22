/* 
 * 文件名：IActivitySupport.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

/**
 * 公用的activity接口〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IActivitySupport {

    /**
     * 获取Application.〈一句话功能简述〉 〈功能详细描述〉
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract FMApplication getFMApplication();

    /**
     * 终止服务. 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract void stopService();

    /**
     * 开启服务.
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract void startService();

    /**
     * 校验网络-如果没有网络就弹出设置,并返回true.
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract boolean validateInternet();

    /**
     * 校验网络-如果没有网络就返回true.
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract boolean hasInternetConnected();

    /**
     * 退出应用.
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract void isExit();

    /**
     * 判断GPS是否已经开启.
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract boolean hasLocationGPS();

    /**
     * 判断基站是否已经开启.
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract boolean hasLocationNetWork();

    /**
     * 检查内存卡.
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract void checkMemoryCard();

    /**
     * 返回当前Activity上下文.
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public abstract Context getContext();

    /**
     * 获取当前登录用户的SharedPreferences配置.
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public SharedPreferences getLoginUserSharedPre();

    /**
     * 取得用户信息 〈一句话功能简述〉 〈功能详细描述〉
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public String getLoginUser();

    /**
     * 关闭键盘 〈一句话功能简述〉 〈功能详细描述〉
     * @param mEdit
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void closeKeyBoard(EditText mEdit);

    /**
     * 关闭键盘 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void closeInput();

    /**
     * 初始化控件
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void initWidget();

    /**
     * 初始化数据
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void initData();

    String getLoginUid();

    String getLoginUserPhone();

    String getLoginState();
}
