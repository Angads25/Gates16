package com.androidtechies.gates16;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.androidtechies.fragments.EventsFragment;
import com.androidtechies.fragments.HomeFragment;
import com.androidtechies.fragments.RegistrationFragment;
import com.androidtechies.fragments.SponsorFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.main_layout) DrawerLayout Drawer;
    @Bind(R.id.navigation) NavigationView mNavigationView;
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        Drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if(savedInstanceState == null)
        {   getSupportFragmentManager().beginTransaction().add(R.id.container, new HomeFragment()).commit();
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()){
                    case R.id.home:     getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                                        break;

                    case R.id.events:   getSupportFragmentManager().beginTransaction().replace(R.id.container, new EventsFragment()).commit();
                                        break;

                    case R.id.sponsers: getSupportFragmentManager().beginTransaction().replace(R.id.container, new SponsorFragment()).commit();
                                        break;

                    case R.id.register: //getSupportFragmentManager().beginTransaction().replace(R.id.container, new RegistrationFragment()).commit();
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gatesgtbit.com/form.html"));
                                        startActivity(browserIntent);
                                        break;
                }
                Drawer.closeDrawers();
                return true;
            }
        });
    }
}



