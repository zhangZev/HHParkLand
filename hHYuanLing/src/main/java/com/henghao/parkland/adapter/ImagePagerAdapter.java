/*
 * 文件名：ImagePagerAdapter.java
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

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.benefit.buy.library.utils.ListUtils;
import com.benefit.buy.library.utils.NSLog;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.AdEntity;
import com.lidroid.xutils.BitmapUtils;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2015年5月2日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

	private final Context context;

	private List<Integer> imageIdList;

	private final int size;

	private boolean isInfiniteLoop;

	private List<AdEntity> ads;

	private boolean isAds = false;

	private BitmapUtils mBitmapUtils;

	// private AdEntity mAdEntity;
	public ImagePagerAdapter(Context context, List<Integer> imageIdList) {
		this.context = context;
		this.imageIdList = imageIdList;
		this.size = ListUtils.getSize(imageIdList);
		this.isInfiniteLoop = false;
		this.isAds = false;
	}

	public ImagePagerAdapter(Context context, List<AdEntity> adEntitys, boolean isAd) {
		this.context = context;
		this.ads = adEntitys;
		this.size = ListUtils.getSize(this.ads);
		this.isInfiniteLoop = false;
		this.isAds = isAd;
		this.mBitmapUtils = new BitmapUtils(context, Constant.CACHE_DIR_PATH);
		this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_empty_big);
		this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
	}

	@Override
	public int getCount() {
		// Infinite loop
		if (this.isAds) {
			return ListUtils.getSize(this.ads);
		}
		else {
			int size = ListUtils.getSize(this.imageIdList);
			return size;
		}
	}

	/**
	 * get really position
	 * @param position
	 * @return
	 */
	private int getPosition(int position) {
		return this.isInfiniteLoop ? position % this.size : position;
	}

	@Override
	public View getView(int position, View view, ViewGroup container) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = holder.imageView = new ImageView(this.context);
			holder.imageView.setScaleType(ScaleType.FIT_XY);
			view.setTag(holder);
		}
		else {
			holder = (ViewHolder) view.getTag();
		}
		if (this.isAds) {
			AdEntity adEntity = this.ads.get(position);
			String adImg = adEntity.getAdImageUrl();
			// ProtocolUrl.ROOT_IMAGE_URL + "system/banner/m/" +
			this.mBitmapUtils.display(holder.imageView, adImg);
			isOnClick(holder, position, adEntity);
		}
		else {
			holder.imageView.setImageResource(this.imageIdList.get(getPosition(position)));
		}
		return view;
	}

	private void isOnClick(ViewHolder holder, int position, final AdEntity adEntity) {
		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

	private void forwardClass(String mAdLink) {
		try {
			String[] mString = mAdLink.split("?");
			if (mString == null) {
				return;
			}
			Intent intent = new Intent();
			String clazzPix = mString[0];// 这里得到类前缀
			clazzPix = captureName(clazzPix);
			String clazz = "com.freemode.shopping.activity." + clazzPix + "Activity";
			@SuppressWarnings("unchecked")
			Class<ActivityFragmentSupport> clazzActivity = (Class<ActivityFragmentSupport>) Class.forName(clazz);
			String endPar = mString[1];
			if (ToolsKit.isEmpty(endPar)) {
				return;
			}
			String[] paramDatas = endPar.split("&");
			if (paramDatas == null) {
				return;
			}
			for (int j = 0; j < paramDatas.length; j++) {
				String parpe = paramDatas[j];
				if (!ToolsKit.isEmpty(parpe)) {
					String[] paramVals = endPar.split("=");
					if (paramVals != null && !ToolsKit.isEmpty(paramVals[0]) && !ToolsKit.isEmpty(paramVals[0])) {
						intent.putExtra(paramVals[0], paramVals[1]);
					}
				}
			}
			intent.setClass(this.context, clazzActivity);
			this.context.startActivity(intent);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				Intent intent = new Intent();
				// String clazzPix = captureName(mAdLink);
				String clazz = "com.freemode.shopping.activity." + mAdLink + "Activity";
				@SuppressWarnings("unchecked")
				Class<ActivityFragmentSupport> clazzActivity = (Class<ActivityFragmentSupport>) Class.forName(clazz);
				intent.setClass(this.context, clazzActivity);
				this.context.startActivity(intent);
			}
			catch (Exception ex) {
				// TODO Auto-generated catch block
				NSLog.e(this, ex.getMessage());
			}
		}
	}

	public String captureName(String classNmae) {
		classNmae = classNmae.substring(0, 1).toUpperCase() + classNmae.substring(1);
		return classNmae;
	}

	private static class ViewHolder {

		ImageView imageView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return this.isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop the isInfiniteLoop to set
	 */
	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}
}
