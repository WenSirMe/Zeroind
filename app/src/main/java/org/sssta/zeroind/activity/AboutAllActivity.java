package org.sssta.zeroind.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.sssta.zeroind.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutAllActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.check_update_button)
    Button checkUpdateButton;
    @Bind(R.id.request_developer_button)
    Button requestDeveloperButton;
    @Bind(R.id.developer_name_button)
    Button developerNameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_all);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }
    private void init(){
        checkUpdateButton.setOnClickListener(this);
        requestDeveloperButton.setOnClickListener(this);
        developerNameButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_update_button:
                Toast.makeText(this,"Beta版暂无自动更新",Toast.LENGTH_LONG).show();
                break;
            case R.id.request_developer_button:
                Toast.makeText(this,"Beta版暂无反馈",Toast.LENGTH_LONG).show();
                break;
            case R.id.developer_name_button:
                Intent intent = new Intent(this,AboutUsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
