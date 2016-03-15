package com.androidtechies.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtechies.gates16.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Angad on 11/03/2016.
 */
public class SocietesAdapter extends RecyclerView.Adapter<SocietesAdapter.SocietyViewHolder>
{   private Context context;
    private ArrayList<ImageItem> arr;

    public SocietesAdapter(Context context,ArrayList<ImageItem> arr)
    {   this.context=context;
        this.arr=arr;
    }

    @Override
    public SocietyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item_layout,parent,false);
        return new SocietyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SocietyViewHolder holder, int position) {
        holder.imageTitle.setText(arr.get(position).getTitle());
        Picasso.with(context).load(arr.get(position).getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class SocietyViewHolder extends RecyclerView.ViewHolder
    {   public TextView imageTitle;
        public ImageView imageView;

        public SocietyViewHolder(View itemView) {
            super(itemView);
            imageTitle = (TextView) itemView.findViewById(R.id.text);
            imageView= (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
