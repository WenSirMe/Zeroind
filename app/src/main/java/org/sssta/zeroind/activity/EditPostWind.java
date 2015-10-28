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

import org.sssta.zeroind.R;
import org.sssta.zeroind.fragment.ChooseWindFragment;
import org.sssta.zeroind.fragment.EditFragment;

import butterknife.ButterKnife;

public class EditPostWind extends AppCompatActivity implements EditFragment.OnFragmentInteractionListener,ChooseWindFragment.OnFragmentInteractionListener {


    SharedPreferences sp;
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void editActivityDown() {
        final FragmentTransaction transaction =  getFragmentManager().beginTransaction();

        saveTempEditContent(editFragment.getEditTextContent());

        transaction.replace(R.id.edit_page_frame, windFragment);
        transaction.commit();
    }

    @Override
    public void editActivityUp() {
        final FragmentTransaction transaction =  getFragmentManager().beginTransaction();
        transaction.replace(R.id.edit_page_frame, editFragment);
        transaction.commit();
    }


    EditFragment editFragment = EditFragment.newInstance(null,null);
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
        sp = getSharedPreferences("TempEdit", Context.MODE_PRIVATE);
        editFragment.setEnterTransition(new Slide(Gravity.TOP).setInterpolator(new OvershootInterpolator(2.5F)).setStartDelay(800));
        windFragment.setEnterTransition(new Slide(Gravity.BOTTOM).setInterpolator(new OvershootInterpolator(2.5F)).setStartDelay(800));

        editFragment.setExitTransition(new Slide(Gravity.TOP).setInterpolator(new AnticipateInterpolator(4.0f)).setDuration(550));
        windFragment.setExitTransition(new Slide(Gravity.BOTTOM).setInterpolator(new AnticipateInterpolator(4.0f)).setDuration(550));

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.edit_page_frame, editFragment).commit();
    }
    private void saveTempEditContent(String string){
        sp.edit().putString("default",string).apply();
    }
}
