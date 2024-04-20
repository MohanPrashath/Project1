package com.mgsofttech.ddmods.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mgsofttech.ddmods.R;

public class BussidFreeFragment extends Fragment {

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ets2_free, viewGroup, false);
    }
}
