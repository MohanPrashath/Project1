package com.mgsofttech.ddmods;

import android.os.Bundle;

public class ContactActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setupBackButton();
    }
}