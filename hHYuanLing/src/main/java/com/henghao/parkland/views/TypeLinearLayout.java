/*
 * 文件名：TypeLinearLayout.java
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
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年10月2日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TypeLinearLayout extends LinearLayout {

	private ImageView mImageView;

	private TextView mTextView;

	private BitmapUtils mBitmapUtils;

	public TypeLinearLayout(Context context) {
		super(context, null);
		setOrientation(LinearLayout.VERTICAL);
		setId(R.id.common_activity_type);
		initTypeView(context);
	}

	public TypeLinearLayout(Context context, int slideStyle) {
		this(context, null);
		attachToActivity(context, slideStyle);
	}

	public TypeLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint({
	        "NewApi", "Recycle"
	})
	public TypeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOrientation(LinearLayout.VERTICAL);
		setId(R.id.common_activity_type);
		initTypeView(context);
	}

	private void initTypeView(Context context) {
		setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
		        android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
		// typeView = inflate(context, R.layout.common_view_bar_center, null);
		this.mImageView = new ImageView(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
		        android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		// 此处相当于布局文件中的Android:layout_gravity属性
		lp.gravity = Gravity.CENTER;
		this.mImageView.setLayoutParams(lp);
		this.mImageView.setScaleType(ScaleType.CENTER);
		// mImageView = (ImageView) typeView.findViewById(R.id.bar_center_img);
		// mTextView = (TextView) typeView.findViewById(R.id.bar_center_title);
		addView(this.mImageView);
		this.mTextView = new TextView(context);
		lp.setMargins(getResources().getDimensionPixelSize(R.dimen.padding_normal), getResources()
		        .getDimensionPixelSize(R.dimen.padding_normal),
		        getResources().getDimensionPixelSize(R.dimen.padding_normal), 0);
		this.mTextView.setLayoutParams(lp);
		this.mTextView.setGravity(Gravity.CENTER);
		this.mTextView.setTextColor(getResources().getColor(R.color.text_color_b));
		this.mTextView.setTextSize(12);
		addView(this.mTextView);
	}

	public void setTypeData(String title, int icon) {
		if (ToolsKit.isEmpty(title)) {
			return;
		}
		if (icon > 0) {
			this.mImageView.setImageResource(icon);
		}
		this.mTextView.setText(title);
	}

	public void setTypeData(String title, String url) {
		if (ToolsKit.isEmpty(title)) {
			return;
		}
		if (this.mBitmapUtils == null) {
			this.mBitmapUtils = new BitmapUtils(getContext(), Constant.CACHE_DIR_PATH);
			this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_empty_big);
			this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
		}
		if (!ToolsKit.isEmpty(url)) {
			this.mBitmapUtils.display(this.mImageView, url);
		}
		this.mTextView.setText(title);
	}

	/**
	 * 点击事件
	 * @param intent
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public void clickView(final Intent intent) {
		if (intent == null) {
			return;
		}
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getContext().startActivity(intent);
			}
		});
	}

	private void attachToActivity(Context context, int slideStyle) {
	}
}
