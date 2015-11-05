package org.sssta.zeroind.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import org.sssta.zeroind.NContent;
import org.sssta.zeroind.R;
import org.sssta.zeroind.fragment.ChooseWindFragment;
import org.sssta.zeroind.fragment.EditFragment;
import org.sssta.zeroind.service.BaseService;
import org.sssta.zeroind.service.response.ResponseStatus;
import org.sssta.zeroind.util.SharedPreferenceUtil;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EditPostWind extends AppCompatActivity implements EditFragment.OnFragmentInteractionListener,ChooseWindFragment.OnFragmentInteractionListener {

    private int FromActivityIndex = 0;
    private int mDirection = 0;
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void editActivityDown() {
        final FragmentTransaction transaction =  getFragmentManager().beginTransaction();
        transaction.replace(R.id.edit_page_frame, windFragment);
        transaction.commit();
    }

    @Override
    public void editActivityUp(int model,int direction) {
        final FragmentTransaction transaction =  getFragmentManager().beginTransaction();
        editFragment = EditFragment.newInstance(FromActivityIndex,direction);
        initTranstionAnimation();
        transaction.replace(R.id.edit_page_frame, editFragment);
        transaction.commit();
        if (model == 1)
            editFragment.submitTextToServer(direction);
    }


    EditFragment editFragment = EditFragment.newInstance(FromActivityIndex,mDirection);
    ChooseWindFragment windFragment = ChooseWindFragment.newInstance(null,null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post_wind);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init(){

        initTranstionAnimation();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.edit_page_frame, editFragment).commit();

        FromActivityIndex = getIntent().getIntExtra(NContent.FROM_ACTIVITY_KEY,0);
        mDirection = getIntent().getIntExtra(NContent.INFO_DIRECTION_FRAGMENT,0);
    }

    private void initTranstionAnimation(){
        editFragment.setEnterTransition(new Slide(Gravity.TOP).setInterpolator(new OvershootInterpolator(2.5F)).setStartDelay(800));
        editFragment.setExitTransition(new Slide(Gravity.TOP).setInterpolator(new AnticipateInterpolator(4.0f)).setDuration(550));
        windFragment.setEnterTransition(new Slide(Gravity.BOTTOM).setInterpolator(new OvershootInterpolator(2.5F)).setStartDelay(800));
        windFragment.setExitTransition(new Slide(Gravity.BOTTOM).setInterpolator(new AnticipateInterpolator(4.0f)).setDuration(550));
    }
}
