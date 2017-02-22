package com.benefit.buy.library.views.anim;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.benefit.buy.library.utils.NSLog;

/**
 * 购物车动画 〈一句话功能简述〉 〈功能详细描述〉
 * @author why
 * @version HDMNV100R001, 2014年10月23日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AnimCart {

    Activity context;

    /**
     * 动画大小
     */
    int[] rect;

    public AnimCart(Activity context, int[] rect) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.rect = rect;
        animation_viewGroup = createAnimLayout();
    }

    //动画时间
    private int mAnimationDuration = 700;

    //正在执行的动画数量
    private int number = 0;

    //是否完成清理
    private boolean isClean = false;

    private FrameLayout animation_viewGroup;

    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //用来清除动画后留下的垃圾
                    try {
                        animation_viewGroup.removeAllViews();
                        NSLog.e("anim clean", "clean complete");
                    }
                    catch (Exception e) {
                        NSLog.e(this, e.getMessage());
                    }
                    isClean = false;
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 执行动画
     * @param drawable
     * @param start_location
     * @param end_location
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public void doAnim(Drawable drawable, int[] start_location, int[] end_location) {
        if (!isClean) {
            setAnim(drawable, start_location, end_location);
        }
        else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(drawable, start_location, end_location);
            }
            catch (Exception e) {
                NSLog.e(this, e.getMessage());
            }
            finally {
                isClean = true;
            }
        }
    }

    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) context.getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * @deprecated 将要执行动画的view 添加到动画层
     * @param vg 动画运行的层 这里是frameLayout
     * @param view 要运行动画的View
     * @param location 动画大小
     * @return
     */
    @Deprecated
    private View addViewToAnimLayout(ViewGroup vg, View view) {
        vg.addView(view);
        //        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(x, y);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(rect[0], rect[1]);
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 动画效果设置
     * @param drawable 将要加入购物车的商品
     * @param start_location 起始位置
     * @param end_location 结束位置
     */
    private void setAnim(Drawable drawable, int[] start_location, int[] end_location) {
        //渐变（大小）
        Animation mScaleAnimation = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_SELF, 0.1f,
                Animation.RELATIVE_TO_SELF, 0.1f);
        mScaleAnimation.setDuration(mAnimationDuration);
        mScaleAnimation.setFillAfter(true);
        //渐变（透明度）
        Animation mAlphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        mAlphaAnimation.setDuration(mAnimationDuration);
        mAlphaAnimation.setFillAfter(true);
        final ImageView iview = new ImageView(context);
        iview.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animation_viewGroup, iview);
        //       view.setAlpha(0.6f);
        //平移
        Animation mTranslateAnimationx = new TranslateAnimation(start_location[0], end_location[0], 0, 0);
        mTranslateAnimationx.setInterpolator(new LinearInterpolator());
        Animation mTranslateAnimationy = new TranslateAnimation(0, 0, start_location[1], end_location[1]);
        mTranslateAnimationy.setInterpolator(new AccelerateInterpolator());
        mTranslateAnimationx.setDuration(mAnimationDuration);
        mTranslateAnimationy.setDuration(mAnimationDuration);
        //旋转
        //        Animation mRotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
        //                Animation.RELATIVE_TO_SELF, 0.5f);
        //        mRotateAnimation.setDuration(mAnimationDuration);
        //        mTranslateAnimation.setInterpolator(new MyInterpolator());
        AnimationSet mAnimationSet = new AnimationSet(false);
        mAnimationSet.setFillAfter(true);
        //        mAnimationSet.addAnimation(mRotateAnimation);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mTranslateAnimationx);
        mAnimationSet.addAnimation(mTranslateAnimationy);
        //        mAnimationSet.addAnimation(mAlphaAnimation);
        mAnimationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                number--;
                if (number == 0) {
                    isClean = true;
                    myHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
        view.startAnimation(mAnimationSet);
    }

    /**
     * 内存过低时及时处理动画产生的未处理冗余
     */
    public void onLowMemory() {
        // TODO Auto-generated method stub
        isClean = true;
        try {
            animation_viewGroup.removeAllViews();
        }
        catch (Exception e) {
            NSLog.e(this, e.getMessage());
        }
        isClean = false;
        context.onLowMemory();
    }
}
