package com.henghao.parkland.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsDate;
import com.benefit.buy.library.views.xlistview.XListView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ProjectMoneyAdapter;
import com.henghao.parkland.model.entity.SGWalletEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.henghao.parkland.utils.ExcelUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 施工钱包
 */
public class ProjectMoneyActivity extends ActivityFragmentSupport implements XListView.IXListViewListener {

    private String[] title = {"日期", "支出类型", "金额"};

    @ViewInject(R.id.listview)
    private XListView mXlistview;

    private TextView tv_total_money;

    private ProjectMoneyAdapter mMoneyAdapter;

    private List<SGWalletEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_money);
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
        initWithRightBar();
        mRightTextView.setText("导出EXCEL");
        mRightTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("施工钱包");
        mXlistview.setXListViewListener(this);
        initView();
        mMoneyAdapter = new ProjectMoneyAdapter(this);

        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(getSDPath() + "/ParKLand");
                        makeDir(file);
                        ExcelUtils.initExcel(file.toString() + ToolsDate.crunttime(), title);
                        final ArrayList<ArrayList<String>> mArray = new ArrayList<ArrayList<String>>();
                        ArrayList<String> dataList = new ArrayList<String>();
                        for (int i=0;i<mList.size();i++){
                            dataList.add(mList.get(i).getTransactionTime());
                            dataList.add(mList.get(i).getExpend()+"");
                            dataList.add(mList.get(i).getMoney()+"");
                            mArray.add(dataList);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ExcelUtils.writeObjListToExcel(mArray, getSDPath()
                                        + "/ParKLand/bill.xls", ProjectMoneyActivity.this);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void initView() {
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_poject_money, mXlistview, false);
        TextView tv_title = (TextView) HeaderView.findViewById(R.id.tv_title);
        tv_total_money = (TextView) HeaderView.findViewById(R.id.tv_total_money);
        tv_title.setText("施工钱包");
        mXlistview.addHeaderView(HeaderView);
        /**
         * 访问网络数据
         */
        ProjectProtocol mProjectProtocol = new ProjectProtocol(this);
        mProjectProtocol.addResponseListener(this);
        SharedPreferences preferences = getLoginUserSharedPre();
        String UID = preferences.getString(Constant.USERID, null);
        mProjectProtocol.querySGWallet(UID);
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SGWALLET)) {
            if (jo instanceof List) {
                List<SGWalletEntity> data = (List<SGWalletEntity>) jo;
                mList.addAll(data);
                mMoneyAdapter.setData(data);
                mXlistview.setAdapter(mMoneyAdapter);
                mMoneyAdapter.notifyDataSetChanged();
                SGWalletEntity entity = data.get(data.size() - 1);
                final double money = entity.getMoney();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_total_money.setText("总金额：" + money + "元");
                    }
                });
            }
        }
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;

    }


}
