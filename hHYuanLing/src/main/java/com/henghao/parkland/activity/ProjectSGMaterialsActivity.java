package com.henghao.parkland.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 施工资料
 */
public class ProjectSGMaterialsActivity extends ActivityFragmentSupport {

    private static final int REQUEST_IMAGE = 0x00;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    /**
     * 工作内容
     */
    @ViewInject(R.id.et_context)
    private EditText et_content;

    /**
     * 上传图片按钮
     */
    @ViewInject(R.id.tv_uploadImage)
    private TextView tv_uploadImage;

    /**
     * 提交按钮
     */
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    /**
     * 取消按钮
     */
    @ViewInject(R.id.tv_cancel)
    private TextView tv_cancel;

    private ArrayList<String> mSelectPath;

    private ArrayList<File> mFileList;

    private static final String TAG = "ProjectSGMaterialsActiv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_sgm);
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
        mLeftTextView.setText("施工资料");
        tv_title.setText("施工资料");
        mFileList = new ArrayList<>();
    }

    @OnClick({R.id.tv_uploadImage, R.id.tv_submit, R.id.tv_cancel})
    private void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_uploadImage:
                addPic();
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    /**
                     * 请求访问网络
                     */
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request.Builder builder = new Request.Builder();
                    String content = et_content.getText().toString().trim();
                    SharedPreferences preferences = getLoginUserSharedPre();
                    String UID = preferences.getString(Constant.USERID, null);
                    MultipartBuilder multipartBuilder = new MultipartBuilder();
                    multipartBuilder.type(MultipartBuilder.FORM)//
                            .addFormDataPart("content", content)//工作内容
                            .addFormDataPart("uid", UID);//用户ID
                    for (File file : mFileList) {
                        multipartBuilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//图片
                    }
                    RequestBody requestBody = multipartBuilder.build();
                    Request request = builder.post(requestBody).url(ProtocolUrl.PROJECT_SGINFO).build();
                    mActivityFragmentView.viewLoading(View.VISIBLE);
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Log.e(TAG, "onFailure: " + e.getMessage());
                            e.printStackTrace();
                            msg("网络请求错误！");
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String content = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(content);
                                final String result = jsonObject.getString("result");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mActivityFragmentView.viewLoading(View.GONE);
                                        Toast.makeText(ProjectSGMaterialsActivity.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }

    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(et_content.getText().toString().trim())) {
            msg("工作内容不能为空！");
            et_content.requestFocus();
            return false;
        }
        if (mFileList.size() == 0) {
            msg("请选择图片！");
            return false;
        }
        return true;
    }

    /**
     * 添加图片
     */
    private void addPic() {
        // 查看session是否过期
        // int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 9;
        Intent picIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if ((this.mSelectPath != null) && (this.mSelectPath.size() > 0)) {
            picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPath);
        }
        startActivityForResult(picIntent, REQUEST_IMAGE);
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_IMAGE) {
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPath)) {
                        StringBuilder sb = new StringBuilder();
                        for (String filePath : mSelectPath) {
                            String imageName = getImageName(filePath);
                            sb.append(imageName + "\n");
                            File file = new File(filePath);
                            mFileList.add(file);
                        }
                        tv_uploadImage.setText(sb.toString());
                        //                        this.mBitmapUtils.display(this.mUserHeaderImageView, headerImg);
                    }
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
