package com.henghao.parkland.adapter;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.TreeEntity;
import com.lidroid.xutils.BitmapUtils;

/**
 * 巡检列表适配器〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NotPollingAdapter extends ArrayAdapter<TreeEntity> {

	private final LayoutInflater inflater;

	private final BitmapUtils mBitmapUtils;

	private final ActivityFragmentSupport mActivityFragmentSupport;

	public NotPollingAdapter(ActivityFragmentSupport activityFragment, List<TreeEntity> mList) {
		super(activityFragment, R.layout.item_notpolling, mList);
		this.mActivityFragmentSupport = activityFragment;
		this.inflater = LayoutInflater.from(activityFragment);
		this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
		this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
		this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HodlerView mHodlerView = null;
		if (convertView == null) {
			mHodlerView = new HodlerView();
			convertView = this.inflater.inflate(R.layout.item_notpolling, null);
			mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			mHodlerView.tv_not = (TextView) convertView.findViewById(R.id.tv_not);
			mHodlerView.tv_jian = (TextView) convertView.findViewById(R.id.tv_jian);
			mHodlerView.img_tip = (ImageView) convertView.findViewById(R.id.img_tip);
			convertView.setTag(mHodlerView);
		}
		else {
			mHodlerView = (HodlerView) convertView.getTag();
		}
		final TreeEntity mEntity = getItem(position);
		mHodlerView.tv_title.setText(mEntity.getTreeName() + "（共" + mEntity.getAllTree() + "棵)");

		mHodlerView.tv_not.setText(mEntity.getTreenot() + "未检");
		mHodlerView.tv_jian.setText(mEntity.getAllTree() - mEntity.getTreenot() + "已检");
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra(Constant.INTNET_TYPE, 112);
				intent.putExtra(Constant.INTNET_DATA, mEntity);
//				intent.setClass(NotPollingAdapter.this.mActivityFragmentSupport, ScanDetailActivity.class);
				NotPollingAdapter.this.mActivityFragmentSupport.startActivity(intent);
			}
		});
		return convertView;
	}

	private class HodlerView {

		ImageView img_tip;

		TextView tv_title;
		TextView tv_not;
		TextView tv_jian;

	}
}
