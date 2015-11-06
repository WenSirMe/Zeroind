// Generated code from Butter Knife. Do not modify!
package org.sssta.zeroind.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends org.sssta.zeroind.activity.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558521, "field 'mSlidingTablayout'");
    target.mSlidingTablayout = finder.castView(view, 2131558521, "field 'mSlidingTablayout'");
    view = finder.findRequiredView(source, 2131558522, "field 'mViewPager'");
    target.mViewPager = finder.castView(view, 2131558522, "field 'mViewPager'");
    view = finder.findRequiredView(source, 2131558523, "field 'commitLG'");
    target.commitLG = finder.castView(view, 2131558523, "field 'commitLG'");
    view = finder.findRequiredView(source, 2131558520, "field 'mUserImage'");
    target.mUserImage = finder.castView(view, 2131558520, "field 'mUserImage'");
  }

  @Override public void unbind(T target) {
    target.mSlidingTablayout = null;
    target.mViewPager = null;
    target.commitLG = null;
    target.mUserImage = null;
  }
}
