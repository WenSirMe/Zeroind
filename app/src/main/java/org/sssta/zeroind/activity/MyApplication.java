package org.sssta.zeroind.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by 林韬 on 2015/10/15.
 */
public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext() {
        return mContext;
    }
}
