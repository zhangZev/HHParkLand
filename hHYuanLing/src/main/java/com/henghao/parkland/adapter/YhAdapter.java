package com.henghao.parkland.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.henghao.parkland.R;
import com.henghao.parkland.activity.GuanhuActivity;
import com.henghao.parkland.activity.YHDataActivity;
import com.henghao.parkland.model.entity.YhBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/13.
 */

public class YhAdapter extends BaseAdapter {
    private List<YhBean> list;
    private Context context;
    private LayoutInflater inflater;

    public YhAdapter(List<YhBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_yanghu_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final YhBean bean = list.get(position);
        viewHolder.tvId.setText(bean.getId() + "");
        viewHolder.tvTreeid.setText(bean.getTreeId());
        viewHolder.tvBehavior.setText(bean.getYhStatusname());
        viewHolder.tvPlace.setText(bean.getYhStatussite());
        viewHolder.tvTime.setText(bean.getYhStatustime());
        if (bean.getIsNo() == null) {
            viewHolder.btnWrite.setVisibility(View.VISIBLE);
        } else {
            if (bean.getIsNo() == 1) {
                viewHolder.btnWrite.setVisibility(View.GONE);
            } else {
                viewHolder.btnWrite.setVisibility(View.VISIBLE);
            }
        }
        viewOnclick(viewHolder, convertView, position, bean);
        return convertView;
    }

    private void viewOnclick(ViewHolder viewHolder, View convertView, int position, final YhBean bean) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getIsNo() != null) {
                    if (bean.getIsNo() == 1) {
                        Intent intent = new Intent(context, YHDataActivity.class);
                        intent.putExtra("yid", bean.getId());//养护信息ID
                        intent.putExtra("treeId", bean.getTreeId());//植物二维码
                        context.startActivity(intent);
                    }
                }
            }
        });
        viewHolder.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuanhuActivity.class);
                intent.putExtra("yid", bean.getId());//养护信息ID
                intent.putExtra("treeId", bean.getTreeId());//植物二维码
                intent.putExtra("yhSite", bean.getYhStatussite());//养护地点
                intent.putExtra("yhTime", bean.getYhStatustime());//养护时间
                context.startActivity(intent);
            }
        });
    }


    static class ViewHolder {
        @InjectView(R.id.tv_id_yanghuitem)
        TextView tvId;
        @InjectView(R.id.tv_treeid_yanghuitem)
        TextView tvTreeid;
        @InjectView(R.id.tv_behavior_yanghuitem)
        TextView tvBehavior;
        @InjectView(R.id.tv_place_yanghuitem)
        TextView tvPlace;
        @InjectView(R.id.tv_time_yanghuitem)
        TextView tvTime;
        @InjectView(R.id.btn_write_yanghuitem)
        Button btnWrite;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
