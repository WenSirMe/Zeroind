package org.sssta.zeroind.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.sssta.zeroind.R;
import org.sssta.zeroind.adapter.AboutUsPagerAdapter;
import org.sssta.zeroind.fragment.AboutUsFragment;
import org.sssta.zeroind.ui.ScrollTabView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends AppCompatActivity implements AboutUsFragment.OnFragmentInteractionListener {

    @Bind(R.id.scroll_tab)
    ScrollTabView scrollTab;
    @Bind(R.id.about_viewpager)
    ViewPager aboutViewpager;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scrollTab.setmTabNum(4);
        aboutViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scrollTab.setPosition(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        aboutViewpager.setAdapter(new AboutUsPagerAdapter(getSupportFragmentManager()));
        //aboutViewpagerTab.setBackgroundResource(R.color.colorAccent);
    }

}
