package com.androidtechies.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtechies.gates16.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shubham on 10-03-2016.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ImageItem> data;

    public GridViewAdapter(Context context, ArrayList<ImageItem> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ImageItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_item_layout, parent, false);
        }
        TextView imageTitle = (TextView) row.findViewById(R.id.text);
        ImageView imageView= (ImageView) row.findViewById(R.id.image);
        imageTitle.setText(data.get(position).getTitle());
        Picasso.with(context).load(data.get(position).getImage()).into(imageView);
        return row;
    }
}
