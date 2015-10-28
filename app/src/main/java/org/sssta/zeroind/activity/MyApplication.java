package org.sssta.zeroind.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by 林韬 on 2015/10/15.
 */
public class MyApplication extends Application {
    private Context mContext;
    private static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        myApplication = this;
    }
    public Context getmContext() {
        return mContext;
    }


    public static MyApplication getInstance() {
        if (myApplication==null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }
}
