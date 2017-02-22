package com.henghao.parkland.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.model.protocol.HttpPublic;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zbar.lib.zxing.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by 晏琦云 on 2017/2/10.
 * 主页面
 */
public class YanghuMainActivity extends ActivityFragmentSupport {

    private static final int REQUEST_CODE_TREEMESSAGE = 0x0000;//植物信息录入request code
    private static final int REQUEST_CODE_YANGHU = 0x0001;//植物养护request code

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    @InjectView(R.id.sp_state_maintenace)
    Spinner sp_State;
    @InjectView(R.id.btn_yhmanage_main)
    Button btnYhmanage;
    @InjectView(R.id.btn_treeyh_main)
    Button btnTreeYh;
    @InjectView(R.id.btn_treemessage_main)
    Button btnTreemessage;
    private String[] state_array = {"施肥", "浇水", "除草", "除虫", "修枝", "防风防汛", "防寒防冻", "防日灼", "扶正", "补栽"};//养护状态选项
    private ArrayAdapter<String> state_Adapter;
    private String str_state;//养护状态
    /**
     * 网络访问相关
     */
    private OkHttpClient okHttpClient;

    // 定位相关声明
    public LocationClient locationClient = null;
    private String addrStr;//获取到的GPS定位地理位置信息
    private static final String TAG = "YanghuMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        this.mActivityFragmentView.viewMain(R.layout.activity_yanmain);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.VISIBLE);
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
        mLeftTextView.setText("养护管理");
    }

    @Override
    public void initData() {
        super.initData();
        /**
         * 初始化养护状态下拉列表框
         */
        state_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, state_array);
        state_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_State.setAdapter(state_Adapter);
        sp_State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_state = state_array[position];
                Log.i(TAG, "onItemSelected: " + str_state);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * 定位
         */
        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(this.myListener); // 注册监听函数
        setLocationOption(); // 设置定位参数
        locationClient.start(); // 开始定位
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    private boolean checkNetworkState() {
        boolean flag = false;
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            addrStr = location.getAddrStr();
            /**
             * 如果GPS未打开且无网络
             */
            if (!checkNetworkState()) {
                addrStr = null;
                Toast.makeText(YanghuMainActivity.this,
                        "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        this.locationClient.setLocOption(option);
    }

    // 三个状态实现地图生命周期管理
    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        this.locationClient.stop();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.locationClient.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.locationClient.start(); // 开始定位
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传------植物养护信息
        if (requestCode == REQUEST_CODE_YANGHU && resultCode == RESULT_OK) {
            if (data != null) {
                if (addrStr == null) {
                    Toast.makeText(YanghuMainActivity.this,
                            "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String content = data.getStringExtra(DECODED_CONTENT_KEY);//二维码返回结果
//                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);//二维码照片
                okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                /**
                 * 查询二维码ID
                 */
                Request request = builder.url(HttpPublic.QUERYBYID + "?chip=" + content).get().build();
                /**
                 * 把request封装
                 */
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(YanghuMainActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String result = response.body().string();
                        Log.i(TAG, "onResponse: " + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int error = jsonObject.getInt("error");
                            if (error == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(YanghuMainActivity.this, "该二维码不存在数据！请先录入植物信息！", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });
                            } else {
                                /**
                                 *   获取养护时间
                                 */
                                Date date = new Date(System.currentTimeMillis());
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                String time_format = dateFormat.format(date);//格式化时间
                                /**
                                 * 跳转到植物养护信息界面
                                 */
                                Intent intent = new Intent(YanghuMainActivity.this, MaintenanceActivity.class);
                                intent.putExtra("treeId", content);
                                intent.putExtra("yhStatusname", str_state);
                                intent.putExtra("yhStatustime", time_format);
                                intent.putExtra("yhStatussite", addrStr);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(YanghuMainActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        } else if (requestCode == REQUEST_CODE_TREEMESSAGE && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);//二维码返回结果
                if (addrStr == null) {
                    Toast.makeText(YanghuMainActivity.this,
                            "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * 跳转到植物信息录入界面
                 */
                Intent intent = new Intent(YanghuMainActivity.this, TreeMessageActivity.class);
                intent.putExtra("treeId", content);
                intent.putExtra("treeSite", addrStr);
                startActivity(intent);
            }
        }
    }

    @OnClick({R.id.btn_treemessage_main, R.id.btn_treeyh_main, R.id.btn_yhmanage_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_treemessage_main: {
                Intent intent = new Intent(YanghuMainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_TREEMESSAGE);
                break;
            }
            case R.id.btn_treeyh_main: {
                Intent intent = new Intent(YanghuMainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_YANGHU);
                break;
            }
            case R.id.btn_yhmanage_main: {
                Intent intent = new Intent(YanghuMainActivity.this, YhManageActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

}
