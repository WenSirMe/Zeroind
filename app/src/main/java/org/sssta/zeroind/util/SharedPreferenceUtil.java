package org.sssta.zeroind.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.sssta.zeroind.Constants;
import org.sssta.zeroind.activity.MyApplication;
import org.sssta.zeroind.model.User;

/**
 * Created by 林韬 on 2015/10/15.
 */
public class SharedPreferenceUtil {

    private static SharedPreferenceUtil mSharedPreferenceUtil;

    private SharedPreferences mSharePreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private String img_id;

    public SharedPreferenceUtil() {
        mContext = MyApplication.getInstance().getmContext();
        mSharePreferences = mContext.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        mEditor = mSharePreferences.edit();
    }

    public static SharedPreferenceUtil getInstance() {
        if (mSharedPreferenceUtil == null) {
            mSharedPreferenceUtil = new SharedPreferenceUtil();
        }
        return mSharedPreferenceUtil;
    }

    public void setPassword(String password) {
        mEditor.putString(Constants.SP_PASSWORD,password);
        mEditor.commit();
    }

    public void setUserName(String userName) {
        mEditor.putString(Constants.SP_USERNAEM,userName);
        mEditor.commit();
    }



    public String getUserName() {
        return mSharePreferences.getString(Constants.SP_USERNAEM,null);
    }

    public String getPassword() {
        return mSharePreferences.getString(Constants.SP_PASSWORD,null);
    }

    public String getSex() {
        return mSharePreferences.getString(Constants.SP_SEX,null);
    }

    public int getUserLevel() {
        return mSharePreferences.getInt(Constants.SP_LEVEL, 0);
    }

    public int getDirection() {
        return mSharePreferences.getInt(Constants.SP_DIRECTION,0);
    }

    public int getExp() {
        return mSharePreferences.getInt(Constants.SP_EXP,0);
    }

    public String getImg_id() {
        return mSharePreferences.getString(Constants.SP_IMG_ID, null);
    }

    public void setUser(User user) {
        mEditor.putString(Constants.SP_USERNAEM,user.getUserName());
        mEditor.putString(Constants.SP_PASSWORD,user.getPassword());
        mEditor.putString(Constants.SP_SEX,user.getSex());
        mEditor.putInt(Constants.SP_LEVEL, user.getLevel());
        mEditor.putInt(Constants.SP_DIRECTION,user.getDirection());
        mEditor.putInt(Constants.SP_EXP,user.getExp());
        mEditor.putString(Constants.SP_IMG_ID,user.getImg_id());
        mEditor.commit();
    }

    public void setSignature(String signature) {
        mEditor.putString(Constants.SP_SIGNATURE,signature);
        mEditor.commit();
    }

    public void setToken(String token) {
        mEditor.putString(Constants.SP_TOKEN,token);
        mEditor.commit();
    }

    public String getToken() {
       return mSharePreferences.getString(Constants.SP_TOKEN,null);
    }

    public void setImageId(String img_id) {
        mEditor.putString(Constants.SP_IMG_ID,img_id);
        mEditor.commit();
    }


    public String getSignature() {
        return mSharePreferences.getString(Constants.SP_SIGNATURE,"签名");
    }

}
