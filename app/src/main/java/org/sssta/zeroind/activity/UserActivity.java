package org.sssta.zeroind.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.sssta.zeroind.Constants;
import org.sssta.zeroind.R;
import org.sssta.zeroind.ui.CircleImageView;
import org.sssta.zeroind.util.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

    private String[] strings = new String[] {
            "男生","女生","新人类"
    };

    private int[] imageIds = new int[] {
            R.drawable.men,R.drawable.women,R.drawable.flip
    };
    @Bind(R.id.info_userImage)
    CircleImageView infoUserImage;
    @Bind(R.id.info_user_name)
    TextView infoUserName;
    @Bind(R.id.info_user_level)
    TextView infoUserLevel;
    @Bind(R.id.userinfo_sex)
    ImageView sexImage;
    @Bind(R.id.info_user_signature)
    TextView infoUserSignature;
    @Bind(R.id.userinfo_back)
    ImageView btBack;
    @Bind(R.id.userinfo_setting)
    ImageView btSetting;
    @Bind(R.id.userinfo_mailbox)
    ImageView btMailbox;
    @Bind(R.id.userinfo_message)
    ImageView btMessage;
    @Bind(R.id.userinfo_wind)
    ImageView btWind;

    private Context mContext;


    private final int TAG = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        mContext = this;
        freshView();
        init();
    }

    void init() {
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserSetting.class);
                startActivityForResult(intent, TAG);
            }
        });

        btMailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btWind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAG) {
            freshView();
        }
    }

    void freshView() {
        for (int i=0;i<strings.length;i++) {
            if (strings[i].equals(SharedPreferenceUtil.getInstance().getSex())) {
                sexImage.setImageResource(imageIds[i]);
            }
        }
        infoUserName.setText(SharedPreferenceUtil.getInstance().getUserName());
        infoUserLevel.setText("level "+String.valueOf(SharedPreferenceUtil.getInstance().getUserLevel()));
        Picasso.with(mContext).load(Constants.BASE_IMAGE_LOAD + SharedPreferenceUtil.getInstance().getImg_id() +
                Constants.PHOTO_TYPE).into(infoUserImage);
        infoUserSignature.setText(SharedPreferenceUtil.getInstance().getSignature());
    }

}
