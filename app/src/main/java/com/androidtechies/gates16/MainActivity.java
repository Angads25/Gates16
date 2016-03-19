package com.androidtechies.gates16;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.androidtechies.fragments.EventsFragment;
import com.androidtechies.fragments.HomeFragment;
import com.androidtechies.fragments.SponsorFragment;

public class MainActivity extends AppCompatActivity {
    private  NavigationView mNavigationView;
    private Toolbar toolbar;
    private DrawerLayout Drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        Drawer=(DrawerLayout)findViewById(R.id.main_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("Gates 2K16");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        Drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if(savedInstanceState==null)
        {   getSupportFragmentManager().beginTransaction().add(R.id.container, new HomeFragment()).commit();
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                        toolbar.setTitle("Home");
                        break;

                    case R.id.events:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new EventsFragment()).commit();
                        toolbar.setTitle("Events");
                        break;

                    case R.id.sponsers:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SponsorFragment()).commit();
                        break;

                    case R.id.register:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gatesgtbit.com/form.html"));
                        startActivity(browserIntent);
                        break;

                    case R.id.sub_header:
                        startActivity(getOpenGmailIntent());
                        break;
                }
                Drawer.closeDrawers();
                return true;
            }
        });
    }

    public static Intent getOpenGmailIntent() {
        Intent send = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode("contact@gatesgtbit.com") +
                "?subject=" + Uri.encode("Feedback") +
                "&body=" + Uri.encode("");
        Uri uri = Uri.parse(uriText);

        send.setData(uri);
        return Intent.createChooser(send, "Send mail");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(Drawer.isDrawerOpen(mNavigationView))
        {   Drawer.closeDrawer(mNavigationView);
        }
        else
        {   AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle("Exit");
            builder.setMessage("Are you sure you want to Exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
            AppCompatDialog dialog = builder.create();
            dialog.show();
        }
    }
}



