package org.sssta.zeroind.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import org.sssta.zeroind.R;
import org.sssta.zeroind.util.SharedPreferenceUtil;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (SharedPreferenceUtil.getInstance().getLoginStatus()) {
                    intent = new Intent(WelcomeActivity.this,MainActivity.class);
                } else {
                    intent = new Intent(WelcomeActivity.this,RegisterActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1000);
    }


}
