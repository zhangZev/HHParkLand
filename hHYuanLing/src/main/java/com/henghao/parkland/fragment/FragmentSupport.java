/*
 * 文件名：FragmentSupport.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.fragment;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.model.ascyn.BusinessResponse;
import com.henghao.parkland.views.ActivityFragmentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * fragment 父类〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-15
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FragmentSupport extends Fragment implements BusinessResponse {

	protected Object object;

	protected ActivityFragmentView mActivityFragmentView;

	@ViewInject(R.id.bar_left)
	protected LinearLayout mLeftLinearLayout;

	@ViewInject(R.id.bar_center)
	protected LinearLayout mCenterLinearLayout;

	@ViewInject(R.id.bar_right)
	protected LinearLayout mRightLinearLayout;

	// protected SystemBarTintManager mTintManager;
	protected ActivityFragmentSupport mActivity;

	protected ImageView mLeftImageView;

	protected TextView mLeftTextView;

	/** 导航栏中间文字 **/
	protected TextView mCenterTextView;

	/** 导航栏中间图标 **/
	protected ImageView mCenterImageView;

	/** 导航栏右边文字 **/
	protected TextView mRightTextView;

	/** 导航栏右边图标 **/
	protected ImageView mRightImageView;

	public int fragmentId;

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof ActivityFragmentSupport) {
			this.mActivity = (ActivityFragmentSupport) activity;
		}
	}

	// TODO why
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.mActivityFragmentView = (ActivityFragmentView) inflater.inflate(R.layout.common_activity_views, null);
		this.mLeftLinearLayout = (LinearLayout) this.mActivityFragmentView.findViewById(R.id.bar_left);
		this.mCenterLinearLayout = (LinearLayout) this.mActivityFragmentView.findViewById(R.id.bar_center);
		this.mRightLinearLayout = (LinearLayout) this.mActivityFragmentView.findViewById(R.id.bar_right);
		return this.mActivityFragmentView;
	}

	/**
	 * 导航栏左边 〈一句话功能简述〉 〈功能详细描述〉
	 * @param inflater
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public View initWithBar() {
		/** 导航栏左边 **/
		View viewLeft = this.mActivity.getLayoutInflater().inflate(R.layout.common_view_bar_left,
		        this.mLeftLinearLayout);
		this.mLeftImageView = (ImageView) viewLeft.findViewById(R.id.bar_left_img);
		this.mLeftTextView = (TextView) viewLeft.findViewById(R.id.bar_left_title);
		this.mLeftTextView.setText(getResources().getString(R.string.back));
		this.mLeftTextView.setVisibility(View.GONE);
		this.mLeftImageView.setImageResource(R.drawable.btn_back);
		return viewLeft;
	}

	/**
	 * 导航栏中间〈一句话功能简述〉 〈功能详细描述〉
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public View initWithCenterBar() {
		/** 导航栏中间 **/
		View viewCenter = this.mActivity.getLayoutInflater().inflate(R.layout.common_view_bar_center,
		        this.mCenterLinearLayout);
		ImageView mcenterImageView = (ImageView) viewCenter.findViewById(R.id.bar_center_img);
		mcenterImageView.setVisibility(View.GONE);
		this.mCenterTextView = (TextView) viewCenter.findViewById(R.id.bar_center_title);
		this.mCenterTextView.setText(getResources().getString(R.string.hc_home));
		// mcenterImageView.setImageResource(R.drawable.nav_logo);
		return viewCenter;
	}

	/**
	 * 导航栏右边
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public View initWithRightBar() {
		View viewRight = this.mActivity.getLayoutInflater().inflate(R.layout.common_view_bar_right,
		        this.mRightLinearLayout);
		this.mRightImageView = (ImageView) viewRight.findViewById(R.id.bar_right_img);
		this.mRightTextView = (TextView) viewRight.findViewById(R.id.bar_right_title);
		this.mRightTextView.setText("back");
		this.mRightTextView.setVisibility(View.GONE);
		// mRightImageView.setImageResource(R.drawable.nav_logo);
		return viewRight;
	}

	public void viewLoading(int vis) {
		this.mActivityFragmentView.viewLoading(vis);
	}

	// end
	@Override
	public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
		this.mActivityFragmentView.viewLoading(View.GONE);
		if (jo == null) {
			return;
		}
	}

	public void onActivityCallBack(Object obj) {
	}

	public Object getObject() {
		return this.object;
	}
}
