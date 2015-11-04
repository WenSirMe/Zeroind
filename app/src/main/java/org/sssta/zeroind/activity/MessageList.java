package org.sssta.zeroind.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.Toast;

import org.sssta.zeroind.R;
import org.sssta.zeroind.adapter.MessageListAdapter;
import org.sssta.zeroind.model.Message;
import org.sssta.zeroind.model.User;
import org.sssta.zeroind.service.BaseService;
import org.sssta.zeroind.service.response.InfoResponse;
import org.sssta.zeroind.service.response.MessageResponse;
import org.sssta.zeroind.util.SharedPreferenceUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MessageList extends AppCompatActivity implements MessageListAdapter.MainRecyclerListener {

    @Bind(R.id.message_recycler_list)
    RecyclerView messageRecyclerList;
    @Bind(R.id.swipe_message_layout)
    SwipeRefreshLayout swipeMessageLayout;

    @Override
    public void onBurnMessageClick(View v, int position) {

    }

    @Override
    public void onThrowMessageClick(View v, int position) {

    }

    @Override
    public void onReadMessageClick(View v, int position) {
        Intent intent = new Intent(this, ReadWindMessage.class);
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
        final MessageListAdapter adapter = new MessageListAdapter(this);
        adapter.setMainRecyclerListener(this);
        messageRecyclerList.setAdapter(adapter);
        messageRecyclerList.setItemAnimator(new FadeInDownAnimator());

        swipeMessageLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeMessageLayout.setRefreshing(true);
                Call<MessageResponse> call = BaseService
                        .getMessageService()
                        .getUnReadMessage(SharedPreferenceUtil.getInstance().getToken());
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Response<MessageResponse> response, Retrofit retrofit) {
                        MessageResponse messageResponse = response.body();
                        if (messageResponse !=null && messageResponse.getStatus()==0){
                            adapter.updateUserMessage((ArrayList<Message>) messageResponse.getUnReadMessageList());
                            Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeMessageLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        swipeMessageLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                    }

                });
//                AsyncTask asyncTask = new AsyncTask() {
//                    @Override
//                    protected Object doInBackground(Object[] params) {
//                        try{
//                            Thread.sleep(2000);
//                        }catch (InterruptedException e){
//                            e.printStackTrace();
//                        }
//
//                        return 1;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Object o) {
//                        if (o.equals(1)){
//                            Toast.makeText(MessageList.this,"Good",Toast.LENGTH_LONG).show();
//                        }
//                        swipeMessageLayout.setRefreshing(false);
//                    }
//                };
//                asyncTask.execute();
            }
        });
    }
}
