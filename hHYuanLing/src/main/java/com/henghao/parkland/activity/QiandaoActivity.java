package com.henghao.parkland.activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 签到界面 〈一句话功能简述〉 〈功能详细描述〉
 * @author yanqiyun
 * @version HDMNV100R001, 2016-12-01
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QiandaoActivity extends ActivityFragmentSupport {

	private static int QIANDAO_REQUEST = 100;

	private int count = 0;// 签到次数

	// 定位相关声明
	public LocationClient locationClient = null;
	// 自定义图标
	BitmapDescriptor mCurrentMarker = null;
	boolean isFirstLoc = true;// 是否首次定位

	/**
	 * 签到的时间（年月日）
	 */
	@ViewInject(R.id.tv_time_qiandao)
	private TextView tv_time_qiandao;
	/**
	 * 当前企业
	 */
	@ViewInject(R.id.tv_company_qiandao)
	private TextView tv_company_qiandao;
	/**
	 * 签到的地点
	 */
	@ViewInject(R.id.tv_place_qiandao)
	private TextView tv_place_qiandao;
	/**
	 * 签到的具体时间（时分）
	 */
	@ViewInject(R.id.tv_hourminute_qiandao)
	private TextView tv_hourminute_qiandao;
	/**
	 * 签到的状态（当前已签到或未签到）
	 */
	@ViewInject(R.id.tv_state_qiandao)
	private TextView tv_state_qiandao;
	/**
	 * 签到
	 */
	@ViewInject(R.id.img_qiandao)
	private ImageView img_qiandao;
	/**
	 * 签到打勾
	 */
	@ViewInject(R.id.img_confirm_qiandao)
	private ImageView img_confirm_qiandao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		this.mActivityFragmentView.viewMain(R.layout.activity_qiandao);
		this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
		this.mActivityFragmentView.viewEmptyGone();
		this.mActivityFragmentView.viewLoading(View.GONE);
		this.mActivityFragmentView.clipToPadding(true);
		setContentView(this.mActivityFragmentView);
		com.lidroid.xutils.ViewUtils.inject(this);
		initWidget();
		initData();
	}

	@OnClick({
		R.id.img_qiandao
	})
	private void viewClick(View v) {
		switch (v.getId()) {
		// 点击签到
			case R.id.img_qiandao:
				String time = this.tv_hourminute_qiandao.getText().toString();// 签到时间
				String address = this.tv_place_qiandao.getText().toString(); // 签到地址
				String company = this.tv_company_qiandao.getText().toString();// 当前企业
				if (address.equals("没有定位信息！")) {
					Toast.makeText(QiandaoActivity.this, "当前没有定位，请定位后再签到！", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(QiandaoActivity.this, QiandaoSubmitActivity.class);
				intent.putExtra("time", time);
				intent.putExtra("address", address);
				intent.putExtra("company", company);
				startActivityForResult(intent, QIANDAO_REQUEST);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == QIANDAO_REQUEST && resultCode == RESULT_OK) {
			this.count++;
			this.img_confirm_qiandao.setVisibility(View.VISIBLE);
			this.tv_state_qiandao.setText("今日你已签到" + this.count + "次");
		}
	}

	/**
	 * 判断GPS是否开启
	 * @param context
	 * @return true 表示开启
	 */
	private boolean isOPen(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return gps;
	}

	/**
	 * 检测网络是否连接
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
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			String addrStr = location.getAddrStr();
			System.out.println("经度：" + longitude + "，纬度：" + latitude);
			/**
			 * 如果GPS未打开且无网络
			 */
			if (!checkNetworkState()) {
				QiandaoActivity.this.tv_place_qiandao.setText("没有定位信息！");
				addrStr = null;
				// Toast.makeText(QiandaoActivity.this,
				// "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
			}
			if (ToolsKit.isEmpty(addrStr)) {
				QiandaoActivity.this.tv_place_qiandao.setText("没有定位信息！");
				QiandaoActivity.this.img_qiandao.setImageResource(R.drawable.icon_grayciecle);
				QiandaoActivity.this.img_qiandao.setClickable(false);
				return;
			}
			QiandaoActivity.this.img_qiandao.setClickable(true);
			QiandaoActivity.this.img_qiandao.setImageResource(R.drawable.icon_orangecircle);
			QiandaoActivity.this.tv_place_qiandao.setText(addrStr);

		}
	};

	/*
	 * (non-Javadoc)
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initWidget()
	 */
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		initWithBar();
		this.mLeftTextView.setVisibility(View.VISIBLE);
		this.mLeftTextView.setText("返回");
		initWithCenterBar();
		this.mCenterTextView.setVisibility(View.VISIBLE);
		this.mCenterTextView.setText("签到");
	}

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

	@Override
	public void onResume() {
		super.onResume();
		this.locationClient.start(); // 开始定位
	}

	/*
	 * (non-Javadoc)
	 * @see com.henghao.wenbo.ActivityFragmentSupport#initData()
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		/**
		 * 定位
		 */
		this.locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
		this.locationClient.registerLocationListener(this.myListener); // 注册监听函数
		this.setLocationOption(); // 设置定位参数
		this.locationClient.start(); // 开始定位
		/**
		 * 设置签到时间（年月日）
		 */
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String time = dateFormat.format(date);
		this.tv_time_qiandao.setText(time);
		/**
		 * 设置签到具体时间（时、分）
		 */
		dateFormat = new SimpleDateFormat("HH:mm");
		time = dateFormat.format(date);
		this.tv_hourminute_qiandao.setText(time);
	}

	// 三个状态实现地图生命周期管理
	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		this.locationClient.stop();
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.locationClient.stop();
	}
}
