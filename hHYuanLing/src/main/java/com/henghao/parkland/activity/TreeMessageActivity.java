package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by 晏琦云 on 2017/2/15.
 * 植物信息录入界面
 */

public class TreeMessageActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_treeid_treemessage)
    TextView tvTreeId;
    @InjectView(R.id.et_treename_treemessage)
    EditText etTreeName;
    @InjectView(R.id.et_treeuse_treemessage)
    EditText etTreeUse;
    @InjectView(R.id.et_treespecification_treemessage)
    EditText etTreeSpecification;
    @InjectView(R.id.tv_treesite_treemessage)
    TextView tvTreeSite;
    @InjectView(R.id.tv_treetime_treemessage)
    TextView tvTreeTime;
    @InjectView(R.id.btn_submit_treemessage)
    Button btnSubmit;
    @InjectView(R.id.btn_cancel_treemessage)
    Button btnCancel;
    private String treeId;//植物编号
    private String treeName;//植物名称
    private String treeUse;//植物用途
    private String treeSpecification;//植物规格
    private String treeSite;//种植地点
    private String treeTime;//录入时间
    private static final String TAG = "TreeMessageActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_treemessage);
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
        mLeftTextView.setText("植物信息录入");
    }

    /**
     * 初始化数据
     */
    public void initData() {
        Intent intent = getIntent();
        treeId = intent.getStringExtra("treeId");
        tvTreeId.setText(treeId);//获取植物编号
        treeSite = intent.getStringExtra("treeSite");
        tvTreeSite.setText(treeSite);//获取种植地点
        /**
         * 获取录入时间
         */
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        treeTime = dateFormat.format(date);//格式化时间
        tvTreeTime.setText(treeTime);
    }

    @OnClick({R.id.btn_submit_treemessage, R.id.btn_cancel_treemessage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_treemessage:
                treeName = etTreeName.getText().toString().trim();
                treeUse = etTreeUse.getText().toString().trim();
                treeSpecification = etTreeSpecification.getText().toString().trim();
                if (treeName.equals("") || treeName == null) {
                    Toast.makeText(this, "植物名称不能为空！请输入内容！", Toast.LENGTH_SHORT).show();
                    etTreeName.requestFocus();
                    return;
                }
                if (treeUse.equals("") || treeUse == null) {
                    Toast.makeText(this, "植物用途不能为空！请输入内容！", Toast.LENGTH_SHORT).show();
                    etTreeUse.requestFocus();
                    return;
                }
                if (treeSpecification.equals("") || treeSpecification == null) {
                    Toast.makeText(this, "植物规格不能为空！请输入内容！", Toast.LENGTH_SHORT).show();
                    etTreeSpecification.requestFocus();
                    return;
                }
                /**
                 * 访问网络
                 */
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
                requestBodyBuilder.add("treeId", treeId);
                requestBodyBuilder.add("treeName", treeName);
                requestBodyBuilder.add("treeUse", treeUse);
                requestBodyBuilder.add("treeSpecification", treeSpecification);
                requestBodyBuilder.add("treeSite", treeSite);
                requestBodyBuilder.add("treeTime", treeTime);
                RequestBody requestBody = requestBodyBuilder.build();
                Request request = builder.post(requestBody).url(HttpPublic.SAVETREE).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TreeMessageActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String str_result = response.body().string();//访问网络返回的数据
                        Log.i(TAG, "onResponse: " + str_result);
                        /**
                         * 解析返回的json字符串
                         */
                        try {
                            JSONObject jsonObject = new JSONObject(str_result);
                            final String result = jsonObject.getString("result");
                            int error = jsonObject.getInt("error");//错误代码 0 错误 1 正确
                            if (error == 1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TreeMessageActivity.this, result, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TreeMessageActivity.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TreeMessageActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_cancel_treemessage:
                finish();
                break;
        }
    }
}
