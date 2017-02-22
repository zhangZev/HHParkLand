package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.YHDataEntity;
import com.henghao.parkland.model.protocol.YHDataProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

public class YHDataActivity extends ActivityFragmentSupport {

    /**
     * 植物编号
     */
    @ViewInject(R.id.tv_treeid_maintenance)
    private TextView tv_treeid_maintenance;

    /**
     * 养护地址
     */
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    /**
     * 养护时间
     */
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    /**
     * 养护人员
     */
    @ViewInject(R.id.tv_worker)
    private TextView tv_worker;
    /**
     * 问题
     */
    @ViewInject(R.id.tv_question)
    private TextView tv_question;
    /**
     * 备注
     */
    @ViewInject(R.id.tv_data)
    private TextView tv_data;
    /**
     * 备注
     */
    @ViewInject(R.id.tv_clen)
    private TextView tv_clen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_yhdata);
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
        int yId = getIntent().getIntExtra("yid", 0);
        String treeId = getIntent().getStringExtra("treeId");
        YHDataProtocol mmProtocol = new YHDataProtocol(this);
        mmProtocol.addResponseListener(this);
        mmProtocol.getYHData(yId, treeId);
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("管护信息");
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.APP_YANGHUQUERY)) {
            if (jo instanceof BaseEntity) {
                return;
            }
            YHDataEntity mdata = (YHDataEntity) jo;
            SetText(mdata);
        }
    }


    private void SetText(YHDataEntity mdata) {
        tv_treeid_maintenance.setText(mdata.getTreeId());
        tv_address.setText(mdata.getYhSite());
        tv_time.setText(mdata.getYhTime());
        tv_worker.setText(mdata.getYhWorker());
        tv_question.setText(mdata.getTreeGrowup());
        tv_data.setText(mdata.getYhComment());
        tv_clen.setText(mdata.getYhClean());
    }
}
