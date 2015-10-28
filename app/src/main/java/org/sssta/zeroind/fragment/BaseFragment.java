package org.sssta.zeroind.fragment;

import android.content.Context;

import org.sssta.zeroind.activity.MainActivity;

import java.lang.reflect.Field;

/**
 * Created by 林韬 on 2015/10/5.
 */
public class BaseFragment extends android.support.v4.app.Fragment{

    public BaseFragment() {
        super();
    }

    public static BaseFragment newInstance(int index,Context context) {
        switch (index) {
            case MainActivity.INDEX_FRAGMENT_PLANERECERIVE:
                return PlaneReceive.newInstance(context);
            case MainActivity.INDEX_FRAGMENT_PLANESEND:
                return PlaneSend.newInstance(context);
            case MainActivity.INDEX_FRAGMENT_REPORT:
                return ReportToUs.newInstance(context);
            case MainActivity.INDEX_FRAGMENT_ABOUT:
                return AboutUs.newInstance(context);
            default:
                return null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = android.support.v4.app.Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
