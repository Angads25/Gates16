package com.androidtechies.gates16;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidtechies.utils.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EventActivity extends AppCompatActivity
{   private Context context;
    private ImageView titleImage;
    //private static String TAG = "EventActivity";
    private CollapsingToolbarLayout collapsingToolbar;

    @SuppressWarnings("deprecation")
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        context=this;
        titleImage = (ImageView) findViewById(R.id.parralax_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.event_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("event_id");
        //Log.e(TAG, id +"");
        collapsingToolbar=(CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#FFFFFF"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.white,getTheme()));
            collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary,getTheme()));
        }
        else {
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.white));
            collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int screenHeight = displaymetrics.heightPixels;
//        int actionBarHeight = 0;
//        TypedValue tv = new TypedValue();
//        if (this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
//            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//        }
//        CardView view=(CardView)findViewById(R.id.view);
//        view.setMinimumHeight(screenHeight - actionBarHeight);
        String eventInfoUrl = "https://gatesapi.herokuapp.com/EventDetails?q=";
        StringRequest request = new StringRequest(Request.Method.GET, eventInfoUrl + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e(TAG, response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0 ; i < array.length(); i++){
                                JSONObject object = array.optJSONObject(i);
                                String image = object.getString("banner");
                                final String title = object.getString("ename");
                                String sname = object.getString("sname");
                                String desc = object.getString("desc");
                                String time1 = object.getString("time1");
                                String time2 = object.getString("time2");
                                String time3 = object.getString("time3");
                                String venue = object.getString("venue");
                                JSONObject em=object.getJSONObject("emanager");
                                String emname=em.getString("emname");
                                final String emmail=em.getString("emmail");
                                final String emphno=em.getString("emphno");
                                final String form=object.getString("form");
                                if(!image.equals("")) {
                                    Picasso.with(context).load(image).into(titleImage);
                                }
                                AppCompatTextView event_name=(AppCompatTextView)findViewById(R.id.event_name);
                                AppCompatTextView society_name=(AppCompatTextView)findViewById(R.id.society_name);
                                AppCompatTextView event_desc=(AppCompatTextView)findViewById(R.id.event_desc);
                                AppCompatTextView event_time1=(AppCompatTextView)findViewById(R.id.event_time1);
                                AppCompatTextView event_time2=(AppCompatTextView)findViewById(R.id.event_time2);
                                AppCompatTextView event_time3=(AppCompatTextView)findViewById(R.id.event_time3);
                                AppCompatTextView event_venue=(AppCompatTextView)findViewById(R.id.event_venue);
                                AppCompatTextView event_man=(AppCompatTextView)findViewById(R.id.event_manager);
                                AppCompatTextView cno=(AppCompatTextView)findViewById(R.id.cno);
                                AppCompatTextView cid=(AppCompatTextView)findViewById(R.id.cid);
                                AppCompatButton call=(AppCompatButton)findViewById(R.id.call);
                                AppCompatButton email=(AppCompatButton)findViewById(R.id.email);
                                AppCompatButton register=(AppCompatButton)findViewById(R.id.reg);
                                if(emphno.equals(""))
                                {   call.setVisibility(View.GONE);
                                    cno.setVisibility(View.GONE);
                                }
                                else
                                {   cno.setText("Contact no.:"+emphno);
                                    call.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                            intent.setData(Uri.parse("tel:"+emphno));
                                            startActivity(intent);
                                        }
                                    });
                                }
                                if(emmail.equals(""))
                                {   email.setVisibility(View.GONE);
                                    cid.setVisibility(View.GONE);
                                }
                                else
                                {   cid.setText("Contact Email.:"+emmail);
                                    email.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(getOpenGmailIntent(emmail,title));
                                        }
                                    });
                                }
                                if(form.equals(""))
                                {   register.setVisibility(View.GONE);
                                }
                                else
                                {   register.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(form));
                                            startActivity(browserIntent);
                                        }
                                    });
                                }
                                event_name.setText(title);
                                society_name.setText(sname);
                                event_desc.setText("Event Description:\n"+desc);
                                if(time1.equals(""))
                                {   event_time1.setVisibility(View.GONE);
                                }
                                else
                                {   Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("IST"));
                                    cal.setTimeInMillis(Long.parseLong(time1));
                                    Date date=cal.getTime();
                                    SimpleDateFormat ft=new SimpleDateFormat ("E dd/MM 'at' hh:mm a", Locale.getDefault());
                                    event_time1.setText(ft.format(date));
                                }
                                if(time2.equals(""))
                                {   event_time2.setVisibility(View.GONE);
                                }
                                else
                                {   Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("IST"));
                                    cal.setTimeInMillis(Long.parseLong(time2));
                                    Date date=cal.getTime();
                                    SimpleDateFormat ft=new SimpleDateFormat ("E dd/MM 'at' hh:mm a", Locale.getDefault());
                                    event_time2.setText(ft.format(date));

                                }
                                if(time3.equals(""))
                                {   event_time3.setVisibility(View.GONE);
                                }
                                else
                                {   Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("IST"));
                                    cal.setTimeInMillis(Long.parseLong(time3));
                                    Date date=cal.getTime();
                                    SimpleDateFormat ft=new SimpleDateFormat ("E dd/MM 'at' hh:mm a", Locale.getDefault());
                                    event_time3.setText(ft.format(date));
                                }
                                if(sname.equals(""))
                                {   society_name.setVisibility(View.GONE);
                                }
                                event_venue.setText("Venue:\n"+venue);
                                event_man.setText("Event Manager:\n"+emname);
                                collapsingToolbar.setTitle(title);
                            }
                        }
                        catch (JSONException e) {
                            //Log.e(TAG, "" + e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "E" + error, Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(request);
    }

    public static Intent getOpenGmailIntent(String email,String event_name) {
        Intent send = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode(email) +
                "?subject=" + Uri.encode(event_name) +
                "&body=" + Uri.encode("");
        Uri uri = Uri.parse(uriText);

        send.setData(uri);
        return Intent.createChooser(send, "Send mail");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}


//Calendar cal = Calendar.getInstance();
//Intent intent = new Intent(Intent.ACTION_EDIT);
//intent.setType("vnd.android.cursor.item/event");
//        intent.putExtra("beginTime", cal.getTimeInMillis());
//        intent.putExtra("allDay", true);
//        intent.putExtra("rrule", "FREQ=YEARLY");
//        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
//        intent.putExtra("title", "A Test Event from android app");
//        startActivity(intent);