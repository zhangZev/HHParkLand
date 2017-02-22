package com.henghao.parkland.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.views.xlistview.XListView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonListStringAdapter;
import com.henghao.parkland.adapter.ProjectAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.utils.PopupWindowHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目管理
 *
 * @author zhangxianwen
 */
@SuppressLint("NewApi")
public class ProjectActivity extends ActivityFragmentSupport implements XListView.IXListViewListener {

    @ViewInject(R.id.listview)
    private XListView mXlistView;
    private PopupWindowHelper popupWindowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.common_xlistview);
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
        mXlistView.setPullLoadEnable(false);
        mXlistView.setPullRefreshEnable(false);
        mXlistView.setXListViewListener(this);

    }

    @Override
    public void initData() {
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("项目管理");
        initWithRightBar();
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText("管理");
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowHelper.showFromBottom(mRightLinearLayout);
            }
        });
        List<String> mlist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mlist.add("测试项目管理");
        }
        ProjectAdapter mAdapter = new ProjectAdapter(this, mlist);
        mXlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        popWindowget();
    }

    private void popWindowget() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popView = inflater.inflate(R.layout.common_androidlistview, null);
        ListView mListView = (ListView) popView.findViewById(R.id.mlistview);
        final List<String> mList = new ArrayList<String>();
        mList.add("工作备忘");
        mList.add("施工备忘");
        mList.add("施工资料");
        mList.add("项目图纸");
        CommonListStringAdapter mListStringAdapter = new CommonListStringAdapter(this, mList);
        mListView.setAdapter(mListStringAdapter);
        mListStringAdapter.notifyDataSetChanged();
        popupWindowHelper = new PopupWindowHelper(popView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String whatSelect = mList.get(arg2);
                Intent intent = new Intent();
                switch (arg2) {
                    case 0:
                        //工作备忘
                        intent.setClass(ProjectActivity.this, ProjectWorkActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //施工备忘
                        intent.setClass(ProjectActivity.this, ProjectWorkActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //施工资料
                        intent.setClass(ProjectActivity.this, ProjectSGMaterialsActivity.class);
                        startActivity(intent);
                        break;
                }
                popupWindowHelper.dismiss();
            }
        });

    }


    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.APP_LOGIN)) {
            if (jo instanceof BaseEntity) {
                BaseEntity base = (BaseEntity) jo;
                msg(base.getMsg());
                return;
            }

        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
