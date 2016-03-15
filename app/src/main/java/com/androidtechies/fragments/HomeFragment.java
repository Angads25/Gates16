package com.androidtechies.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidtechies.gates16.R;
import com.androidtechies.model.ImageItem;
import com.androidtechies.model.SocietesAdapter;
import com.androidtechies.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    private Toolbar toolbar;
    private Context context;
    private ArrayList<ImageItem> societyArray = null;
    private String baseImageUrl = "http://gatesapi.herokuapp.com/img/icons/";
    private ImageView indicator1, indicator2, indicator3;
    private static String TAG = "HomeFragment";
    private RecyclerView myList;
    private String societyUrl = "http://gatesapi.herokuapp.com/SocietesCard?q=societies";
    int i;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        societyArray = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, societyUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0 ; i < array.length(); i++){
                                JSONObject object = array.optJSONObject(i);
                                String tempTitle = object.getString("sname");
                                String tempUrl = baseImageUrl + object.getString("imgurl");

                                Log.e(TAG, "" + tempTitle + " " +tempUrl);
                                societyArray.add(new ImageItem(tempUrl, tempTitle));
                            }
                            myList.setAdapter(new SocietesAdapter(context, societyArray));

                        } catch (JSONException e) {
                            Log.v(TAG, ""+ e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "E"+ error, Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        indicator1 = (ImageView) view.findViewById(R.id.indicator1);
        indicator2 = (ImageView) view.findViewById(R.id.indicator2);
        indicator3 = (ImageView) view.findViewById(R.id.indicator3);
        CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout)view.findViewById(R.id.collapse_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getActivity().getTheme()));
        }
        else {
            collapsingToolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#FFFFFF"));
        ViewPager pager=(ViewPager)view.findViewById(R.id.pager);
        FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position)
                {   case 1: return new PagerSecondFragment();

                    case 2: return new PagerThirdFragment();

                    default: return new PagerFirstFragment();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
        pager.setAdapter(fragmentStatePagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position)
                {
                    case 0:
                        indicator1.setImageResource(R.drawable.selected_indicator);
                        break;
                    case 1:
                        indicator2.setImageResource(R.drawable.selected_indicator);
                        break;
                    case 2:
                        indicator3.setImageResource(R.drawable.selected_indicator);
                        break;
                }
                if(i!=position)
                {   indicator1.setImageResource(R.drawable.unselected_indicator);
                    indicator2.setImageResource(R.drawable.unselected_indicator);
                    indicator3.setImageResource(R.drawable.unselected_indicator);
                }
            }

            @Override
            public void onPageSelected(int position) {
                i=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        TextView TV=(TextView)view.findViewById(R.id.gatesIntro);
        Spanned sp= Html.fromHtml(getResources().getString(R.string.gtbit));
        TV.setText(sp);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false);
        myList = (RecyclerView)view.findViewById(R.id.societies);
        myList.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
