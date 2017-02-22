package com.henghao.parkland.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.henghao.parkland.R;
import com.henghao.parkland.adapter.MyGridAdapter;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的〈一句话功能简述〉 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyFragment extends FragmentSupport {

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
        mEntity.setImageId(R.drawable.my_one);
        mEntity.setName("签到");
        mList.add(mEntity);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.my_two);
        mEntity2.setName("打卡");
        mList.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.drawable.my_three);
        mEntity3.setName("审批");
        mList.add(mEntity3);
        //第四个
        AppGridEntity mEntity4 = new AppGridEntity();
        mEntity4.setImageId(R.drawable.my_one);
        mEntity4.setName("日志");
        mList.add(mEntity4);
        //第五个
        AppGridEntity mEntity5 = new AppGridEntity();
        mEntity5.setImageId(R.drawable.my_five);
        mEntity5.setName("合同管理");
        mList.add(mEntity5);
        //第六个
       /* AppGridEntity mEntity6 = new AppGridEntity();
        mEntity6.setImageId(R.drawable.my_six);
        mEntity6.setName("项目管理");
        mList.add(mEntity6);*/
        //第七个
        AppGridEntity mEntity7 = new AppGridEntity();
        mEntity7.setImageId(R.drawable.my_seven);
        mEntity7.setName("交易记录");
        mList.add(mEntity7);
        //第八个
        AppGridEntity mEntity8 = new AppGridEntity();
        mEntity8.setImageId(R.drawable.my_eight);
        mEntity8.setName("工作痕迹");
        mList.add(mEntity8);
        //第九个
        AppGridEntity mEntity9 = new AppGridEntity();
        mEntity9.setImageId(R.drawable.my_nine);
        mEntity9.setName("开工报告");
        mList.add(mEntity9);
        //第10个
        AppGridEntity mEntity10 = new AppGridEntity();
        mEntity10.setImageId(R.drawable.my_ten);
        mEntity10.setName("技术交底");
        mList.add(mEntity10);
        //第11个
        AppGridEntity mEntity11 = new AppGridEntity();
        mEntity11.setImageId(R.drawable.my_eleven);
        mEntity11.setName("抽查记录");
        mList.add(mEntity11);
        //第12个
        AppGridEntity mEntity12 = new AppGridEntity();
        mEntity12.setImageId(R.drawable.my_twelve);
        mEntity12.setName("设备备案");
        mList.add(mEntity12);
        //第13个
        AppGridEntity mEntity13 = new AppGridEntity();
        mEntity13.setImageId(R.drawable.my_thirty);
        mEntity13.setName("工序报验");
        mList.add(mEntity13);
        //第12个
        AppGridEntity mEntity14 = new AppGridEntity();
        mEntity14.setImageId(R.drawable.my_fourtity);
        mEntity14.setName("竣工验收");
        mList.add(mEntity14);
        MyGridAdapter maAdapter = new MyGridAdapter(this.mActivity, mList);
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
