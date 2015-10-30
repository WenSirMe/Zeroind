// Generated code from Butter Knife. Do not modify!
package org.sssta.zeroind.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserSetting$$ViewBinder<T extends org.sssta.zeroind.activity.UserSetting> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492993, "field 'btextPasswordSetting'");
    target.btextPasswordSetting = finder.castView(view, 2131492993, "field 'btextPasswordSetting'");
    view = finder.findRequiredView(source, 2131492994, "field 'passwordLinear'");
    target.passwordLinear = finder.castView(view, 2131492994, "field 'passwordLinear'");
    view = finder.findRequiredView(source, 2131492995, "field 'originalPassword'");
    target.originalPassword = finder.castView(view, 2131492995, "field 'originalPassword'");
    view = finder.findRequiredView(source, 2131492996, "field 'newPassword'");
    target.newPassword = finder.castView(view, 2131492996, "field 'newPassword'");
    view = finder.findRequiredView(source, 2131492989, "field 'settingImage'");
    target.settingImage = finder.castView(view, 2131492989, "field 'settingImage'");
    view = finder.findRequiredView(source, 2131492990, "field 'settingUsername'");
    target.settingUsername = finder.castView(view, 2131492990, "field 'settingUsername'");
    view = finder.findRequiredView(source, 2131492992, "field 'settingDirection'");
    target.settingDirection = finder.castView(view, 2131492992, "field 'settingDirection'");
    view = finder.findRequiredView(source, 2131492991, "field 'settingSignature'");
    target.settingSignature = finder.castView(view, 2131492991, "field 'settingSignature'");
    view = finder.findRequiredView(source, 2131492997, "field 'buttonCommit'");
    target.buttonCommit = finder.castView(view, 2131492997, "field 'buttonCommit'");
  }

  @Override public void unbind(T target) {
    target.btextPasswordSetting = null;
    target.passwordLinear = null;
    target.originalPassword = null;
    target.newPassword = null;
    target.settingImage = null;
    target.settingUsername = null;
    target.settingDirection = null;
    target.settingSignature = null;
    target.buttonCommit = null;
  }
}
