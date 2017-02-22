package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.model.protocol.HttpPublic;
import com.henghao.parkland.views.FlowRadioGroup;
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
 * Created by 晏琦云 on 2017/2/13.
 * 管护信息填写界面
 */

public class GuanhuActivity extends ActivityFragmentSupport {
    @InjectView(R.id.tv_treeid_guanhu)
    TextView tvTreeId;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_yhsite_guanhu)
    TextView tvYhSite;
    @InjectView(R.id.tv_yhtime_guanhu)
    TextView tvYhTime;
    @InjectView(R.id.et_yhWorkder_guanhu)
    EditText etYhWorkder;
    @InjectView(R.id.et_yhDetails_guanhu)
    EditText etYhDetails;
    @InjectView(R.id.et_comment_guanhu)
    EditText etComment;
    @InjectView(R.id.btn_submit_guanhu)
    Button btnSubmit;
    @InjectView(R.id.btn_cancel_guanhu)
    Button btnCancel;
    @InjectView(R.id.rg_clean_guanhu)
    RadioGroup rgClean;
    @InjectView(R.id.rg_treegrowup_guanhu)
    RadioGroup rgTreegrowup;
    @InjectView(R.id.rg_question_guanhu)
    FlowRadioGroup rgQuestion;

    private int yid;//养护信息ID
    private String treeId;//植物二维码
    private String yhSite;//养护地点
    private String yhTime;//养护时间
    private String yhWorker;//养护人员
    private String yhDetails;//养护内容
    private String yhQuestion = "无";//问题发现
    private String yhClean = "好";//陆地保洁情况
    private String treeGrowup = "好";//植物长势
    private String yhComment;//备注信息
    private static final String TAG = "GuanhuActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_guanhu);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initData();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("管护");
        tv_title.setText("管护");
        Intent intent = getIntent();
        yid = intent.getIntExtra("yid", 0);
        treeId = intent.getStringExtra("treeId");
        yhSite = intent.getStringExtra("yhSite");
        yhTime = intent.getStringExtra("yhTime");
        tvTreeId.setText(treeId);
        tvYhSite.setText(yhSite);
        tvYhTime.setText(yhTime);
        ((RadioButton) rgQuestion.getChildAt(0)).setChecked(true);
        ((RadioButton) rgTreegrowup.getChildAt(0)).setChecked(true);
        ((RadioButton) rgClean.getChildAt(0)).setChecked(true);
        rgQuestion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case 1://无
                        yhQuestion = "无";
                        break;
                    case 2://有病虫害
                        yhQuestion = "有病虫害";
                        break;
                    case 3://有病虫害
                        yhQuestion = "施肥";
                        break;
                    case 4://有病虫害
                        yhQuestion = "破坏";
                        break;
                    case 5://有病虫害
                        yhQuestion = "须冲洗";
                        break;
                }
            }
        });
        rgClean.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case 1:
                        yhClean = "好";
                        break;
                    case 2:
                        yhClean = "良好";
                        break;
                    case 3:
                        yhClean = "差";
                        break;
                }
            }
        });
        rgTreegrowup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case 1:
                        treeGrowup = "好";
                        break;
                    case 2:
                        treeGrowup = "良好";
                        break;
                    case 3:
                        treeGrowup = "差";
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_submit_guanhu, R.id.btn_cancel_guanhu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_guanhu:
                yhWorker = etYhWorkder.getText().toString().trim();
                yhDetails = etYhDetails.getText().toString().trim();
                yhComment = etComment.getText().toString().trim();
                if (yhWorker.equals("") || yhWorker == null) {
                    msg("请输入养护人员");
                    return;
                }
                if (yhDetails.equals("") || yhDetails == null) {
                    msg("请输入养护内容");
                    return;
                }
                if (yhComment.equals("") || yhComment == null) {
                    yhComment = "无";
                }
                /**
                 * 访问网络，提交数据
                 */
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
                /**
                 * 封装请求参数到requestBodyBuilder中，构建requestBody
                 */
                requestBodyBuilder.add("yid", String.valueOf(yid));
                requestBodyBuilder.add("treeId", treeId);
                requestBodyBuilder.add("yhSite", yhSite);
                requestBodyBuilder.add("yhWorker", yhWorker);
                requestBodyBuilder.add("yhDetails", yhDetails);
                requestBodyBuilder.add("yhTime", yhTime);
                requestBodyBuilder.add("yhQuestion", yhQuestion);
                requestBodyBuilder.add("yhClean", yhClean);
                requestBodyBuilder.add("treeGrowup", treeGrowup);
                requestBodyBuilder.add("yhComment", yhComment);
                RequestBody requestBody = requestBodyBuilder.build();
                Request request = builder.post(requestBody).url(HttpPublic.SAVEGHMSG).build();
                /**
                 * 封装request
                 */
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GuanhuActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String result_str = response.body().string();
                        Log.i(TAG, "onResponse: " + result_str);
                        try {
                            JSONObject jsonObject = new JSONObject(result_str);
                            int error = jsonObject.getInt("error");//错误代码 0 错误 1 正确
                            if (error == 1) {
                                final String result = jsonObject.getString("result");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GuanhuActivity.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                finish();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GuanhuActivity.this, "添加失败，请重试！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GuanhuActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_cancel_guanhu:
                finish();
                break;
        }
    }
}
