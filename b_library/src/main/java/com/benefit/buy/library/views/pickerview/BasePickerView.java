package com.benefit.buy.library.views.pickerview;

import com.benefit.buy.library.R;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

/**
 * Created by Sai on 15/11/22. 精仿iOSPickerViewController控件
 */
public class BasePickerView {

    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

    private final Context context;

    protected ViewGroup contentContainer;

    private ViewGroup decorView;//activity的根View

    private ViewGroup rootView;//附加View 的 根View

    private OnDismissListener onDismissListener;

    private boolean isDismissing;

    private Animation outAnim;

    private Animation inAnim;

    private final int gravity = Gravity.BOTTOM;

    public BasePickerView(Context context) {
        this.context = context;
        initViews();
        init();
        initEvents();
    }

    protected void initViews() {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        this.decorView = (ViewGroup) ((Activity) this.context).getWindow().getDecorView()
                .findViewById(android.R.id.content);
        this.rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, this.decorView, false);
        this.rootView.setLayoutParams(
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.contentContainer = (ViewGroup) this.rootView.findViewById(R.id.content_container);
        this.contentContainer.setLayoutParams(this.params);
    }

    protected void init() {
        this.inAnim = getInAnimation();
        this.outAnim = getOutAnimation();
    }

    protected void initEvents() {
    }

    /**
     * show的时候调用
     * @param view 这个View
     */
    private void onAttached(View view) {
        this.decorView.addView(view);
        this.contentContainer.startAnimation(this.inAnim);
    }

    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        if (isShowing()) {
            return;
        }
        onAttached(this.rootView);
    }

    /**
     * 检测该View是不是已经添加到根视图
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        View view = this.decorView.findViewById(R.id.outmost_container);
        return view != null;
    }

    public void dismiss() {
        if (this.isDismissing) {
            return;
        }
        //消失动画
        this.outAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                BasePickerView.this.decorView.post(new Runnable() {

                    @Override
                    public void run() {
                        //从activity根视图移除
                        BasePickerView.this.decorView.removeView(BasePickerView.this.rootView);
                        BasePickerView.this.isDismissing = false;
                        if (BasePickerView.this.onDismissListener != null) {
                            BasePickerView.this.onDismissListener.onDismiss(BasePickerView.this);
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.contentContainer.startAnimation(this.outAnim);
        this.isDismissing = true;
    }

    public Animation getInAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(this.context, res);
    }

    public Animation getOutAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(this.context, res);
    }

    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public BasePickerView setCancelable(boolean isCancelable) {
        View view = this.rootView.findViewById(R.id.outmost_container);
        if (isCancelable) {
            view.setOnTouchListener(this.onCancelableTouchListener);
        }
        else {
            view.setOnTouchListener(null);
        }
        return this;
    }

    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    public View findViewById(int id) {
        return this.contentContainer.findViewById(id);
    }
}
