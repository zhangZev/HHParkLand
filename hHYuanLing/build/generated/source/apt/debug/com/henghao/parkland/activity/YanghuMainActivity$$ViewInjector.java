// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class YanghuMainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.YanghuMainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624212, "field 'sp_State'");
    target.sp_State = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131624215, "field 'btnYhmanage' and method 'onClick'");
    target.btnYhmanage = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624214, "field 'btnTreeYh' and method 'onClick'");
    target.btnTreeYh = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624213, "field 'btnTreemessage' and method 'onClick'");
    target.btnTreemessage = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.YanghuMainActivity target) {
    target.sp_State = null;
    target.btnYhmanage = null;
    target.btnTreeYh = null;
    target.btnTreemessage = null;
  }
}
