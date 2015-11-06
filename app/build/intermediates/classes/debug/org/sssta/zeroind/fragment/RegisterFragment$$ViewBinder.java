// Generated code from Butter Knife. Do not modify!
package org.sssta.zeroind.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterFragment$$ViewBinder<T extends org.sssta.zeroind.fragment.RegisterFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558567, "field 'editUsername'");
    target.editUsername = finder.castView(view, 2131558567, "field 'editUsername'");
    view = finder.findRequiredView(source, 2131558568, "field 'spinnerSex'");
    target.spinnerSex = finder.castView(view, 2131558568, "field 'spinnerSex'");
    view = finder.findRequiredView(source, 2131558569, "field 'editPassword'");
    target.editPassword = finder.castView(view, 2131558569, "field 'editPassword'");
  }

  @Override public void unbind(T target) {
    target.editUsername = null;
    target.spinnerSex = null;
    target.editPassword = null;
  }
}
