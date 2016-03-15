package com.androidtechies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.androidtechies.gates16.R;
import com.androidtechies.model.GridViewAdapter;
import com.androidtechies.model.ImageItem;

import java.util.ArrayList;

/**<p>
 * Created by Shubham on 10-03-2016.
 * </p>
 */
public class SponsorFragment extends Fragment
{   private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sponsor,container,false);
        GridView gridView=(GridView)view.findViewById(R.id.gridView);
//        ArrayList<ImageItem> arr=new ArrayList<>();
//        for(int i=0;i<20;i++)
//        {   arr.add(new ImageItem(R.mipmap.ic_launcher,i+"th Grid"));
//        }
//        gridView.setAdapter(new GridViewAdapter(context,arr));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
