package com.androidtechies.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidtechies.gates16.EventActivity;
import com.androidtechies.gates16.R;
import com.androidtechies.model.EventItem;
import com.androidtechies.model.ListAdapter;
import com.androidtechies.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**<p>
 * Created by Jasbir Singh on 2/16/2016.
 * </p>
 */
public class DaySecondFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private Context context;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String eventDayTwoUrl = "https://gatesapi.herokuapp.com/DayEvents?day=2";
    private ArrayList<EventItem> eventDayTwoArray = null;
    private static String TAG = "DaySecondFragment";
    private ListView listView;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_day);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, EventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("event_id",eventDayTwoArray.get(position).getEvent_id());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        eventDayTwoArray = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, eventDayTwoUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array2 = new JSONArray(response);
                            for(int i=0;i<array2.length();i++) {
                                JSONObject object = array2.optJSONObject(i);
                                String title = object.getString("ename");
                                String image = object.getString("ebanner");
                                long time = Long.parseLong(object.getString("time"));
                                int id=Integer.parseInt(object.getString("eid"));
                                String desc=object.getString("desc");
                                eventDayTwoArray.add(new EventItem(title, time, image,id,desc));
                                Log.e(TAG, "" + title + " " + eventDayTwoUrl);

                            }
                            listView.setAdapter(new ListAdapter(context, eventDayTwoArray));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "E"+ error, Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
        return view;
    }

    @Override
    public void onRefresh() {
        Thread T1=new Thread(new Runnable() {
            @Override
            public void run() {
                eventDayTwoArray = new ArrayList<>();
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog = new ProgressDialog(context);
                        dialog.setMessage("Loading...");
                        dialog.show();
                    }
                });
                StringRequest request = new StringRequest(Request.Method.GET, eventDayTwoUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0 ; i < array.length(); i++){
                                JSONObject object = array.optJSONObject(i);
                                String title = object.getString("ename");
                                String image = object.getString("ebanner");
                                long time = Long.parseLong(object.getString("time"));
                                int id=Integer.parseInt(object.getString("eid"));
                                String desc=object.getString("desc");
                                eventDayTwoArray.add(new EventItem(title, time, image,id,desc));
                            }
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(new ListAdapter(context, eventDayTwoArray));
                                }
                            });
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
                VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if(dialog.isShowing())
                        {   dialog.dismiss();
                        }
                    }
                });
            }
        });
        T1.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
