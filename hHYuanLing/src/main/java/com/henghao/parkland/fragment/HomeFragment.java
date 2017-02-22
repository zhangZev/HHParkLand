package com.henghao.parkland.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonListStringAdapter;
import com.henghao.parkland.utils.PopupWindowHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 首页 〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HomeFragment extends FragmentSupport {

    @ViewInject(R.id.tv_search_main)
    private TextView tv_search_main;

    private View popView;

    private PopupWindowHelper popupWindowHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_home);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
        LayoutInflater inflater = LayoutInflater.from(this.mActivity);
        this.popView = LayoutInflater.from(this.mActivity).inflate(R.layout.common_android_listview, null);
        ListView mListView = (ListView) this.popView.findViewById(R.id.mlistview);
        final List<String> mList = new ArrayList<String>();
        mList.add("园林企业");
        mList.add("苗木企业");
        mList.add("苗木商城");
        mList.add("招投标信息");
        mList.add("需求信息");
        mList.add("设备租赁");
        CommonListStringAdapter mListStringAdapter = new CommonListStringAdapter(this.mActivity, mList);
        mListView.setAdapter(mListStringAdapter);
        mListStringAdapter.notifyDataSetChanged();
        this.popupWindowHelper = new PopupWindowHelper(this.popView);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String whatSelect = mList.get(arg2);
                HomeFragment.this.tv_search_main.setText(whatSelect);
                HomeFragment.this.popupWindowHelper.dismiss();
            }
        });
    }

    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void initwithContent() {
        // TODO Auto-generated method stub
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
    }

    public void initWidget() {
        initwithContent();
    }

    @OnClick({R.id.tv_search_main})
    private void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_main:
                //下拉
                this.popupWindowHelper.showAsDropDown(this.tv_search_main);
                break;
            default:
                break;
        }
    }
}
