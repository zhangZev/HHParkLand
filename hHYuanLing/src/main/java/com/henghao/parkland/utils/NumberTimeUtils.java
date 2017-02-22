/* 
 * 文件名：NumberTimeUtils.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * qinyulun 在activity中，finish（）方法要调用stopTimer方法〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年11月28日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NumberTimeUtils {

    private OnTimeCallBack mOnTimeCallBack;

    public int TIMER = 0;

    /**
     * 判断是否重新发送 false
     */
    private boolean isClickSend = false;

    /**
     * 开始计时 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void startTimer() {
        isClickSend = true;
        mHandler.post(mRunnable);
    }

    /**
     * 停止计时 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void stopTimer() {
        isClickSend = false;
        if (mOnTimeCallBack != null) {
            mOnTimeCallBack.stopHandler();
        }
        mHandler.removeCallbacks(mRunnable);
    }

    Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            //            mHandler.postDelayed(mRunnable, 1000);
            mHandler.sendEmptyMessage(1);
        }
    };

    public boolean isClickSend() {
        return isClickSend;
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TIMER--;
            if (TIMER <= 0) {
                //可以点击
                isClickSend = false;
                mHandler.removeCallbacks(mRunnable);
                if (mOnTimeCallBack != null) {
                    mOnTimeCallBack.stopHandler();
                }
            }
            else {
                //不可以点击
                isClickSend = true;
                mHandler.postDelayed(mRunnable, 1000);
                StringBuffer buffer = new StringBuffer();
                buffer.append("(").append(TIMER).append(")");
                if (mOnTimeCallBack != null) {
                    mOnTimeCallBack.runHandler(TIMER);
                }
            }
        }
    };

    public void setNumberTime(int time) {
        TIMER = time;
    }

    public void numberFormatTime(String data) {
        try {
            TIMER = Integer.parseInt(data);
        }
        catch (NumberFormatException e) {
            TIMER = 0;
        }
    }

    public OnTimeCallBack getOnTimeCallBack() {
        return mOnTimeCallBack;
    }

    public void setOnTimeCallBack(OnTimeCallBack onTimeCallBack) {
        mOnTimeCallBack = onTimeCallBack;
    }

    public interface OnTimeCallBack {

        /**
         * 当定时器停止的时候调用
         * @see [类、类#方法、类#成员]
         * @since [产品/模块版本]
         */
        public void stopHandler();

        /**
         * 定时器运行的时候传递时间过去
         * @param time
         * @see [类、类#方法、类#成员]
         * @since [产品/模块版本]
         */
        public void runHandler(int time);
    }
}
