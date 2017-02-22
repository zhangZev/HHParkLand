package com.henghao.parkland.activity;

import android.content.Intent;
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
import com.henghao.parkland.model.entity.UserLoginEntity;
import com.henghao.parkland.model.protocol.LoginProtocol;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;


public class LoginActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.login_pass_quick)
    private TextView login_pass_quick;

    @ViewInject(R.id.login_user)
    private EditText login_user;

    @ViewInject(R.id.login_pass)
    private EditText login_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_login);
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
        initWithCenterBar();
        mCenterTextView.setVisibility(View.VISIBLE);
        mCenterTextView.setText("登录");
    }

    @OnClick({R.id.login_pass_quick, R.id.btn_layout})
    public void viewClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.login_pass_quick:
                //注册
                intent.setClass(LoginActivity.this, RegActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_layout:
                //登录
                if (checkData()) {
                    LoginProtocol mLoginProtocol = new LoginProtocol(this);
                    mLoginProtocol.addResponseListener(this);
                    mLoginProtocol.login(login_user.getText().toString().trim(), login_pass.getText().toString().trim());
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                }
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(login_user.getText().toString().trim())) {
            msg("用户名不能为空");
            return false;
        }
        if (ToolsKit.isEmpty(login_pass.getText().toString().trim())) {
            msg("密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.APP_LOGIN)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                return;
            }
            UserLoginEntity userLogin = (UserLoginEntity) jo;
            getLoginUserSharedPre().edit().putString(Constant.USERID, userLogin.getUid()).putString(Constant.USERNAME, userLogin.getUsername()).putString(Constant.USERPHONE, userLogin.getTel()).commit();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
