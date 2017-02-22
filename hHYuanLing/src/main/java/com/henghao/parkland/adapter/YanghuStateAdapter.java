package com.henghao.parkland.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henghao.parkland.R;

import java.util.List;

/**
 * 养护状态信息〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class YanghuStateAdapter extends ArrayAdapter<String> {

    private final LayoutInflater inflater;


    private final Context mActivityFragmentSupport;

    public YanghuStateAdapter(Context activityFragment, List<String> mList) {
        super(activityFragment, R.layout.item_yanghustatelist, mList);
        this.mActivityFragmentSupport = activityFragment;
        this.inflater = LayoutInflater.from(activityFragment);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HodlerView mHodlerView = null;
        if (convertView == null) {
            mHodlerView = new HodlerView();
            convertView = this.inflater.inflate(R.layout.item_yanghustatelist, null);
            mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        mHodlerView.tv_title.setText(getItem(position));
        return convertView;
    }

    private class HodlerView {

        TextView tv_title;

    }
}
