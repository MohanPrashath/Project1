package com.mgsofttech.ddmods.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mgsofttech.ddmods.R;
import com.mgsofttech.ddmods.adapter.AdapterPagerHome;


public class Ets2Fragment extends Fragment {

    ViewPager itemPager;
    TabLayout itemTabLayout;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ets2, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view,bundle);
        this.itemPager = (ViewPager) getActivity().findViewById(R.id.itemPagerEts2);
        this.itemTabLayout = (TabLayout) getActivity().findViewById(R.id.itemTabLayoutEts2);
        setTabLayout();
    }

    private void setTabLayout() {
        AdapterPagerHome adapterPagerHome = new AdapterPagerHome(getActivity().getSupportFragmentManager());
        adapterPagerHome.addFragment(new Ets2FreeFragment(),"FREE MODS");
        adapterPagerHome.addFragment(new Ets2PaidFragment(),"PAID MODS");
        this.itemPager.setOffscreenPageLimit(2);
        this.itemPager.setAdapter(adapterPagerHome);
        this.itemTabLayout.setupWithViewPager(this.itemPager);
        this.itemTabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) new TabLayout.OnTabSelectedListener() {
            public void onTabReselected(TabLayout.Tab tab) {
            }

            public void onTabUnselected(TabLayout.Tab tab) {
            }

            public void onTabSelected(TabLayout.Tab tab) {
                Ets2Fragment.this.itemPager.setCurrentItem(tab.getPosition());
            }
        });
    }
}