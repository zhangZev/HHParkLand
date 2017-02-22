// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class YhManageActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.YhManageActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624063, "field 'listView'");
    target.listView = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131624062, "field 'tvState'");
    target.tvState = (android.widget.TextView) view;
  }

  public static void reset(com.henghao.parkland.activity.YhManageActivity target) {
    target.listView = null;
    target.tvState = null;
  }
}
