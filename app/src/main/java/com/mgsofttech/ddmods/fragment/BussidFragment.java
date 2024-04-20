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


public class BussidFragment extends Fragment {

    ViewPager itemPager;
    TabLayout itemTabLayout;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_bussid, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view,bundle);
        this.itemPager = (ViewPager) getActivity().findViewById(R.id.itemPagerBussid);
        this.itemTabLayout = (TabLayout) getActivity().findViewById(R.id.itemTabLayoutBussid);
        setTabLayout();
    }

    private void setTabLayout() {
        AdapterPagerHome adapterPagerHome = new AdapterPagerHome(getActivity().getSupportFragmentManager());
        adapterPagerHome.addFragment(new BussidFreeFragment(),"FREE MODS");
        adapterPagerHome.addFragment(new BussidPaidFragment(),"PAID MODS");
        this.itemPager.setOffscreenPageLimit(2);
        this.itemPager.setAdapter(adapterPagerHome);
        this.itemTabLayout.setupWithViewPager(this.itemPager);
        this.itemTabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) new TabLayout.OnTabSelectedListener() {
            public void onTabReselected(TabLayout.Tab tab) {
            }

            public void onTabUnselected(TabLayout.Tab tab) {
            }

            public void onTabSelected(TabLayout.Tab tab) {
                BussidFragment.this.itemPager.setCurrentItem(tab.getPosition());
            }
        });
    }
}