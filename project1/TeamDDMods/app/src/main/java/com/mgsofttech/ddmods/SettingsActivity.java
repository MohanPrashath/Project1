package com.mgsofttech.ddmods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.mgsofttech.ddmods.extras.TinyDB;
import com.mgsofttech.ddmods.model.ModelUser;
import com.mgsofttech.ddmods.sharedpref.DarkModePrefManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends ActivityBase {
    private Switch darkModeSwitch;
    private TextView userEmail,userFullName;
    private CircleImageView userImage;
    private TinyDB tinyDB;
    private ModelUser modelUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TinyDB tinyDB2 = new TinyDB(this);
        this.tinyDB = tinyDB2;
        this.modelUser = tinyDB2.getUser();
        this.userFullName = (TextView) findViewById(R.id.userFullName);
        this.userEmail = (TextView) findViewById(R.id.userEmail);
        this.userFullName.setText(this.modelUser.getUserFullName());
        this.userEmail.setText(this.modelUser.getUserEmail());
        this.userImage = (CircleImageView)  findViewById(R.id.userImage);
        ((RequestBuilder) Glide.with((FragmentActivity) this).load(this.modelUser.getUserImage()).placeholder((int) R.drawable.ic_user)).into((ImageView) this.userImage);

        setupBackButton();
        setDarkModeSwitch();

        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    private void setDarkModeSwitch(){
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setChecked(new DarkModePrefManager(this).isNightMode());
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DarkModePrefManager darkModePrefManager = new DarkModePrefManager(SettingsActivity.this);
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        });
    }
}