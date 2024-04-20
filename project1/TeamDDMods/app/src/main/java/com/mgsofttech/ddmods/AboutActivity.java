package com.mgsofttech.ddmods;

import android.os.Bundle;
import android.widget.ImageView;

public class AboutActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_about);
        setupBackButton();
    }
}