package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.model.protocol.HttpPublic;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by 晏琦云 on 2017/2/10.
 * 植物养护信息界面
 */

public class MaintenanceActivity extends ActivityFragmentSupport {

    private static final String TAG = "MaintenanceActivity";
    @InjectView(R.id.tv_treeid_maintenance)
    TextView tvTreeid;
    @InjectView(R.id.tv_state_maintenance)
    TextView tvState;
    @InjectView(R.id.tv_time_maintenance)
    TextView tvTime;
    @InjectView(R.id.tv_place_maintenance)
    TextView tvPlace;
    @InjectView(R.id.btn_confirm_maintenance)
    Button btnConfirm;
    @InjectView(R.id.btn_cancel_maintenance)
    Button btnCancel;
    /**
     * 网络访问相关
     */
    private OkHttpClient okHttpClient;
    private String treeId;//二维码扫描内容
    private String yhStatusname;//养护状态
    private String yhStatustime;//养护时间
    private String yhStatussite;//养护地点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_maintenace);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("养护信息");
    }

    public void initData() {
        Intent intent = getIntent();
        treeId = intent.getStringExtra("treeId");
        yhStatusname = intent.getStringExtra("yhStatusname");
        yhStatustime = intent.getStringExtra("yhStatustime");
        yhStatussite = intent.getStringExtra("yhStatussite");
        tvTreeid.setText(treeId);
        tvState.setText(yhStatusname);
        tvTime.setText(yhStatustime);
        tvPlace.setText(yhStatussite);
    }


    @OnClick({R.id.btn_confirm_maintenance, R.id.btn_cancel_maintenance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_maintenance:
                /**
                 * 访问网络
                 */
                okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
                RequestBody requestBody = requestBodyBuilder.add("treeId", treeId)//
                        .add("yhStatusname", yhStatusname)//
                        .add("yhStatustime", yhStatustime)//
                        .add("yhStatussite", yhStatussite)
                        .build();
                Request request = builder.post(requestBody).url(HttpPublic.SAVESTATUS).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MaintenanceActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String result = response.body().string();
                        Log.i(TAG, "onResponse: " + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            final String result_str = jsonObject.getString("result");
                            Log.i(TAG, "onResponse: " + result_str);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MaintenanceActivity.this, result_str, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } catch (JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MaintenanceActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_cancel_maintenance:
                finish();
                break;
        }
    }
}
