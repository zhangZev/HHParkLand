package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.views.checkbox.SmoothCheckBox;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 测试View
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ViewTestActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.view_check)
    private SmoothCheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_viewtest);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
        setContentView(this.mActivityFragmentView);
        com.lidroid.xutils.ViewUtils.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initData() {
        this.mCheckBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
            }
        });
    }

    @Override
    public void initWidget() {
        initWithContent();
    }

    private void initWithContent() {
        // TODO Auto-generated method stub
        /** 导航栏 */
        initWithBar();
        this.mLeftTextView.setText("View测试");
        this.mLeftTextView.setVisibility(View.VISIBLE);
    }
}
