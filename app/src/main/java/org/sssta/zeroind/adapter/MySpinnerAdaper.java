package org.sssta.zeroind.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.sssta.zeroind.R;

/**
 * Created by 林韬 on 2015/10/25.
 */
public class MySpinnerAdaper extends BaseAdapter {

    private String[] strings;
    private Context mContext;
    LayoutInflater inflater;
    private int[] imageIds;

    public MySpinnerAdaper(String[] strings,int[] imageIds, Context mContext) {
        this.imageIds = imageIds;
        this.strings = strings;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.spinner_item,null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.spinner_image);
        TextView textView = (TextView) convertView.findViewById(R.id.spinner_text);
        imageView.setImageResource(imageIds[position]);
        textView.setText(strings[position]);
        return convertView;
    }
}
