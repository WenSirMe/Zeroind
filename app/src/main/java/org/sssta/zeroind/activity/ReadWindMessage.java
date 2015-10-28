package org.sssta.zeroind.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.animation.AnticipateInterpolator;

import org.sssta.zeroind.R;

public class ReadWindMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_wind_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setEnterTransition(new Explode().setInterpolator(new AnticipateInterpolator()));
    }
}
