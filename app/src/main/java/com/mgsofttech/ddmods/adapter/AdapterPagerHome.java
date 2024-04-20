package com.mgsofttech.ddmods.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mgsofttech.ddmods.fragment.BussidPaidFragment;

import java.util.ArrayList;

public class AdapterPagerHome extends FragmentPagerAdapter {
    private final  ArrayList<Fragment> modelFragmentList = new ArrayList<>();
    private  final ArrayList<String> modelTitleList = new ArrayList<>();

    public AdapterPagerHome(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragment(Fragment fragment, String str) {
        this.modelTitleList.add(str);
        this.modelFragmentList.add(fragment);
    }

    public Fragment getItem(int i) {
        return this.modelFragmentList.get(i);
    }

    public int getCount() {
        return this.modelFragmentList.size();
    }

    public CharSequence getPageTitle(int i) {
        return this.modelTitleList.get(i);
    }

}

