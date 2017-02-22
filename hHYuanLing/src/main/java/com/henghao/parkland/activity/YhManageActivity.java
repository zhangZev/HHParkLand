package com.henghao.parkland.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.YhAdapter;
import com.henghao.parkland.model.entity.YhBean;
import com.henghao.parkland.model.protocol.HttpPublic;
import com.henghao.parkland.views.dialog.DialogList;
import com.henghao.parkland.views.dialog.DialogYanghu;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zbar.lib.zxing.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by 晏琦云 on 2017/2/13.
 * 养护管理界面
 */

public class YhManageActivity extends ActivityFragmentSupport {


    private static final int REQUEST_CODE_TREEMESSAGE = 0x0000;//植物信息录入request code
    private static final int REQUEST_CODE_YANGHU = 0x0001;//植物养护request code

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    // 定位相关声明
    public LocationClient locationClient = null;
    private String addrStr;//获取到的GPS定位地理位置信息

    @InjectView(R.id.lv_yhmanage)
    ListView listView;
    @InjectView(R.id.tv_state_yhmanage)
    TextView tvState;
    private YhAdapter adapter;
    private List<YhBean> dataList;//数据信息列表
    /**
     * 网络访问相关
     */
    private OkHttpClient okHttpClient;
    private static final String TAG = "YhManageActivity";

    private String[] state_array = {"施肥", "浇水", "除草", "除虫", "修枝", "防风防汛", "防寒防冻", "防日灼", "扶正", "补栽"};//养护状态选项
    private ArrayAdapter<String> state_Adapter;
    private String str_state;//养护状态
    private DialogYanghu dialogYanghu;
    private Request request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.acitivty_yhmanage);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        /**
         * 定位
         */
        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(this.myListener); // 注册监听函数
        setLocationOption(); // 设置定位参数
        locationClient.start(); // 开始定位
        okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        request = builder.url(HttpPublic.YHMSG).get().build();

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YhBean bean = dataList.get(position);
                Intent intent = new Intent(YhManageActivity.this, GuanhuActivity.class);
                intent.putExtra("yid", bean.getId());//养护信息ID
                intent.putExtra("treeId", bean.getTreeId());//植物二维码
                intent.putExtra("yhSite", bean.getYhStatussite());//养护地点
                intent.putExtra("yhTime", bean.getYhStatustime());//养护时间
                startActivity(intent);
            }
        });*/
    }


    private void getDataReq(Request request) {
        /**
         * 封装request
         */
        Call call = okHttpClient.newCall(request);
        this.mActivityFragmentView.viewLoading(View.VISIBLE);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivityFragmentView.viewLoading(View.GONE);
                        Toast.makeText(YhManageActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String str_result = response.body().string();//请求网络返回结果
                Log.i(TAG, "onResponse: " + str_result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dataList = parseJson(str_result);
                        Collections.reverse(dataList);
                        mActivityFragmentView.viewLoading(View.GONE);
                        if (dataList.size() == 0) {
                            listView.setVisibility(View.GONE);
                            tvState.setVisibility(View.VISIBLE);
                            tvState.setText("你今天还没有对植物进行养护哦！");
                        } else {
                            tvState.setVisibility(View.GONE);
                            adapter = new YhAdapter(dataList, YhManageActivity.this);
                            listView.setAdapter(adapter);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void initData() {
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("养护管理");
        initWithRightBar();
        mRightImageView.setVisibility(View.VISIBLE);
        mRightImageView.setImageResource(R.drawable.scan_right);
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //二维码
                dialogYanghu = getDialogYanghu();
                dialogYanghu.show();
            }
        });
    }

    /**
     * 弹出对话框
     *
     * @return
     */
    @NonNull
    private DialogYanghu getDialogYanghu() {
        return new DialogYanghu(YhManageActivity.this, new DialogYanghu.DialogAlertListener() {
            @Override
            public void onDialogCreate(Dialog dlg) {

            }

            @Override
            public void onDialogOk(Dialog dlg) {
                DialogList mdialogList = dialogList();
                dialogYanghu.cancel();
                mdialogList.show();
            }

            @Override
            public void onDialogCancel(Dialog dlg) {
                //录入
                Intent intent = new Intent(YhManageActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_TREEMESSAGE);
            }
        });


    }

    private DialogList dialogList() {
        return new DialogList(YhManageActivity.this, new DialogList.DialogAlertListener() {
            @Override
            public void onDialogCreate(Dialog dlg) {

            }

            @Override
            public void onDialogOk(Dialog dlg, String par) {
                str_state = par;
                Intent intent = new Intent(YhManageActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_YANGHU);
            }

            @Override
            public void onDialogCancel(Dialog dlg) {

            }
        });
    }

    /**
     * 解析Json字符串
     */
    private List<YhBean> parseJson(String str_result) {
        List<YhBean> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(str_result);
            int error = jsonObject.getInt("error");//错误代码 0 错误 1 正确
            if (error == 1) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject yhObject = dataArray.getJSONObject(i);
                    YhBean bean = new YhBean();
                    int id = yhObject.getInt("yid");//养护信息ID
                    String treeId = yhObject.getString("treeId");//植物二维码
                    String yhStatusname = yhObject.getString("yhStatusname");//养护行为
                    String yhStatussite = yhObject.getString("yhStatussite");//养护地点
                    String yhStatustime = yhObject.getString("yhStatustime");//养护时间
                    int isNo = yhObject.getInt("isNo");//养护信息ID
                    bean.setId(id);
                    bean.setTreeId(treeId);
                    bean.setYhStatusname(yhStatusname);
                    bean.setYhStatussite(yhStatussite);
                    bean.setYhStatustime(yhStatustime);
                    bean.setIsNo(isNo);
                    data.add(bean);
                }
            } else {
                String result = jsonObject.getString("result");
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(YhManageActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return data;
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
                Toast.makeText(YhManageActivity.this,
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
        getDataReq(request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传------植物养护信息
        if (requestCode == REQUEST_CODE_YANGHU && resultCode == RESULT_OK) {
            if (data != null) {
                if (addrStr == null) {
                    Toast.makeText(YhManageActivity.this,
                            "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = data.getExtras();
                // 显示扫描到的内容
                final String content = bundle.getString("result");
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
                                Toast.makeText(YhManageActivity.this, "网络访问错误！", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(YhManageActivity.this, "该二维码不存在数据！请先录入植物信息！", Toast.LENGTH_SHORT).show();
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
                                Intent intent = new Intent(YhManageActivity.this, MaintenanceActivity.class);
                                intent.putExtra("treeId", content);
                                intent.putExtra("yhStatusname", str_state);
                                intent.putExtra("yhStatustime", time_format);
                                intent.putExtra("yhStatussite", addrStr);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(YhManageActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        } else if (requestCode == REQUEST_CODE_TREEMESSAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                // 显示扫描到的内容
                String content = bundle.getString("result");
                if (addrStr == null) {
                    Toast.makeText(YhManageActivity.this,
                            "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * 跳转到植物信息录入界面
                 */
                Intent intent = new Intent(YhManageActivity.this, TreeMessageActivity.class);
                intent.putExtra("treeId", content);
                intent.putExtra("treeSite", addrStr);
                startActivity(intent);
            }
        }
    }
}
