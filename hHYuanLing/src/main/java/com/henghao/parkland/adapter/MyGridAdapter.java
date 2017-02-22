package com.henghao.parkland.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.QiandaoActivity;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 我的〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyGridAdapter extends ArrayAdapter<AppGridEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private final List<AppGridEntity> mList;

    public MyGridAdapter(ActivityFragmentSupport activityFragment, List<AppGridEntity> mList2) {
        super(activityFragment, R.layout.item_gridview_textimage, mList2);
        this.mActivityFragmentSupport = activityFragment;
        this.mList = mList2;
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
            convertView = this.inflater.inflate(R.layout.item_gridview_textimage, null);
            mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHodlerView.image_title = (ImageView) convertView.findViewById(R.id.image_title);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        mHodlerView.image_title.setImageResource(getItem(position).getImageId());
        mHodlerView.tv_title.setVisibility(View.VISIBLE);
        mHodlerView.tv_title.setText(getItem(position).getName());
        viewOnClick(mHodlerView, convertView, position);
        return convertView;
    }

    /**
     * 点击事件
     *
     * @param mHodlerView
     * @param convertView
     * @param position
     */
    private void viewOnClick(HodlerView mHodlerView, View convertView, final int position) {
        final Intent intent = new Intent();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        //签到
                        intent.setClass(mActivityFragmentSupport, QiandaoActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    /*case 3:
                        //日志
                        intent.setClass(mActivityFragmentSupport, ProjectLogActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;*/
                   /* case 5:
                        //项目管理
                        intent.setClass(mActivityFragmentSupport, ProjectActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;*/
                }
            }
        });

    }


    class HodlerView {

        TextView tv_title;

        ImageView image_title;
    }
}
