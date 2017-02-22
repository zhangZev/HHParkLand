/*
 * 文件名：GuideActivity.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.viewpagerindicator.CirclePageIndicator;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.GuideAdapter;
import com.henghao.parkland.model.entity.AppGuideEntity;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.protocol.SystemProtocol;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;



/**
 * 引导页
 * @author zhangxianwen
 * @version HDMNV100R001, 2015-4-24
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GuideActivity extends ActivityFragmentSupport {

	private static final int[] pics = {
//	        R.drawable.bg_page_01, R.drawable.bg_page_02, R.drawable.bg_page_03, R.drawable.bg_page_04,
//	        R.drawable.bg_page_05
	};

	@ViewInject(R.id.pager)
	private ViewPager viewPager;

	@ViewInject(R.id.indicator)
	private CirclePageIndicator circlePageIndicator;

	private BitmapUtils mBitmapUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_activity_guide);
		com.lidroid.xutils.ViewUtils.inject(this);
		initWidget();
		initData();
	}

	@Override
	public void initWidget() {
		super.initWidget();
		this.mBitmapUtils = new BitmapUtils(this, Constant.CACHE_DIR_PATH);
		this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
		this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
		boolean isStart = this.mSharedPreferences.getBoolean(Constant.IS_APP_START_FIRST, false);
		GuideAdapter guideAdapter = null;
		if (isStart) {
			SystemProtocol mSystemProtocol = new SystemProtocol(this);
			mSystemProtocol.addResponseListener(this);
			mSystemProtocol.appGuide();
		}
		else {
			guideAdapter = new GuideAdapter(this, pics);
			this.viewPager.setAdapter(guideAdapter);
			this.circlePageIndicator.setViewPager(this.viewPager);
		}
	}

	@Override
	public void initData() {
		super.initData();
	}

	@Override
	public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
		// TODO Auto-generated method stub
		super.OnMessageResponse(url, jo, status);
		if (jo == null) {
			// 提示
			return;
		}
		List<AppGuideEntity> appGuides = new ArrayList<AppGuideEntity>();
		if (url.endsWith(ProtocolUrl.APP_GUIDE)) {
			if (jo instanceof BaseEntity) {
				BaseEntity base = (BaseEntity) jo;
				msg(base.getMsg());
				GuideAdapter guideAdapter = new GuideAdapter(this, pics);
				this.viewPager.setAdapter(guideAdapter);
				this.circlePageIndicator.setViewPager(this.viewPager);
				return;
			}
			AppGuideEntity appStart = (AppGuideEntity) jo;
			List<AppGuideEntity> lists = appStart.getGuideList();
			if (ToolsKit.isEmpty(lists)) {
				GuideAdapter guideAdapter = new GuideAdapter(this, pics);
				this.viewPager.setAdapter(guideAdapter);
				this.circlePageIndicator.setViewPager(this.viewPager);
				return;
			}
			appGuides.addAll(lists);
			// appGuides.add(appStart.getGuideImgTwo());
			// appGuides.add(appStart.getGuideImgThree());
			// appGuides.add(appStart.getGuideImgFour());
			GuideAdapter guideAdapter = new GuideAdapter(this, appGuides);
			this.viewPager.setAdapter(guideAdapter);
			this.circlePageIndicator.setViewPager(this.viewPager);
		}
	}
}
