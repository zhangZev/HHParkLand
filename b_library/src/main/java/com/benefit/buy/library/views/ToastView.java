package com.benefit.buy.library.views;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.R;

/**
 * 自定义toastview
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-10
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ToastView {

    private Toast toast;

    private int time;

    private Timer timer;

    private FlippingImageView mFivIcon;

    private ToastView(Context context, String text) {
        //        LayoutInflater inflater = LayoutInflater.from(context);
        //        View view = inflater.inflate(R.layout.toast_view, null);
        //        TextView t = (TextView) view.findViewById(R.id.toast_text);
        //        mFivIcon = (FlippingImageView) view.findViewById(R.id.flipping);
        //        t.setText(text);
        if (toast != null) {
            toast.cancel();
        }
        //        toast = new Toast(context);
        //        toast.setDuration(Toast.LENGTH_SHORT);
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        //        toast.setText(text);
        //        toast.setView(view);
    }

    public static ToastView makeText(Context context, String text) {
        ToastView mToastView = new ToastView(context, text);
        return mToastView;
    }

    public static ToastView makeText(Context context, int text) {
        ToastView mToastView = new ToastView(context, text);
        return mToastView;
    }

    private ToastView(Context context, int text) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_view, null);
        TextView t = (TextView) view.findViewById(R.id.toast_text);
        mFivIcon = (FlippingImageView) view.findViewById(R.id.flipping);
        t.setText(text);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
    }

    //设置toast显示位置
    public ToastView setGravity(int gravity, int xOffset, int yOffset) {
        //toast.setGravity(Gravity.CENTER, 0, 0); //居中显示
        toast.setGravity(gravity, xOffset, yOffset);
        return this;
    }

    //设置toast显示时间
    public void setDuration(int duration) {
        toast.setDuration(duration);
    }

    //设置toast显示时间(自定义时间)
    public void setLongTime(int duration) {
        //toast.setDuration(duration);
        time = duration;
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if ((time - 1000) >= 0) {
                    show();
                    time = time - 1000;
                }
                else {
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    public void show() {
        //        mFivIcon.startAnimation();
        toast.show();
    }

    public void cancel() {
        if (toast != null) {
            //            mFivIcon.clearAnimation();
            toast.cancel();
        }
    }
}
