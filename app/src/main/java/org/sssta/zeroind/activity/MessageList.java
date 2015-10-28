package org.sssta.zeroind.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import org.sssta.zeroind.R;
import org.sssta.zeroind.adapter.MessageListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;

public class MessageList extends AppCompatActivity implements MessageListAdapter.MainRecyclerListener {

    @Bind(R.id.message_recycler_list)
    RecyclerView messageRecyclerList;

    @Override
    public void onBurnMessageClick(View v, int position) {

    }

    @Override
    public void onThrowMessageClick(View v, int position) {

    }

    @Override
    public void onReadMessageClick(View v, int position) {
        Intent intent = new Intent(this,ReadWindMessage.class);
        getWindow().setExitTransition(new Explode().setInterpolator(new AnticipateInterpolator()));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        messageRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        MessageListAdapter adapter = new MessageListAdapter(this);
        adapter.setMainRecyclerListener(this);
        messageRecyclerList.setAdapter(adapter);
        messageRecyclerList.setItemAnimator(new FadeInDownAnimator());
    }
}
