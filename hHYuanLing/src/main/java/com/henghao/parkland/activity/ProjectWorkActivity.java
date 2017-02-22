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
import com.henghao.parkland.views.DateChooseWheelViewDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;


/**
 * 工作备忘
 */
public class ProjectWorkActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.tv_starttime)
    private TextView tv_start;

    @ViewInject(R.id.tv_endtime)
    private TextView tv_endtime;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.et_content)
    private EditText et_content;

    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    @ViewInject(R.id.tv_cancel)
    private TextView tv_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_work);
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
        tv_title.setText("工作备忘");
    }


    @OnClick({R.id.tv_starttime, R.id.tv_endtime, R.id.tv_submit, R.id.tv_cancel})
    private void viewOnClcik(View v) {
        switch (v.getId()) {
            case R.id.tv_starttime:
                getDialogTime("开始时间", 1);
                break;
            case R.id.tv_endtime:
                getDialogTime("结束时间", 2);
                break;
            case R.id.tv_submit:
                String start_time = tv_start.getText().toString().trim();
                String end_time = tv_endtime.getText().toString().trim();
                String content = et_content.getText().toString().trim();
                SharedPreferences preferences = getLoginUserSharedPre();
                String UID = preferences.getString(Constant.USERID, null);
                if (checkData()) {
                    ProjectProtocol mProjectProtocol = new ProjectProtocol(this);
                    mProjectProtocol.addResponseListener(this);
                    mProjectProtocol.upLoadWorkBW(start_time, end_time, content, UID);
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;

        }
    }

    private boolean checkData() {
        if (tv_start.getText().toString().trim().equals("请选择时间")) {
            msg("请选择工作开始时间！");
            return false;
        }
        if (tv_endtime.getText().toString().trim().equals("请选择时间")) {
            msg("请选择工作结束时间！");
            return false;
        }
        if (ToolsKit.isEmpty(et_content.getText().toString().trim())) {
            msg("工作内容不能为空！");
            return false;
        }
        return true;
    }

    private DateChooseWheelViewDialog getDialogTime(String title, final int whatPos) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                switch (whatPos) {
                    case 1:
                        tv_start.setText(time);
                        break;
                    case 2:
                        tv_endtime.setText(time);
                        break;
                }
            }
        });
        startDateChooseDialog.setDateDialogTitle(title);
        startDateChooseDialog.showDateChooseDialog();
        startDateChooseDialog.setCanceledOnTouchOutside(true);
        return startDateChooseDialog;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_WORKBEIWANG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                finish();
                return;
            }
        }
    }
}
