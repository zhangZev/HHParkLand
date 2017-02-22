package com.henghao.parkland.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsRegex;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.protocol.LoginProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

public class RegActivity extends ActivityFragmentSupport {

    /**
     * 用户名
     */
    @ViewInject(R.id.login_user)
    private EditText login_user;
    /**
     * 电话
     */
    @ViewInject(R.id.login_phone)
    private EditText login_phone;
    /**
     * 密码
     */
    @ViewInject(R.id.login_pass)
    private EditText login_pass;
    /**
     * 密码
     */
    @ViewInject(R.id.login_passagain)
    private EditText login_passagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_reg);
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
        initWithBar();
        mLeftTextView.setText("注册");
        mLeftTextView.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_layout})
    public void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_layout:
                //注册
                LoginProtocol mLoginProtocol = new LoginProtocol(this);
                mLoginProtocol.addResponseListener(this);
                if (checkData()) {
                    String userName = login_user.getText().toString().trim();
                    String phone = login_phone.getText().toString().trim();
                    String pwd = login_pass.getText().toString().trim();
                    //注册
                    mLoginProtocol.reg_user(userName, pwd, phone);
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                }
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(login_user.getText().toString().trim())) {
            msg("用户名不能为空");
            return false;
        } else if (ToolsKit.isEmpty(login_phone.getText().toString().trim())) {
            msg("电话号码不能为空");
            return false;
        } else if (!ToolsRegex.isMobileNumber(login_phone.getText().toString().trim())) {
            msg("电话号码格式不正确");
            return false;
        } else if (ToolsKit.isEmpty(login_pass.getText().toString().trim())) {
            msg("密码不能为空");
            return false;
        } else if (ToolsKit.isEmpty(login_passagain.getText().toString().trim())) {
            msg("密码确认不能为空");
            return false;
        } else if (!login_passagain.getText().toString().trim().equals(login_pass.getText().toString().trim())) {
            msg("两次输入密码不相同");
            return false;
        }

        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.APP_REG)) {
            BaseEntity mBaseEntity = (BaseEntity) jo;
            msg(mBaseEntity.getMsg());
            finish();
        }
    }
}
