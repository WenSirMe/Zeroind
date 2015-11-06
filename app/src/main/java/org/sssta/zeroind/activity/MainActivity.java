package org.sssta.zeroind.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import org.sssta.zeroind.Constants;
import org.sssta.zeroind.R;
import org.sssta.zeroind.fragment.BaseFragment;
import org.sssta.zeroind.fragment.PlaneReceive;
import org.sssta.zeroind.ui.CircleImageView;
import org.sssta.zeroind.util.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements PlaneReceive.OnUserReplyListener,View.OnClickListener {

    Context mContext;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    private int currentIndex = 0;
    public static final int INDEX_FRAGMENT_PLANERECERIVE = 0;
    public static final int INDEX_FRAGMENT_PLANESEND = 1;
    public static final int INDEX_FRAGMENT_REPORT = 2;
    public static final int INDEX_FRAGMENT_ABOUT = 3;

    private boolean lgActivity = false;
    private BaseFragment[] mBaseFragment = new BaseFragment[4];
    private MenuItem selectMenuItem;

    private TextView userName;
    private TextView userSign;
    private CircleImageView userImage;

    private final int TAG = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mContext = this;
        SharedPreferenceUtil.getInstance();
        userName.setText(SharedPreferenceUtil.getInstance().getUserName());
        initFragment();
    }

    void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_left);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);

        View navigationHeader = mNavigationView.inflateHeaderView(R.layout.navigation_header);
        userImage = (CircleImageView) navigationHeader.findViewById(R.id.navigation_user_image);
        userName = (TextView) navigationHeader.findViewById(R.id.navigation_userName);
        userSign = (TextView) navigationHeader.findViewById(R.id.navigation_userSign);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserActivity.class);
                startActivityForResult(intent, TAG);
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        selectMenuItem = mNavigationView.getMenu().getItem(0);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectMenu(menuItem);
                        return false;
                    }
                }
        );
        initToolbar();
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                requestIfHasLetter();
                refreshDrawer();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initFragment() {

        mBaseFragment[INDEX_FRAGMENT_PLANERECERIVE] = BaseFragment.newInstance(INDEX_FRAGMENT_PLANERECERIVE,mContext);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,mBaseFragment[INDEX_FRAGMENT_PLANERECERIVE])
                .commit();
        currentIndex = INDEX_FRAGMENT_PLANERECERIVE;

        toolbar.setTitle("风零");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent intent;
        switch (item.getItemId()) {
            case R.id.button_receive:
                toolbar.setTitle("风零");
                intent = new Intent(this,MessageList.class);
                startActivity(intent);
                return true;
            case R.id.button_send:
                toolbar.setTitle("风零");
                intent = new Intent(this,PostMessage.class);
                startActivity(intent);
                return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAG) {
            refreshDrawer();

        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
//            actionBar.setHomeAsUpIndicator(R.drawable.anim);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onUserReplay(Uri uri) {

    }

    public void showFragment(int index) {
        if (currentIndex==index) {
            return;
        }
        if (mBaseFragment[index]==null) {
            mBaseFragment[index] = BaseFragment.newInstance(index,mContext);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,mBaseFragment[index])
                .commit();
        currentIndex = index;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }

    private void selectMenu(MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.menu_item_send_plane:
                showFragment(INDEX_FRAGMENT_PLANERECERIVE);
                lgActivity = false;
                break;
//            case R.id.userImage:
//                intent = new Intent(mContext,LoginActivity.class);
//                startActivity(intent);
//                break;
            case R.id.menu_item_one:
                SharedPreferenceUtil.getInstance().setLogin(false);
                lgActivity = true;
                break;
            case R.id.menu_item_about:
                intent = new Intent(this, AboutUsActivity.class);
                startActivity(intent);
                lgActivity = false;
                break;
            case R.id.menu_item_report:
                showFragment(INDEX_FRAGMENT_REPORT);
                lgActivity = false;
                break;
        }
        if (!lgActivity) {
            selectMenuItem = menuItem;
            toolbar.setTitle(menuItem.getTitle());
        }
        selectMenuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
    }

    public void requestIfHasLetter() {

    }

    public void refreshDrawer() {
        Picasso.with(mContext).load(Constants.BASE_IMAGE_LOAD+SharedPreferenceUtil.getInstance().getImg_id()+Constants.PHOTO_TYPE)
                .placeholder(R.drawable.error_image)
                .error(R.drawable.error_image)
                .into(userImage);
        userName.setText(SharedPreferenceUtil.getInstance().getUserName());
        userSign.setText(SharedPreferenceUtil.getInstance().getSignature());
    }
}
