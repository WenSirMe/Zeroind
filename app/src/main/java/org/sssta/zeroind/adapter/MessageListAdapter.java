package org.sssta.zeroind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import org.sssta.zeroind.R;
import org.sssta.zeroind.model.Message;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Heaven on 2015/10/2.
 */
public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private Context context;
    public static int ItemCount = 0;
    private static final int ITEM_TYPE_TEXT = 1;
    private static final int ITEM_TYPE_IMAGE = 2;
    private static final int READ_MESSAGE = 1;
    private static final int THROW_MESSAGE= 2;
    private static final int BURN_MESSAGE= 3;
    private static final int CONTENT_IMAGE = 4;
    private static ArrayList<Message> messageList = new ArrayList<>();
    private MainRecyclerListener mainRecyclerListener;
    private final ArrayList<ArrayList<Object>> ItemList = new ArrayList<>();

    @Override
    public void onClick(View v) {
        int position;
        switch ((int) v.getTag(R.id.tag_first)) {
            case READ_MESSAGE:

                if (mainRecyclerListener != null) {
                    mainRecyclerListener.onReadMessageClick(v, (int) v.getTag(R.id.tag_second));
                }
                break;
            case THROW_MESSAGE:
                if (mainRecyclerListener != null) {
                    position = (int) v.getTag(R.id.tag_second);
                    deleteItemData(position);
                    mainRecyclerListener.onThrowMessageClick(v, (int) v.getTag(R.id.tag_second));

                }
                break;
            case BURN_MESSAGE:
                if (mainRecyclerListener != null) {
                    position = (int) v.getTag(R.id.tag_second);
                    deleteItemData(position);
                    mainRecyclerListener.onBurnMessageClick(v, (int) v.getTag(R.id.tag_second));
                }
                break;
        }
    }

    public MessageListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return ItemCount;
    }

    public void setItemCount(int count){
        ItemCount = count;
    }
    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE_TEXT : ITEM_TYPE_IMAGE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (ItemList.size() == 0) bindItemContent();
        final StartViewHolder startViewHolder = (StartViewHolder)holder;
        if (getItemViewType(position) == ITEM_TYPE_IMAGE) {
            startViewHolder.viewHasFlag.setVisibility(View.INVISIBLE);
        } else {
            startViewHolder.viewHasFlag.setVisibility(View.VISIBLE);
        }
        startViewHolder.burnMessage.setOnClickListener(this);
        startViewHolder.throwMessage.setOnClickListener(this);
        startViewHolder.readMessage.setOnClickListener(this);

        startViewHolder.burnMessage.setTag(R.id.tag_first, BURN_MESSAGE);
        startViewHolder.throwMessage.setTag(R.id.tag_first, THROW_MESSAGE);
        startViewHolder.readMessage.setTag(R.id.tag_first, READ_MESSAGE);

        startViewHolder.burnMessage.setTag(R.id.tag_second, position);
        startViewHolder.throwMessage.setTag(R.id.tag_second, position);
        startViewHolder.readMessage.setTag(R.id.tag_second, position);

        startViewHolder.abbrMessage.setText(messageList.get(position).getContent());
        //startViewHolder.userLevel.setText(messageList.get(position).);
        //此处应从后端获得
        startViewHolder.userLevel.setText(String.valueOf((int)(Math.random()*12)));
        YoYo.with(Techniques.FlipInX).playOn(startViewHolder.itemView);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        StartViewHolder startViewHolder = new StartViewHolder(view);
        return startViewHolder;
    }




    public void setMainRecyclerListener(MainRecyclerListener mainRecyclerListener) {
        this.mainRecyclerListener = mainRecyclerListener;
    }

    private void deleteItemData(int position) {
        ItemList.remove(position);
        ItemCount = ItemList.size();
        Log.i("ItemCount", String.valueOf(ItemCount));
        notifyItemRemoved(position);
    }

    private void addItemData(int position) {
    }

    private void bindItemContent() {

    }

    public static class StartViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.throw_message)
        ImageView throwMessage;
        @Bind(R.id.burn_message)
        ImageView burnMessage;
        @Bind(R.id.read_message)
        ImageView readMessage;
        @Bind(R.id.user_level)
        TextView userLevel;
        @Bind(R.id.abbr_message)
        TextView abbrMessage;
        @Bind(R.id.view_has_flag)
        ImageView viewHasFlag;
        public StartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void updateUserMessage(ArrayList<Message> mList){
        messageList = mList;
        setItemCount(messageList.size());
        notifyDataSetChanged();
    }

    public interface MainRecyclerListener {
        public void onBurnMessageClick(View v, int position);

        public void onThrowMessageClick(View v, int position);

        public void onReadMessageClick(View v, int position);

    }


}

