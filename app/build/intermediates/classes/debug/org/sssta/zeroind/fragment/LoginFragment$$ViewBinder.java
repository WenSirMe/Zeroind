// Generated code from Butter Knife. Do not modify!
package org.sssta.zeroind.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginFragment$$ViewBinder<T extends org.sssta.zeroind.fragment.LoginFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558562, "field 'editUserName'");
    target.editUserName = finder.castView(view, 2131558562, "field 'editUserName'");
    view = finder.findRequiredView(source, 2131558563, "field 'userNameImage'");
    target.userNameImage = finder.castView(view, 2131558563, "field 'userNameImage'");
    view = finder.findRequiredView(source, 2131558564, "field 'editPassword'");
    target.editPassword = finder.castView(view, 2131558564, "field 'editPassword'");
    view = finder.findRequiredView(source, 2131558565, "field 'passwordImage'");
    target.passwordImage = finder.castView(view, 2131558565, "field 'passwordImage'");
  }

  @Override public void unbind(T target) {
    target.editUserName = null;
    target.userNameImage = null;
    target.editPassword = null;
    target.passwordImage = null;
  }
}
