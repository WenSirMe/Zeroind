package org.sssta.zeroind.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.sssta.zeroind.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林韬 on 2015/10/6.
 */
public class RecyclerWrapAdapter extends RecyclerView.Adapter {

    private List<Integer> lists;


    private View headerLayout;

    private int mCurrentPosition;

    private boolean isHeaderViewSetted = false;

    public List<Integer> getLists() {
        return lists;
    }


    public RecyclerWrapAdapter(View headerLayout) {
        this.headerLayout = headerLayout;
        if (headerLayout != null) {
            isHeaderViewSetted = true;
        }
        lists = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("view type",viewType+"");
        if (viewType == RecyclerView.INVALID_TYPE) {
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(headerLayout);
            return headerViewHolder;
        } else if (viewType == RecyclerView.INVALID_TYPE - 1) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_text, null);
            view.setLayoutParams(new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewSetted && position < 1) {
            return;
        }
        ((ItemViewHolder)holder).textView.setText(String.valueOf(lists.
                get(position-(isHeaderViewSetted ? 1: 0))));
        return;
    }

    @Override
    public int getItemCount() {
        return isHeaderViewSetted ? lists.size() + 1 : lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        mCurrentPosition = position;;
        if (position < 1 && isHeaderViewSetted) {
            return RecyclerView.INVALID_TYPE;
        }
        return RecyclerView.INVALID_TYPE - 1;
    }


//    @Override
//    public long getItemId(int position) {
//        if (!isHeaderViewSetted || position >= 1) {
//            return super.getItemId(position);
//        }
//        return -1;
//    }


    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.pull_header_image);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_text);
        }
    }

    public View getHeaderLayout() {
        return headerLayout;
    }

    /**
     * 设置头部linearLayout
     * @param headerLayout
     */
    public void setHeaderLayout(View headerLayout) {
        this.headerLayout = headerLayout;
        isHeaderViewSetted = true;
    }
}
