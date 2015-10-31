package org.sssta.zeroind.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.sssta.zeroind.fragment.AboutUsFragment;

/**
 * Created by Heaven on 2015/11/1.
 */
public class AboutUsPagerAdapter extends FragmentPagerAdapter implements ViewGroup.OnClickListener {
    public AboutUsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public long getItemId(int position) {


        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int position) {
        return AboutUsFragment.newInstance(String.valueOf(position),"");
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void onClick(View v) {

    }
}
