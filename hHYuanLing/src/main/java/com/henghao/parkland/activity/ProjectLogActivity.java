package com.henghao.parkland.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.utils.FileUtils;
import com.henghao.parkland.views.DateChooseWheelViewDialog;
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
import java.util.List;


/**
 * 日志备忘
 */
public class ProjectLogActivity extends ActivityFragmentSupport {
    private ArrayList<String> mSelectPath;

    private static final int REQUEST_IMAGE = 2;
    private int REQUEST_FILE = 1001;

    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.tv_yujitime)
    private TextView tv_yujitime;

    /**
     * 日期
     */
    private String mData;
    /**
     * 预计日期
     */
    private String mYjData;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    /**
     * 项目名
     */
    @ViewInject(R.id.et_name)
    private TextView et_name;

    /**
     * 项目进度
     */
    @ViewInject(R.id.et_jindu)
    private TextView et_jindu;

    /**
     * 差异原因
     */
    @ViewInject(R.id.et_reson)
    private TextView et_reson;

    /**
     * 资源需求
     */
    @ViewInject(R.id.et_ziyuan)
    private TextView et_ziyuan;

    /**
     * 接受者
     */
    @ViewInject(R.id.et_username)
    private TextView et_username;

    /**
     * 备注
     */
    @ViewInject(R.id.et_beizhu)
    private TextView et_beizhu;

    /**
     * 文件选择
     */
    @ViewInject(R.id.tv_selectfile)
    private TextView tv_selectfile;
    /**
     * 图片
     */
    @ViewInject(R.id.tv_selectphoto)
    private TextView tv_selectphoto;
    private File imagefile;
    private File douFile;
    private List<File> mListFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_log);
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
        mLeftTextView.setText("日志备忘");
        tv_title.setText("日志备忘");
    }

    @OnClick({R.id.tv_yujitime, R.id.tv_time, R.id.tv_selectfile, R.id.tv_selectphoto, R.id.btn_sub})
    private void viewOnclick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:
                getDialogTime("日期", 1);
                break;
            case R.id.tv_yujitime:
                getDialogTime("预计完成日期", 2);
                break;
            case R.id.tv_selectfile:
                showFileChooser();
                break;
            case R.id.tv_selectphoto:
                addPic();
                break;
            case R.id.btn_sub:
                if (checkData()) {
                    uploadUserHeader(mListFile, douFile);
                }
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(et_name.getText().toString().trim())) {
            msg("项目名称不能为空");
            return false;
        }

        if (ToolsKit.isEmpty(mData)) {
            msg("请选择日期");
            return false;
        }
        if (ToolsKit.isEmpty(et_jindu.getText().toString().trim())) {
            msg("项目进度不能为空");
            return false;
        }
        if (ToolsKit.isEmpty(et_reson.getText().toString().trim())) {
            msg("差异原因不能为空");
            return false;
        }
        if (ToolsKit.isEmpty(et_ziyuan.getText().toString().trim())) {
            msg("资源需求不能为空");
            return false;
        }
        if (ToolsKit.isEmpty(mYjData)) {
            msg("请选择预计完成日期");
            return false;
        }
        if (ToolsKit.isEmpty(et_username.getText().toString().trim())) {
            msg("接收者不能为空");
            return false;
        }

        return true;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), REQUEST_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private DateChooseWheelViewDialog getDialogTime(String title, final int whatPos) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                switch (whatPos) {
                    case 1:
                        mData = time;
                        tv_time.setText("日期：" + time);
                        break;
                    case 2:
                        mYjData = time;
                        tv_yujitime.setText("预计完成日期：" + time);
                        break;
                }
            }
        });
        startDateChooseDialog.setDateDialogTitle(title);
        startDateChooseDialog.showDateChooseDialog();
        startDateChooseDialog.setCanceledOnTouchOutside(true);
        return startDateChooseDialog;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1001:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    douFile = new File(path);
                    tv_selectfile.setText("文件名：" + douFile.getName());
                }
                break;
            case REQUEST_IMAGE:
                this.mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                mListFile = new ArrayList<>();
                List<String> mName = new ArrayList<>();
                if (!ToolsKit.isEmpty(this.mSelectPath)) {
                    for (String file : mSelectPath) {
                        mListFile.add(new File(file));
                        mName.add(new File(file).getName());
                    }
                }
                tv_selectphoto.setText("图片名：" + mName.toString());
                /*if (!ToolsKit.isEmpty(this.mSelectPath)) {
                    String headerImg = this.mSelectPath.get(0);
                    //                        this.mBitmapUtils.display(this.mUserHeaderImageView, headerImg);
                    imagefile = new File(headerImg);
                }*/
                break;
        }
    }

    private void upLoadDou() {

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

    public void uploadUserHeader(List<File> photo, File document) {
        mActivityFragmentView.viewLoading(View.VISIBLE);
        /**
         * 请求访问网络
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        MultipartBuilder multipartBilder = new MultipartBuilder();
        String name = et_name.getText().toString().trim();//项目名
        String jindu = et_jindu.getText().toString().trim();//项目进度
        String reson = et_reson.getText().toString().trim();//差异原因
        String ziyuan = et_ziyuan.getText().toString().trim();//资源需求
        String beizhu = et_beizhu.getText().toString().trim();//备注
        String userName = et_username.getText().toString().trim();//接收者

        multipartBilder.type(MultipartBuilder.FORM)
                .addFormDataPart("projectTime", mData)
                .addFormDataPart("uid", getLoginUid())
                .addFormDataPart("requests", ziyuan)
                .addFormDataPart("projectPlan", jindu)
                .addFormDataPart("reason", reson)
                .addFormDataPart("send", userName)
                .addFormDataPart("predictfinishTime", mYjData)
                .addFormDataPart("comments", beizhu)
                .addFormDataPart("projectName", name);//名称
        if (photo != null) {
            for (File file : photo) {
                multipartBilder.addFormDataPart(file.getName(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));//图片
            }
        }
        if (document != null) {
            multipartBilder.addFormDataPart(document.getName(), document.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), document));//文件
        }
        RequestBody requestBody = multipartBilder.build();
        Request request = builder.post(requestBody).url(ProtocolUrl.PROJECT_RZBEIWANG).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        msg("网络请求错误！");
                        mActivityFragmentView.viewLoading(View.GONE);
                    }
                });
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
                            Toast.makeText(ProjectLogActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
