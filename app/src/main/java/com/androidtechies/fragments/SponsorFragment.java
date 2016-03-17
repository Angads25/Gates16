package com.androidtechies.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidtechies.gates16.R;
import com.androidtechies.model.GridViewAdapter;
import com.androidtechies.model.ImageItem;
import com.androidtechies.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**<p>
 * Created by Shubham on 10-03-2016.
 * </p>
 */
public class SponsorFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{   private Context context;
    private  ArrayList<ImageItem> arr;
    private GridView gridView;
    private Toolbar toolbar;
    //private static String TAG="SponserFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout rellayout;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sponsor,container,false);
        toolbar=(Toolbar)((AppCompatActivity)context).findViewById(R.id.toolbar);
        gridView=(GridView)view.findViewById(R.id.gridView);
        rellayout=(RelativeLayout)view.findViewById(R.id.rellayout);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_sponsers);
        swipeRefreshLayout.setOnRefreshListener(this);
        arr=new ArrayList<>();
        String sponserUrl = "http://gatesapi.herokuapp.com/SponsorsInfo";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, sponserUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e(TAG,response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0 ; i < array.length(); i++){
                                JSONObject object = array.optJSONObject(i);
                                String title = object.getString("title");
                                toolbar.setTitle(title);
                                JSONArray sponsers = object.getJSONArray("array");
                                for(int j=0;j<sponsers.length();j++)
                                {   JSONObject object1 = sponsers.optJSONObject(j);
                                    String name = object1.getString("name");
                                    String banner = object1.getString("banner");
                                    arr.add(new ImageItem(banner, name));
                                }
                            }
                            gridView.setAdapter(new GridViewAdapter(context, arr));
                        } catch (JSONException e) {
                            //Log.e(TAG, ""+ e);
                            Snackbar snackbar = Snackbar.make(rellayout, "Failed To Fetch Data", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        finally
                        {   progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "E"+ error, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(rellayout, "Failed To Fetch Data", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(request);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onRefresh() {
        Thread T1=new Thread(new Runnable() {
            @Override
            public void run() {
                arr=new ArrayList<>();
                String sponserUrl = "http://gatesapi.herokuapp.com/SponsorsInfo";
                StringRequest request = new StringRequest(Request.Method.GET, sponserUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.e(TAG,response);
                            try {
                                JSONArray array = new JSONArray(response);
                                for (int i = 0 ; i < array.length(); i++){
                                    JSONObject object = array.optJSONObject(i);
                                    String title = object.getString("title");
                                    toolbar.setTitle(title);
                                    JSONArray sponsers = object.getJSONArray("array");
                                    for(int j=0;j<sponsers.length();j++)
                                    {   JSONObject object1 = sponsers.optJSONObject(j);
                                        String name = object1.getString("name");
                                        String banner = object1.getString("banner");
                                        arr.add(new ImageItem(banner, name));
                                    }
                                }
                                gridView.setAdapter(new GridViewAdapter(context, arr));
                            } catch (JSONException e) {
                                //Log.e(TAG, ""+ e);
                                Snackbar snackbar = Snackbar.make(rellayout, "Failed To Fetch Data", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                            finally {
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                });
                            }
                        }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, "E"+ error, Toast.LENGTH_LONG).show();
                        Snackbar snackbar = Snackbar.make(rellayout, "Failed To Fetch Data", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });
                VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(request);
            }
        });
        T1.start();
    }
}
