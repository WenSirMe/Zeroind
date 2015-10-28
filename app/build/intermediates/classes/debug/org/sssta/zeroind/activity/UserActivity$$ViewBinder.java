// Generated code from Butter Knife. Do not modify!
package org.sssta.zeroind.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserActivity$$ViewBinder<T extends org.sssta.zeroind.activity.UserActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492973, "field 'infoUserImage'");
    target.infoUserImage = finder.castView(view, 2131492973, "field 'infoUserImage'");
    view = finder.findRequiredView(source, 2131492976, "field 'infoUserName'");
    target.infoUserName = finder.castView(view, 2131492976, "field 'infoUserName'");
    view = finder.findRequiredView(source, 2131492978, "field 'infoUserLevel'");
    target.infoUserLevel = finder.castView(view, 2131492978, "field 'infoUserLevel'");
    view = finder.findRequiredView(source, 2131492977, "field 'sexImage'");
    target.sexImage = finder.castView(view, 2131492977, "field 'sexImage'");
    view = finder.findRequiredView(source, 2131492981, "field 'infoUserSignature'");
    target.infoUserSignature = finder.castView(view, 2131492981, "field 'infoUserSignature'");
    view = finder.findRequiredView(source, 2131492972, "field 'btBack'");
    target.btBack = finder.castView(view, 2131492972, "field 'btBack'");
    view = finder.findRequiredView(source, 2131492974, "field 'btSetting'");
    target.btSetting = finder.castView(view, 2131492974, "field 'btSetting'");
    view = finder.findRequiredView(source, 2131492982, "field 'btMailbox'");
    target.btMailbox = finder.castView(view, 2131492982, "field 'btMailbox'");
    view = finder.findRequiredView(source, 2131492983, "field 'btMessage'");
    target.btMessage = finder.castView(view, 2131492983, "field 'btMessage'");
    view = finder.findRequiredView(source, 2131492984, "field 'btWind'");
    target.btWind = finder.castView(view, 2131492984, "field 'btWind'");
  }

  @Override public void unbind(T target) {
    target.infoUserImage = null;
    target.infoUserName = null;
    target.infoUserLevel = null;
    target.sexImage = null;
    target.infoUserSignature = null;
    target.btBack = null;
    target.btSetting = null;
    target.btMailbox = null;
    target.btMessage = null;
    target.btWind = null;
  }
}
