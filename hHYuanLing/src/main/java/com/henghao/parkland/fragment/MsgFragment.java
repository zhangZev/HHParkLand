package com.henghao.parkland.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.henghao.parkland.R;
import com.lidroid.xutils.ViewUtils;

/**
 * 消息〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MsgFragment extends FragmentSupport {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_msg);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
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
