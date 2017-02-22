package com.henghao.parkland.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

/**
 * 施工备忘
 */
public class ProjectSGActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    /**
     * 项目名称
     */
    @ViewInject(R.id.et_project_name)
    private EditText et_project_name;

    /**
     * 工作内容
     */
    @ViewInject(R.id.et_content)
    private EditText et_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sg);
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
        mLeftTextView.setText("施工备忘");
        tv_title.setText("施工备忘");
    }

    @OnClick({R.id.tv_submit, R.id.tv_cancel})
    private void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (checkData()) {
                    ProjectProtocol mProjectProtocol = new ProjectProtocol(this);
                    mProjectProtocol.addResponseListener(this);
                    SharedPreferences preferences = getLoginUserSharedPre();
                    String UID = preferences.getString(Constant.USERID, null);
                    mProjectProtocol.upLoadSGBW(et_project_name.getText().toString().trim(), et_content.getText().toString().trim(), UID);
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(et_project_name.getText().toString().trim())) {
            msg("项目名称不能为空！");
            return false;
        }
        if (ToolsKit.isEmpty(et_content.getText().toString().trim())) {
            msg("工作内容不能为空！");
            return false;
        }
        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SGBEIWANG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                finish();
                return;
            }
        }
    }
}