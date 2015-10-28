package org.sssta.zeroind.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import org.sssta.zeroind.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostMessage extends AppCompatActivity {

    @Bind(R.id.frame_leap_edit_button)
    FrameLayout frameLeapEditButton;
    @OnClick(R.id.frame_leap_edit_button)
    public void leapEditWindActivity(){
        Intent intent = new Intent(this,EditPostWind.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
