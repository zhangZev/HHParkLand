/*
 * 文件名：GuideAdapter.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.adapter;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.MainActivity;
import com.henghao.parkland.model.entity.AppGuideEntity;
import com.lidroid.xutils.BitmapUtils;

/**
 * 引导页
 * @author qyl
 * @version HDMNV100R001, 2015-4-28
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GuideAdapter extends PagerAdapter {

	private final LayoutInflater inflater;

	private final ActivityFragmentSupport mActivityFragment;

	private final BitmapUtils mBitmapUtils;

	private List<AppGuideEntity> mAppGuides;

	private int[] appGuides;

	public GuideAdapter(ActivityFragmentSupport activityFragment, List<AppGuideEntity> appGuides) {
		super();
		this.mActivityFragment = activityFragment;
		this.inflater = LayoutInflater.from(this.mActivityFragment);
		this.mBitmapUtils = new BitmapUtils(this.mActivityFragment, Constant.CACHE_DIR_PATH);
		this.mAppGuides = appGuides;
	}

	public GuideAdapter(ActivityFragmentSupport activityFragment, int[] guides) {
		super();
		this.mActivityFragment = activityFragment;
		this.inflater = LayoutInflater.from(this.mActivityFragment);
		this.mBitmapUtils = new BitmapUtils(this.mActivityFragment, Constant.CACHE_DIR_PATH);
		this.appGuides = guides;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = this.inflater.inflate(R.layout.common_item_guide, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.item_guide_image);
		Button button = (Button) view.findViewById(R.id.item_guide_btn);
		boolean isStartFirst = this.mActivityFragment.getLoginUserSharedPre().getBoolean(Constant.IS_APP_START_FIRST,
		        false);
		if (isStartFirst) {
			// 第一次啟動,服务的启动
			AppGuideEntity guideImg = this.mAppGuides.get(position);
			// TODO qin ProtocolUrl.APP_SYSTEM_IMG_URL +
			this.mBitmapUtils.display(imageView, guideImg.getGuideImg());
			((ViewPager) container).addView(view, 0);
			if (position >= (this.mAppGuides.size() - 1)) {
				button.setVisibility(View.VISIBLE);
			}
			else {
				button.setVisibility(View.GONE);
			}
		}
		else {
			// 本地
			int guide = this.appGuides[position];
			imageView.setImageResource(guide);
			((ViewPager) container).addView(view, 0);
			if (position >= (this.appGuides.length - 1)) {
				button.setVisibility(View.VISIBLE);
			}
			else {
				button.setVisibility(View.GONE);
			}
		}
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = GuideAdapter.this.mActivityFragment.getLoginUserSharedPre().edit();
				editor.putBoolean(Constant.APP_START_FIRST, true);
				editor.putBoolean(Constant.IS_APP_START_FIRST, false);
				editor.commit();
				Intent _intent = new Intent();
				_intent.setClass(GuideAdapter.this.mActivityFragment, MainActivity.class);
				GuideAdapter.this.mActivityFragment.startActivity(_intent);
				GuideAdapter.this.mActivityFragment.finish();
			}
		});
		return view;
	}

	@Override
	public int getCount() {
		boolean isStartFirst = this.mActivityFragment.getLoginUserSharedPre().getBoolean(Constant.IS_APP_START_FIRST,
		        false);
		if (isStartFirst) {
			return this.mAppGuides.size();
		}
		else {
			return this.appGuides.length;
		}
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == (View) obj;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
