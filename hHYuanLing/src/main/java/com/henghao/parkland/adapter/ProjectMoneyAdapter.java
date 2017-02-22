package com.henghao.parkland.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.SGWalletEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 施工钱包〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectMoneyAdapter extends BaseAdapter {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private List<SGWalletEntity> mList;

    public ProjectMoneyAdapter(ActivityFragmentSupport activityFragment) {
        this.mActivityFragmentSupport = activityFragment;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    public void setData(List<SGWalletEntity> data) {
        this.mList = data;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HodlerView mHodlerView = null;
        if (convertView == null) {
            mHodlerView = new HodlerView();
            convertView = this.inflater.inflate(R.layout.item_poject_money, null);
            mHodlerView.tv_date = (TextView) convertView.findViewById(R.id.tv_data);
            mHodlerView.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            mHodlerView.tv_context = (TextView) convertView.findViewById(R.id.tv_context);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        SGWalletEntity entity = mList.get(position);
        mHodlerView.tv_date.setText(entity.getTransactionTime());
        if (entity.getExpend() > 0) {
            mHodlerView.tv_context.setText("支出");
            mHodlerView.tv_money.setTextColor(Color.RED);
            mHodlerView.tv_money.setText(entity.getExpend() + "");
        } else if (entity.getInputs() > 0) {
            mHodlerView.tv_context.setText("收入");
            mHodlerView.tv_money.setTextColor(Color.GREEN);
            mHodlerView.tv_money.setText(entity.getInputs() + "");
        } else {
            mHodlerView.tv_context.setText("初始化");
            mHodlerView.tv_money.setText("0.0");
        }
        return convertView;
    }


    class HodlerView {

        TextView tv_context;
        TextView tv_money;
        TextView tv_date;

    }
}
