// Generated code from Butter Knife. Do not modify!
package com.henghao.parkland.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TreeMessageActivity$$ViewInjector {
  public static void inject(Finder finder, final com.henghao.parkland.activity.TreeMessageActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624201, "field 'tvTreeId'");
    target.tvTreeId = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624202, "field 'etTreeName'");
    target.etTreeName = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624203, "field 'etTreeUse'");
    target.etTreeUse = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624204, "field 'etTreeSpecification'");
    target.etTreeSpecification = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624205, "field 'tvTreeSite'");
    target.tvTreeSite = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624206, "field 'tvTreeTime'");
    target.tvTreeTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131624207, "field 'btnSubmit' and method 'onClick'");
    target.btnSubmit = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624208, "field 'btnCancel' and method 'onClick'");
    target.btnCancel = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.henghao.parkland.activity.TreeMessageActivity target) {
    target.tvTreeId = null;
    target.etTreeName = null;
    target.etTreeUse = null;
    target.etTreeSpecification = null;
    target.tvTreeSite = null;
    target.tvTreeTime = null;
    target.btnSubmit = null;
    target.btnCancel = null;
  }
}
