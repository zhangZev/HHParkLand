package com.henghao.parkland.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.android.uhflibs.as3992_native;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.utils.tools.ToolsToast;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.NotPollingAdapter;
import com.henghao.parkland.model.entity.TreeEntity;
import com.henghao.parkland.model.protocol.NfcDetailsProtocol;
import com.henghao.parkland.utils.DeviceControl;
import com.henghao.parkland.utils.LocationUtils;
import com.henghao.parkland.views.dialog.DialogAlertEdit;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 巡检人员 〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016-11-17
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ISOActivity extends ActivityFragmentSupport {

    private DialogAlertEdit dlg;

    /**
     * 未巡检
     */
    @ViewInject(R.id.m_bottom_left1)
    private TextView m_bottom_left1;

    /**
     * 全部
     */
    @ViewInject(R.id.m_bottom_right1)
    private TextView m_bottom_right1;

    /**
     * 名字
     */
    @ViewInject(R.id.m_name)
    private TextView m_name;

    @ViewInject(R.id.rl_bottom)
    private RelativeLayout rl_bottom;

    private MapView mMapView;

    @ViewInject(R.id.listView_search_epclist)
    private ListView mListview;

    private boolean isLogin;

    private List<TreeEntity> mList;

    private int allTreeNum;

    private int notTreeNum;

    private as3992_native native_method;

    private int init_progress = 0;

    private DeviceControl DevCtrl;

    private ReadThread rthread;

    private PowerManager pM = null;

    private PowerManager.WakeLock wK = null;

    private SearchThread st;

    private boolean inSearch = false;

    private Handler handler = null;

    private SoundPool soundPool;

    private int soundId;

    private ArrayList<String> listIsoId = null;

    private ArrayList<String> epcs = null;

    private TextView tv_alltreeTextView;

    private NotPollingAdapter mNotAdapter;

    private BaiduMap mBaiduMap;

    @ViewInject(R.id.m_add)
    private TextView m_add;

    private final List<String> idStrings = new ArrayList<String>();

    private String address;

    private double lat;

    private double lng;

    private LocationMode mCurrentMode;

    private LocationClient mLocClient;

    private BitmapDescriptor mCurrentMarker;

    public MyLocationListenner myListener = new MyLocationListenner();

    private boolean ready = true; // 是否获取nfc信息

    private NfcAdapter mAdapter;

    private PendingIntent mPendingIntent;

    private IntentFilter[] mFilters;

    private String[][] mTechLists;

    private String idFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_iso);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
        setContentView(this.mActivityFragmentView);
        // 获取地图控件引用
        this.mMapView = (MapView) findViewById(R.id.bmapView);
        com.lidroid.xutils.ViewUtils.inject(this);
        this.mListview.setFocusable(false);
        this.mListview.setFocusableInTouchMode(false);
        this.listIsoId = new ArrayList<String>();
        initWidget();
        View child = this.mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        locationMap();
        //		this.mMapView.showZoomControls(false);
        nfcAdapterView();
    }

    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void nfcAdapterView() {
        // TODO Auto-generated method stub
        this.mAdapter = NfcAdapter.getDefaultAdapter(this);
        // 创建一个通用的PendingIntent，将交付给了这个活动。NFC堆栈将填写的意图与发现的标签的细节提供这个活动之前。
        this.mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // 设置所有的MIME基础派遣一个意图过滤器
        IntentFilter ntech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        this.mFilters = new IntentFilter[] {ntech,};
        // 设置所有nfcf标签技术清单
        this.mTechLists = new String[][] {new String[] {MifareClassic.class.getName()},
                new String[] {NfcV.class.getName()}};
    }

    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void locationMap() {
        mapLoc();
        this.mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                for (int i = 0; i < ISOActivity.this.mList.size(); i++) {
                    if (marker.getTitle().contains(ISOActivity.this.mList.get(i).getTreeName())) {
                        // 第一个数:马尾松
                        //						intent.setClass(ISOActivity.this, ScanDetailActivity.class);
                        intent.putExtra(Constant.INTNET_TYPE, 111);
                        intent.putExtra(Constant.INTNET_DATA, ISOActivity.this.mList.get(i));
                        startActivity(intent);
                    }
                }
                return false;
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see com.henghao.wenbo.ActivityFragmentSupport#initWidget()
     */
    @Override
    public void initWidget() {
        // TODO Auto-generated method stub
        showLoginAlert();
        initWithCenterBar();
        this.mCenterTextView.setText("园林大数据溯源云平台");
        this.mNfcDetailsProtocol = new NfcDetailsProtocol(this);
        this.mNfcDetailsProtocol.addResponseListener(this);
    }

    private void locMarkerAllAdd(TreeEntity treeEntity) {
        // 定义Maker坐标点
        LatLng point = new LatLng(treeEntity.getLat(), treeEntity.getLng());
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.tree_marker2);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).title(treeEntity.getTreeName()).icon(bitmap);
        // 在地图上添加Marker，并显示
        Marker marker = (Marker) this.mBaiduMap.addOverlay(option);
        this.markers = new ArrayList<Marker>();
        this.markers.add(marker);
    }

    private void locMarkerAdd(TreeEntity treeEntity) {
        for (int i = 0; i < this.markers.size(); i++) {
            if (treeEntity.getTreeName().equals(this.markers.get(i).getTitle())) {
                this.markers.get(i).remove();
                break;
            }
        }
        // 定义Maker坐标点
        LatLng point = new LatLng(treeEntity.getLat(), treeEntity.getLng());
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.tree_marker);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).title(treeEntity.getTreeName()).icon(bitmap);
        // 在地图上添加Marker，并显示
        Marker marker = (Marker) this.mBaiduMap.addOverlay(option);
        this.markers = new ArrayList<Marker>();
        this.markers.add(marker);
    }

    private void initDevice() {
        //		GetFindAll();
        this.native_method = new as3992_native();
        if (this.native_method.OpenComPort("/dev/ttyMT2") != 0) {
            System.out.println("这里啦");
            //            Cur_Tag_Info.setText("Open serialport failed");
            return;
        }
        this.init_progress++;
        try {
            this.DevCtrl = new DeviceControl("/sys/class/misc/mtgpio/pin");
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.d("as3992_ac", "AAA");
            System.out.println("这里啦");
            //            new AlertDialog.Builder(this).setTitle(R.string.DIA_ALERT).setMessage(R.string.DEV_OPEN_ERR).setPositiveButton(R.string.DIA_CHECK, new DialogInterface.OnClickListener() {
            //
            //                @Override
            //                public void onClick(DialogInterface dialog, int which) {
            //                    finish();
            //                }
            //            }).show();
            return;
        }
        try {
            this.DevCtrl.PowerOnDevice();
            Log.i("as3992_destroy", "poweron");
        }
        catch (IOException e) {
        }
        this.init_progress++;
        this.rthread = new ReadThread();
        this.rthread.start();
        this.pM = (PowerManager) getSystemService(POWER_SERVICE);
        if (this.pM != null) {
            this.wK = this.pM.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE,
                    "lock3992");
            if (this.wK != null) {
                this.wK.acquire();
                this.init_progress++;
            }
        }
        if (this.init_progress == 2) {
            Log.w("3992_6C", "wake lock init failed");
        }
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        byte[] soft_ver = this.native_method.get_version(0);
        if (soft_ver == null) {
            //            Cur_Tag_Info.setText("Communication Error");
            System.out.println("这里啦");
            return;
        }
        else {
            System.out.println("这里啦");
            //            Cur_Tag_Info.setText("Firmware: ");
            //            Cur_Tag_Info.append(new String(soft_ver, 0, soft_ver.length));
            //
            //            Set_Tag.setEnabled(true);
            //            Search_Tag.setEnabled(true);
            //            Read_Tag.setEnabled(true);
            //            Write_Tag.setEnabled(true);
            //            Set_EPC.setEnabled(true);
            //            Set_Password.setEnabled(true);
            //            Lock_Tag.setEnabled(true);
            //            Area_Select.setEnabled(true);
            this.native_method.set_gen2(0, (byte) 6);
            this.native_method.set_gen2(1, (byte) 1);
            this.native_method.set_gen2(3, (byte) 1);
            set_sensitivity(-72);
            // power_antenna(true);
            set_hopping_freq(920625, 924375, 750, -40);
            this.native_method.set_alloc_param(1, 10000, 0);
        }
        /*************************************************************************************/
        this.soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        if (this.soundPool == null) {
            Log.e("as3992", "Open sound failed");
        }
        this.soundId = this.soundPool.load("/system/media/audio/ui/VideoRecord.ogg", 0);
        Log.w("as3992_6C", "id is " + this.soundId);
        this.handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    ISOActivity.this.soundPool.play(ISOActivity.this.soundId, 1, 1, 0, 0, 1);
                    String[] tmp = new String[ISOActivity.this.epcs.size()];
                    ISOActivity.this.epcs.toArray(tmp);
                    for (int k = 0; k < tmp.length; k++) {
                        System.out.println("tmp[" + k + "]:::::::" + tmp[k].toString());
                        // 获取到数据啦
                        //					
                        ISOActivity.this.nfcId = tmp[k];
                        ISOActivity.this.mNfcDetailsProtocol.getNfcById(tmp[k]);
                        ISOActivity.this.mActivityFragmentView.viewLoading(View.VISIBLE);
                        //						GetUpdateNfc(tmp[k]);
                    }
                }
                else {
                }
            }
        };
        this.st = new SearchThread();
        this.inSearch = true;
        power_antenna(true);
        this.st.start();
    }

    private void mapLoc() {
        this.mBaiduMap = this.mMapView.getMap();
        this.mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 设置为普通模式的地图
        // 开启定位图层
        this.mBaiduMap.setMyLocationEnabled(true);
        this.mLocClient = new LocationClient(this); // 定位用到的一个类
        this.mLocClient.registerLocationListener(this.myListener); // 注册监听
        // /LocationClientOption类用来设置定位SDK的定位方式，
        LocationClientOption option = new LocationClientOption(); // 以下是给定位设置参数
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        this.mLocClient.setLocOption(option);
        this.mLocClient.start();
    }

    boolean isFirstLoc = true; // 是否首次定位

    private List<Marker> markers;

    private NfcDetailsProtocol mNfcDetailsProtocol;

    private String nfcId;

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || ISOActivity.this.mMapView == null) {
                return;
            }
            ISOActivity.this.address = location.getAddrStr();
            ISOActivity.this.lat = location.getLatitude();
            ISOActivity.this.lng = location.getLongitude();
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            ISOActivity.this.mBaiduMap.setMyLocationData(locData);
            if (ISOActivity.this.address == null) {
                if (LocationUtils.getAddress() == null) {
                    ISOActivity.this.m_add.setText("位置：定位失败");
                }
                else {
                    ISOActivity.this.m_add.setText("位置：" + LocationUtils.getAddress());
                }
            }
            else {
                ISOActivity.this.m_add.setText("位置：" + ISOActivity.this.address);
            }
            if (ISOActivity.this.address == null) {
                ISOActivity.this.isFirstLoc = true;
            }
            if (ISOActivity.this.isFirstLoc) {
                ISOActivity.this.isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(20.0f);
                ISOActivity.this.mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void UpdateVIew(TreeEntity treeEntity) {
        // TODO Auto-generated method stub
        AddTreeAll();
        this.mNotAdapter.notifyDataSetChanged();
    }

    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void initComentData() {
        this.mList = new ArrayList<TreeEntity>();
        if (this.address == null) {
            if (LocationUtils.getAddress() == null) {
                this.m_add.setText("位置：定位失败");
            }
            else {
                this.m_add.setText("位置：" + LocationUtils.getAddress());
            }
        }
        else {
            this.m_add.setText("位置：" + this.address);
        }
        if (this.lat == 0) {
            this.lat = LocationUtils.getLat();
        }
        if (this.lng == 0) {
            this.lng = LocationUtils.getLng();
        }
        // 添加ListView头部
        LayoutInflater mInflater = LayoutInflater.from(this);
        View heView = mInflater.inflate(R.layout.include_isotitle, this.mListview, false);
        this.tv_alltreeTextView = (TextView) heView.findViewById(R.id.tv_alltree);
        this.mListview.addHeaderView(heView);
        TreeEntity mEntity = new TreeEntity();
        mEntity.setNfcNo("db-51-9f-7a-00-01-04-e0");// e2 00 51 09 67 13 01 17 08 20 c8 1c
        mEntity.setNfcId("bc-55-9f-7a-00-01-04-e0");// 8b-5a-9f-7a-00-01-04-e0
        mEntity.setGreenMianji("10平方米");
        //		mEntity.setLat(26.630245621876607);
        //		mEntity.setLng(106.63856540747065);
        mEntity.setLat(this.lat + 0.0001);
        mEntity.setLng(this.lng);
        mEntity.setTreeName("马尾松");
        mEntity.setAllTree(150);
        mEntity.setTreenot(150);
        //		mEntity.setImageResouse(R.drawable.tree1);
        mEntity.setTreeAddress("非洲南部、台湾、中国大陆秦岭淮河以南");
        mEntity.setTreeAlong("松属");
        mEntity.setTreeChandi("贵阳");
        mEntity.setTreeHeight("6.4米");
        mEntity.setTreeLocation("贵州省贵阳市乌当区黔灵山路");
        mEntity.setTreeNianLing("56岁");
        mEntity.setTreeZhiJing("0.8米");
        mEntity.setTreeJK("健康");
        mEntity.setSuoshu("贵州省林业厅");
        mEntity.setFuzeren("王丹");
        mEntity.setMiaomu("贵州大众苗木");
        this.mList.add(mEntity);
        TreeEntity mEntity2 = new TreeEntity();
        mEntity2.setTreeName("桂花树");
        mEntity2.setNfcNo("e2 00 40 74 94 03 00 60 24 40 1c 36");
        mEntity2.setAllTree(595);
        mEntity2.setTreenot(595);
        mEntity2.setGreenMianji("6平方米");
        //		mEntity2.setImageResouse(R.drawable.tree2);
        mEntity2.setTreeAddress("原产我国西南部，现各地广泛栽培。");
        mEntity2.setNfcId("86-7d-8d-2b-50-01-04-e0");
        //		mEntity2.setLat(26.629248185040424);
        //		mEntity2.setLng(106.63762126989741);
        mEntity2.setLat(this.lat);
        mEntity2.setLng(this.lng + 0.0001);
        mEntity2.setTreeAlong("木犀属(Osmanthus)");
        mEntity2.setTreeChandi("遵义");
        mEntity2.setTreeHeight("3.4米");
        mEntity2.setTreeLocation("贵州省贵阳市乌当区黔灵山路");
        mEntity2.setTreeNianLing("28岁");
        mEntity2.setTreeZhiJing("0.4米");
        mEntity2.setTreeJK("健康");
        mEntity2.setSuoshu("贵州省林业厅");
        mEntity2.setFuzeren("王丹");
        mEntity2.setMiaomu("贵州大众苗木");
        this.mList.add(mEntity2);
        TreeEntity mEntity3 = new TreeEntity();
        mEntity3.setNfcNo("e2 00 40 74 94 03 00 60 24 40 1c 39");
        mEntity3.setNfcId("6d-d0-8d-2b-50-01-04-e0");
        mEntity3.setTreeName("白杨树");
        mEntity3.setGreenMianji("5平方米");
        mEntity3.setAllTree(746);
        mEntity3.setTreenot(746);
        mEntity3.setLat(this.lat);
        mEntity3.setLng(this.lng + 0.0002);
        //		mEntity3.setLat(26.62855764674586);
        //		mEntity3.setLng(106.63942371435542);
        //		mEntity3.setImageResouse(R.drawable.tree3);
        mEntity3.setTreeAddress("欧洲；北非；亚洲");
        mEntity3.setTreeAlong("杨属");
        mEntity3.setTreeChandi("贵阳");
        mEntity3.setTreeHeight("7.1米");
        mEntity3.setTreeLocation("贵州省贵阳市乌当区黔灵山路");
        mEntity3.setTreeNianLing("85岁");
        mEntity3.setTreeZhiJing("1.3米");
        mEntity3.setTreeJK("健康");
        mEntity3.setSuoshu("贵州省林业厅");
        mEntity3.setFuzeren("张晓鸥");
        mEntity3.setMiaomu("贵州大众苗木");
        this.mList.add(mEntity3);
        this.mNotAdapter = new NotPollingAdapter(this, this.mList);
        this.mListview.setAdapter(this.mNotAdapter);
        AddTreeAll();
        for (int i = 0; i < ISOActivity.this.mList.size(); i++) {
            locMarkerAllAdd(ISOActivity.this.mList.get(i));
        }
        this.mNotAdapter.notifyDataSetChanged();
    }

    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void AddTreeAll() {
        // TODO Auto-generated method stub
        this.allTreeNum = 0;
        this.notTreeNum = 0;
        for (int i = 0; i < this.mList.size(); i++) {
            this.allTreeNum += this.mList.get(i).getAllTree();
        }
        for (int i = 0; i < this.mList.size(); i++) {
            if (this.mList.get(i).isCheck()) {
                if (this.mList.get(i).getTreenot() != 0) {
                    this.mList.get(i).setTreenot(this.mList.get(i).getTreenot() - 1);
                }
            }
            this.notTreeNum += this.mList.get(i).getTreenot();
        }
        this.tv_alltreeTextView.setText(this.allTreeNum + "棵");
        this.m_bottom_left1.setText("未检测" + this.notTreeNum + "棵");
        this.m_bottom_right1.setText("已检测" + (this.allTreeNum - this.notTreeNum) + "棵");
    }

    /*
     * (non-Javadoc)
     * @see com.henghao.wenbo.ActivityFragmentSupport#onResume()
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        this.mMapView.onResume();
        if (!hasInternetConnected()) {
            ISOActivity.this.isFirstLoc = true;
        }
        if (this.isLogin) {
            this.dlg.cancel();
        }
        else {
            this.dlg.show();
        }
        if (this.mAdapter != null) {
            this.mAdapter.enableForegroundDispatch(this, this.mPendingIntent, this.mFilters, this.mTechLists);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.henghao.wenbo.ActivityFragmentSupport#onPause()
     */
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        this.mMapView.onPause();
    }

    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void showLoginAlert() {
        // TODO Auto-generated method stub
        this.dlg = new DialogAlertEdit(ISOActivity.this, new DialogAlertEdit.DialogAlertListener() {

            @Override
            public void onDialogOk(Dialog dlg, Object param) {
                final String userName = param.toString();
                if (userName.equals("123")) {
                    if (!hasInternetConnected()) {
                        ToolsToast.show(ISOActivity.this, "请检查网络连接");
                        return;
                    }
                    //					ISOActivity.this.isFirstLoc = true;
                    ISOActivity.this.mActivityFragmentView.viewLoading(View.VISIBLE);
                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            initDevice();
                            ISOActivity.this.mActivityFragmentView.viewLoading(View.GONE);
                            ISOActivity.this.rl_bottom.setVisibility(View.VISIBLE);
                            ISOActivity.this.m_name.setText("当前巡检工号为:" + userName);
                            initComentData();
                            //							getLoginUserSharedPre().edit().putString(Constant.USERNAME, userName).commit();
                            ISOActivity.this.isLogin = true;
                        }
                    }, 1500);
                    dlg.cancel();
                }
                else {
                    ToolsToast.show(ISOActivity.this, "对不起，工号不正确");
                }
            }

            @Override
            public void onDialogCreate(Dialog dlg, Object param) {
            }

            @Override
            public void onDialogCancel(Dialog dlg, Object param) {
                dlg.cancel();
                finish();
            }
        }, "巡检员登陆", "", "取消", "确定");
    }

    /*
     * (non-Javadoc)
     * @see com.henghao.wenbo.ActivityFragmentSupport#initData()
     */
    @Override
    public void initData() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* 回调内容 */
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1000:
                /**
                 * 二维码返回
                 */
                String result = data.getStringExtra("result");
                if (result.equals("1")) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.INTNET_TYPE, 111);
                    //					intent.setClass(ISOActivity.this, ScanDetailActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    String convertByteArray(byte[] ids) {
        if (ids.length == 0) {
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                sb.append('-');
            }
            String s = Integer.toHexString(ids[i] & 0xff);
            if (s.length() == 1) {
                sb.append('0');
            }
            sb.append(s);
        }
        return sb.toString();
    }

    class SearchThread extends Thread {

        @Override
        public void run() {
            super.run();
            // while(!isInterrupted()) {
            while (ISOActivity.this.inSearch) {
                Message msg = new Message();
                // epcs = native_method.search_UHF();
                // epcs = search_UHF();
                ISOActivity.this.epcs = new ArrayList<String>();
                String[] arrTemp = search_UHF();
                if (null != arrTemp) {
                    for (int i = 0; i < arrTemp.length; i++) {
                        ISOActivity.this.epcs.add(arrTemp[i]);
                    }
                }
                if (!ToolsKit.isEmpty(ISOActivity.this.epcs)) {
                    for (int i = ISOActivity.this.epcs.size() - 1; i > -1; i--) {
                        if (ISOActivity.this.listIsoId.contains(ISOActivity.this.epcs.get(i))) {
                            ISOActivity.this.epcs.remove(i);
                        }
                        else {
                            ISOActivity.this.listIsoId.add(ISOActivity.this.epcs.get(i));
                        }
                    }
                    if (ISOActivity.this.epcs.size() > 0) {
                        msg.what = 1;
                        ISOActivity.this.handler.sendMessage(msg);
                    }
                    else {
                        msg.what = 0;
                        ISOActivity.this.handler.sendMessage(msg);
                    }
                }
                else {
                    msg.what = 0;
                    ISOActivity.this.handler.sendMessage(msg);
                }
                try {
                    sleep(200);
                }
                catch (InterruptedException e) {
                    this.interrupt();
                    //						Log.i("as3992",
                    //								"********************************************************");
                    //						Log.w("as3992", "test w color");
                    //						Log.v("as3992", "test v color");
                }
            }
            Log.d("as3992", "search thread is interrupted");
        }
    }

    int power_antenna(boolean power) {
        int retry = 0;
        while (this.native_method.set_antenna_power(power) != 0) {
            Log.e("as3992", "set antenna power " + power + " retry");
            if ((++retry) >= 5) {
                return -1;
            }
        }
        return 0;
    }

    class EpcDataBase {

        String epc;

        int valid;

        public EpcDataBase(String e, int v) {
            this.epc = e;
            this.valid = v;
        }

        @Override
        public String toString() {
            return this.epc;
        }
    }

    String[] search_UHF() {
        List<as3992_native.Tag_Data_Rssi> cnt;
        int retry = 0;
        do {
            //			Log.e("as3992", "search retry");
            cnt = this.native_method.search_card_rssi();
            if ((++retry) >= 10) {
                return null;
            }
        }
        while (cnt == null);
        int noc = cnt.size();
        String[] out = new String[noc];
        for (int i = 0; i < noc; i++) {
            out[i] = new String();
            int loe = cnt.get(i).tdata.epc.length;
            for (int j = 0; j < loe; j++) {
                out[i] += String.format("%02x ", cnt.get(i).tdata.epc[j]);
            }
        }
        return out;
    }

    class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                ISOActivity.this.native_method.read_thread();
            }
        }
    }

    int set_sensitivity(int sens) {
        int retry = 0;
        Log.i("as3992_spec", "" + sens);
        // while(set_sens(sens) != 0)
        while (this.native_method.set_gen2(5, (byte) sens) != 0) {
            Log.e("as9932", "setsens retry");
            if ((++retry) >= 5) {
                return -1;
            }
        }
        return 0;
    }

    int set_hopping_freq(int start, int stop, int increment, int rssi)// , int
    // id)
    // //in
    // my
    // gui
    // program
    // don't
    // use
    // profile
    // id.
    // Just
    // set
    // it to
    // zero.
    {
        int retry = 0, reval;
        while ((reval = this.native_method.set_freq(start, stop, increment, rssi, 0)) != 0) {
            if (reval == -2) {
                return -2;
            }
            Log.e("as9932", "setfreq retry");
            if ((++retry) >= 5) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public void onDestroy() {
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        super.onDestroy();
        this.mMapView.onDestroy();
        try {
            if (this.inSearch) {
                this.inSearch = false;
            }
            this.soundPool.release();
        }
        catch (Exception e) {
        }
        /*
         * switch (init_progress) { case 3: wK.release(); case 2: rthread.interrupt(); try { DevCtrl.PowerOffDevice();
         * Log.i("as3992_destroy", "write poweroff cmd to file"); } catch (IOException e) { e.printStackTrace(); } case
         * 1: native_method.CloseComPort(); case 0: default: init_progress = 0; }
         */
        try {
            this.native_method.CloseComPort();
            this.rthread.interrupt();
            try {
                this.DevCtrl.PowerOffDevice();
                Log.i("as3992_destroy", "write poweroff cmd to file");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this.st.interrupt();
            this.init_progress = 0;
        }
        catch (Exception e) {
        }
        /*
         * Intent i = getIntent(); setResult(RESULT_OK, i);
         */
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        if (this.mList == null || ToolsKit.isEmpty(this.mList)) {
            return;
        }
        resolveIntent(intent);
    }

    void resolveIntent(Intent intent) {
        // 分析intent
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            if (this.ready == false) {
                return;
            }
            // 提示音
            MediaPlayer.create(this, R.raw.discovered_tag_notification).start();
            Bundle b = intent.getExtras();
            Tag t = (Tag) b.get(NfcAdapter.EXTRA_TAG);
            byte[] idarr = t.getId();// bc-55-9f-7a-00-01-04-e0
            final String idString = convertByteArray(idarr);
            this.nfcId = idString;
            this.ready = false;
            System.out.println("idString:::::::" + idString);
            this.ready = true;
            // 触发执行
            // EventBus.getDefault().post("谁看到我了就执行");
            // 获取到数据啦
            //						locMarkerAdd(ISOActivity.this.mList.get(i));
            //						// 处理数据
            //						UpdateVIew(ISOActivity.this.mList.get(i));
            // 获取数据
            this.mNfcDetailsProtocol.getNfcById(idString);
            this.mActivityFragmentView.viewLoading(View.VISIBLE);
        }
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        // TODO Auto-generated method stub
        super.OnMessageResponse(url, jo, status);
        this.mActivityFragmentView.viewLoading(View.GONE);
        if (url.endsWith(ProtocolUrl.APP_GET_NFCBYID)) {
            if (jo == null) {
                return;
            }
            String string = (String) jo;
            if (string.contains("不存在")) {
                ToolsToast.show(this, string);
                return;
            }
            Intent intent = new Intent();
            intent.setClass(this, CommonWebActivity.class);
            intent.putExtra(Constant.INTNET_URL, string);
            intent.putExtra(Constant.INTNET_TITLE, "详情");
            startActivity(intent);
            if (this.idStrings.contains(this.nfcId)) {
                // 说明之前扫描过
                return;
            }
            else {
                for (int j = 0; j < ISOActivity.this.mList.size(); j++) {
                    if (this.mList.get(j).getNfcId().equals(this.nfcId)
                            || this.mList.get(j).getNfcNo().equals(this.nfcId)) {
                        if (this.mList.get(j).isCheck()) {
                            return;
                        }
                        this.mList.get(j).setCheck(true);
                        locMarkerAdd(ISOActivity.this.mList.get(j));
                        // 处理数据
                        UpdateVIew(ISOActivity.this.mList.get(j));
                    }
                }
            }
            this.idStrings.add(this.nfcId);
        }
    }
}
