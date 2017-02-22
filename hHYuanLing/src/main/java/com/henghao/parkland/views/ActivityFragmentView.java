/*
 * 文件名：ActivityFragmentView.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.R;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年5月1日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ActivityFragmentView extends LinearLayout {

	/**
	 * 主视图父线性布局
	 */
	private LinearLayout mainRootLinearLayout;

	/**
	 * 空的或者异常父线性布局
	 */
	private LinearLayout emptyRootLinearLayout;

	/**
	 * activity的内容控制器
	 */
	private View viewMain;

	/**
	 * 空视图
	 */
	private View emptyMain;

	/**
	 * 导航栏视图
	 */
	private View navitionBarView;

	private View viewLoading;

	public ActivityFragmentView(Context context) {
		this(context, null);
	}

	public ActivityFragmentView(Context context, int slideStyle) {
		this(context, null);
		attachToActivity(context, slideStyle);
	}

	public ActivityFragmentView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint({
	        "NewApi", "Recycle"
	})
	public ActivityFragmentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOrientation(LinearLayout.VERTICAL);
		setId(R.id.common_activity);
		// setBackgroundColor(getResources().getColor(R.color.app_bg));
		initNavationBar(context);
		initContent(context, attrs, defStyleAttr);
	}

	@SuppressLint("NewApi")
	public void clipToPadding(boolean isFlag) {
		setClipToPadding(isFlag);
		setFitsSystemWindows(isFlag);
	}

	private void initNavationBar(Context context) {
		this.navitionBarView = inflate(context, R.layout.common_view_navigation_bar, null);
		this.navitionBarView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
		        ToolsKit.dip2px(context, 55)));
		addView(this.navitionBarView, 0);
	}

	@SuppressLint("Recycle")
	private void initContent(Context context, AttributeSet attrs, int defStyleAttr) {
		FrameLayout mFrameLayout = new FrameLayout(context);
		mFrameLayout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
		        android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		mFrameLayout.setId(R.id.common_activity_frament);
		addView(mFrameLayout, 1);
		this.emptyRootLinearLayout = new LinearLayout(context);
		this.emptyRootLinearLayout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
		        android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		this.emptyRootLinearLayout.setOrientation(LinearLayout.VERTICAL);
		this.emptyRootLinearLayout.setId(R.id.activity_data_empty);
		mFrameLayout.addView(this.emptyRootLinearLayout, 0);
		this.mainRootLinearLayout = new LinearLayout(context);
		this.mainRootLinearLayout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
		        android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		this.mainRootLinearLayout.setOrientation(LinearLayout.VERTICAL);
		this.mainRootLinearLayout.setId(R.id.activity_main);
		mFrameLayout.addView(this.mainRootLinearLayout, 1);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewDataEmpty);
		int vMain = ta.getResourceId(R.styleable.ViewDataEmpty_viewMain, -1);
		if (vMain != -1) {
			this.viewMain = inflate(context, vMain, this.mainRootLinearLayout);
		}
		int vEmpty = ta.getResourceId(R.styleable.ViewDataEmpty_viewEmpty, -1);
		if (vMain != -1) {
			this.emptyMain = inflate(context, vEmpty, this.emptyRootLinearLayout);
		}
		this.viewLoading = inflate(context, R.layout.common_view_stub_loading, null);
		mFrameLayout.addView(this.viewLoading, 2);
		this.viewLoading.setVisibility(View.GONE);
	}

	public void setMainBackgroundColor(int color) {
		if (this.mainRootLinearLayout != null) {
			this.mainRootLinearLayout.setBackgroundColor(color);
		}
	}

	public void viewMain(View view) {
		if (this.mainRootLinearLayout != null) {
			this.viewMain = view;
			this.mainRootLinearLayout.addView(view);
		}
	}

	public void viewMain(int viewId) {
		if (this.mainRootLinearLayout != null) {
			this.viewMain = inflate(getContext(), viewId, this.mainRootLinearLayout);
		}
	}

	public void viewEmpty(View view) {
		if (this.emptyRootLinearLayout != null) {
			this.emptyMain = view;
			this.emptyRootLinearLayout.addView(view);
		}
	}

	public void viewEmpty(int viewId) {
		if (this.emptyRootLinearLayout != null) {
			this.emptyMain = inflate(getContext(), viewId, this.emptyRootLinearLayout);
		}
	}

	public void viewEmptyGone() {
		this.emptyMain.setVisibility(View.GONE);
		this.viewMain.setVisibility(View.VISIBLE);
	}

	public void viewMainGone() {
		this.emptyMain.setVisibility(View.VISIBLE);
		this.viewMain.setVisibility(View.GONE);
	}

	public void viewLoading(int visibility) {
		this.viewLoading.setVisibility(visibility);
	}

	public View getViewMain() {
		return this.viewMain;
	}

	public View getEmptyMain() {
		return this.emptyMain;
	}

	public View getNavitionBarView() {
		return this.navitionBarView;
	}

	private void attachToActivity(Context context, int slideStyle) {
	}
}
