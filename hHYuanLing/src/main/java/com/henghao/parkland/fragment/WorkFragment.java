package com.henghao.parkland.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benefit.buy.library.viewpagerindicator.CirclePageIndicator;
import com.benefit.buy.library.views.NoScrollListView;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.WorkListShowAdapter;
import com.henghao.parkland.adapter.WorkShowAdapter;
import com.henghao.parkland.utils.CommonAutoViewpager;
import com.henghao.parkland.views.viewpage.AutoScrollViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 工作台 〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WorkFragment extends FragmentSupport {

    @ViewInject(R.id.listview_xuqiu)
    private NoScrollListView listview_xuqiu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_work);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
        initView();
        List<String> mList = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            mList.add("15");
        }
        WorkShowAdapter mAdapter = new WorkShowAdapter(this.mActivity, mList);
        this.listview_xuqiu.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        // TODO Auto-generated method stub
        LayoutInflater mInflater = LayoutInflater.from(this.mActivity);
        //添加布局
        View headerView = mInflater.inflate(R.layout.include_homework, this.listview_xuqiu, false);
        //滚动图
        AutoScrollViewPager viewPager = (AutoScrollViewPager) headerView.findViewById(R.id.view_pager);
        CirclePageIndicator indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
        CommonAutoViewpager.viewPageAdapter(this.mActivity, viewPager, indicator);
        //信息展示
        NoScrollListView mNoScrollListView = (NoScrollListView) headerView.findViewById(R.id.listview_noscroll);
        //信息展示数据
        List<String> mList = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            mList.add("8");
        }
        WorkListShowAdapter mShowAdapter = new WorkListShowAdapter(this.mActivity, mList);
        mNoScrollListView.setAdapter(mShowAdapter);
        mShowAdapter.notifyDataSetChanged();
        this.listview_xuqiu.addHeaderView(headerView);
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
}
