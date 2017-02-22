package com.benefit.buy.library.phoneview;

import java.io.File;
import java.util.ArrayList;

import com.benefit.buy.library.R;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 多图选择 Created by Nereo on 2015/4/7.
 */
public class MultiImageSelectorActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback {

    /** 最大图片选择次数，int类型，默认9 */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";

    /** 图片选择模式，默认多选 */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";

    /** 是否显示相机，默认显示 */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";

    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合 */
    public static final String EXTRA_RESULT = "select_result";

    /** 默认选择集 */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /** 单选 */
    public static final int MODE_SINGLE = 0;

    /** 多选 */
    public static final int MODE_MULTI = 1;

    private ArrayList<String> resultList = new ArrayList<String>();

    private Button mSubmitButton;

    private int mDefaultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        //        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        //        mTintManager.setStatusBarTintEnabled(true);
        //                mTintManager.setNavigationBarTintEnabled(true);
        //        mTintManager.setStatusBarTintResource(R.color.phone_bar_color);
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            setTranslucentStatus(true);
        //        }
        Intent intent = getIntent();
        this.mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if ((mode == MODE_MULTI) && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            this.resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, this.mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, this.resultList);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();
        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //                setResult(RESULT_CANCELED);
                //                finish();
                Intent data = new Intent();
                data.putStringArrayListExtra(EXTRA_RESULT, MultiImageSelectorActivity.this.resultList);
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });
        // 完成按钮
        this.mSubmitButton = (Button) findViewById(R.id.commit);
        if (this.mDefaultCount == 1) {
            this.mSubmitButton.setVisibility(View.GONE);
        }
        if ((this.resultList == null) || (this.resultList.size() <= 0)) {
            this.mSubmitButton.setText("完成");
            this.mSubmitButton.setEnabled(false);
        }
        else {
            this.mSubmitButton.setText("完成(" + this.resultList.size() + "/" + this.mDefaultCount + ")");
            this.mSubmitButton.setEnabled(true);
        }
        this.mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if ((MultiImageSelectorActivity.this.resultList != null)
                        && (MultiImageSelectorActivity.this.resultList.size() > 0)) {
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, MultiImageSelectorActivity.this.resultList);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        }
        else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        this.resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, this.resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if (!this.resultList.contains(path)) {
            this.resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if (this.resultList.size() > 0) {
            this.mSubmitButton.setText("完成(" + this.resultList.size() + "/" + this.mDefaultCount + ")");
            if (!this.mSubmitButton.isEnabled()) {
                this.mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path) {
        if (this.resultList.contains(path)) {
            this.resultList.remove(path);
            this.mSubmitButton.setText("完成(" + this.resultList.size() + "/" + this.mDefaultCount + ")");
        }
        else {
            this.mSubmitButton.setText("完成(" + this.resultList.size() + "/" + this.mDefaultCount + ")");
        }
        // 当为选择图片时候的状态
        if (this.resultList.size() == 0) {
            this.mSubmitButton.setText("完成");
            this.mSubmitButton.setEnabled(false);
        }
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            Intent data = new Intent();
            this.resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, this.resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
