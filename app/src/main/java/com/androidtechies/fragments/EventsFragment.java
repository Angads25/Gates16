package com.androidtechies.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtechies.gates16.R;
import com.androidtechies.model.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment
{   private ViewPager viewPager;
    private FragmentManager fragmentManager;

    public EventsFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {   View view = inflater.inflate(R.layout.events_fragment, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        fragmentManager = getActivity().getSupportFragmentManager();
        setupViewPager(viewPager);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    private void setupViewPager(ViewPager viewPager)
    {   ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        String titles[]={"Day 1","Day 2","Day 3"};
        adapter.addFrag(new DayFirstFragment(),titles[0]);
        adapter.addFrag(new DaySecondFragment(), titles[1]);
        adapter.addFrag(new DayThirdFragment(), titles[2]);
        viewPager.setAdapter(adapter);
    }
}
