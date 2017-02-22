package com.henghao.parkland.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.henghao.parkland.R;
import com.henghao.parkland.adapter.AppGridAdapter;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 应用〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppFragment extends FragmentSupport {

    @ViewInject(R.id.gridview)
    private GridView gridview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.common_gridview);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
        List<AppGridEntity> mList = new ArrayList<AppGridEntity>();
        //第一个
        AppGridEntity mEntity = new AppGridEntity();
        mEntity.setImageId(R.drawable.app_one);
        mEntity.setName("苗木管理");
        mList.add(mEntity);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.app_two);
        mEntity2.setName("养护管理");
        mList.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.drawable.app_three);
        mEntity3.setName("招投标");
        mList.add(mEntity3);
        //第四个
        AppGridEntity mEntity4 = new AppGridEntity();
        mEntity4.setImageId(R.drawable.app_one);
        mEntity4.setName("园林协会");
        mList.add(mEntity4);
        //第五个
        AppGridEntity mEntity5 = new AppGridEntity();
        mEntity5.setImageId(R.drawable.app_five);
        mEntity5.setName("资质申请");
        mList.add(mEntity5);
        //第六个
        AppGridEntity mEntity6 = new AppGridEntity();
        mEntity6.setImageId(R.drawable.app_six);
        mEntity6.setName("备用金申请");
        mList.add(mEntity6);
        //第七个
        AppGridEntity mEntity7 = new AppGridEntity();
        mEntity7.setImageId(R.drawable.app_seven);
        mEntity7.setName("园林资讯");
        mList.add(mEntity7);
        AppGridAdapter maAdapter = new AppGridAdapter(this.mActivity, mList);
        this.gridview.setAdapter(maAdapter);
        maAdapter.notifyDataSetChanged();
    }

    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void initwithContent() {
        // TODO Auto-generated method stub
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
    }

    public void initWidget() {
        initwithContent();
    }
}
