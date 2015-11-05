package org.sssta.zeroind.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.sssta.zeroind.NContent;
import org.sssta.zeroind.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadMessageActivity extends AppCompatActivity {

    @Bind(R.id.leap_edit_button)
    ImageButton leapEditButton;
    @Bind(R.id.message_level_text)
    TextView messageLevelText;
    @Bind(R.id.message_content_text)
    TextView messageContentText;
    @Bind(R.id.message_image)
    ImageView messageImage;
    private int mDirection=0;
    @OnClick(R.id.leap_edit_button)
    public void leapEditActivity() {
        Intent intent = new Intent(this, EditPostWind.class);
        intent.putExtra(NContent.FROM_ACTIVITY_KEY, NContent.REQUEST_MESSAGE);
        intent.putExtra(NContent.INFO_DIRECTION_FRAGMENT, mDirection);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_wind_message);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setEnterTransition(new Explode().setInterpolator(new AnticipateInterpolator()));
        init();
    }
    private void init(){
        Bundle bundle = getIntent().getBundleExtra(NContent.INFO_MESSAGE_ALL);
        messageContentText.setText(bundle.getString(NContent.INFO_MESSAGE_CONTENT));
        mDirection = bundle.getInt(NContent.INFO_MESSAGE_DIRECTION);
    }


}
