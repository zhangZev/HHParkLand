package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.lidroid.xutils.ViewUtils;

/**
 * 日志备忘
 */
public class LogForgetActivity extends ActivityFragmentSupport {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_log_forget);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        setContentView(this.mActivityFragmentView);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    @Override
    public void initData() {
        super.initData();
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("工作备忘");
    }
}
