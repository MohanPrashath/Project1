package com.mgsofttech.ddmods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mgsofttech.ddmods.adapter.AdapterPagerHome;
import com.mgsofttech.ddmods.extras.TinyDB;
import com.mgsofttech.ddmods.fragment.BussidFragment;
import com.mgsofttech.ddmods.fragment.Ets2Fragment;
import com.mgsofttech.ddmods.model.ModelUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    FirebaseAnalytics firebaseAnalytics;
    FirebaseFirestore firebaseFirestore;
    DrawerLayout itemDrawer;
    ImageView homeMenu;
    ImageView homeSettings;
    NavigationView itemNavigation;
    ModelUser modelUser;
    TinyDB tinyDB;
    ViewPager itemPager;
    LinearLayout itemHomeNavBussid;
    TextView itemHomeNavBussidTitle;
    LinearLayout itemHomeNavEts2;
    TextView itemHomeNavEts2Title;

    int resumeCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TinyDB tinyDB2 = new TinyDB(this);
        this.tinyDB = tinyDB2;
        this.modelUser = tinyDB2.getUser();
        this.itemDrawer = (DrawerLayout) findViewById(R.id.itemDrawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.itemNavigation);
        this.itemNavigation = navigationView;
        navigationView.setItemIconTintList((ColorStateList) null);
        this.itemNavigation.setCheckedItem((int) R.id.nav_home);
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.homeSettings = (ImageView) findViewById(R.id.homeSettings);
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        this.homeMenu = (ImageView) findViewById(R.id.homeMenu);
        this.itemPager = (ViewPager) findViewById(R.id.itemPager) ;
        this.itemHomeNavBussid = (LinearLayout) findViewById(R.id.itemHomeNavBussid);
        this.itemHomeNavEts2 = (LinearLayout) findViewById(R.id.itemHomeNavEts2);
        this.itemHomeNavBussidTitle = (TextView) findViewById(R.id.itemHomeNavBussidTitle);
        this.itemHomeNavEts2Title = (TextView) findViewById(R.id.itemHomeNavEts2Title);
        setupNavigationHeader();
        setupNavigationItemClick();
        setupHomeTopBar();
        setupModList();

    }

    private void setupModList() {
        AdapterPagerHome adapterPagerHome = new AdapterPagerHome(getSupportFragmentManager());
        adapterPagerHome.addFragment(new BussidFragment(), "BUSSID");
        adapterPagerHome.addFragment(new Ets2Fragment(), "ETS2");
        this.itemPager.setOffscreenPageLimit(2);
        this.itemPager.setAdapter(adapterPagerHome);
        this.itemPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                Log.d("logPageSelectY", "POS B: " + i);
                if (i == 0) {
                    MainActivity.this.itemHomeNavBussid.callOnClick();
                } else if (i == 1) {
                    MainActivity.this.itemHomeNavEts2.callOnClick();
                }
            }
        });
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        this.itemHomeNavBussid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.itemHomeNavEts2Title.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    MainActivity.this.itemHomeNavBussidTitle.setTextColor(MainActivity.this.getColor(R.color.colorAccent));
                }
                MainActivity.this.itemHomeNavEts2Title.setVisibility(View.GONE);
                MainActivity.this.itemHomeNavBussidTitle.setVisibility(View.VISIBLE);
                MainActivity.this.itemPager.setCurrentItem(0);
            }
        });
        this.itemHomeNavEts2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.itemHomeNavBussidTitle.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    MainActivity.this.itemHomeNavEts2Title.setTextColor(MainActivity.this.getColor(R.color.colorAccent));
                }
                MainActivity.this.itemHomeNavEts2Title.setVisibility(View.VISIBLE);
                MainActivity.this.itemHomeNavBussidTitle.setVisibility(View.GONE);
                MainActivity.this.itemPager.setCurrentItem(1);
            }
        });
    }


    private void setupHomeTopBar() {
        this.homeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.itemDrawer.openDrawer((int) GravityCompat.START);
            }
        });
        this.homeSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }

    private void setupNavigationItemClick() {
        this.itemNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                MainActivity.this.itemDrawer.closeDrawer((int) GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (item.getItemId() == R.id.nav_profile) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        } else if (item.getItemId() == R.id.nav_downloads) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                        }else if (item.getItemId() == R.id.nav_about) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        }else if (item.getItemId() == R.id.nav_contact) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, ContactActivity.class));
                        }
                    }
                }, 300);
                return false;
            }
        });
    }

    private void  setupNavigationHeader() {
        View headerView = this.itemNavigation.getHeaderView(0);
        ((TextView) headerView.findViewById(R.id.itemNavigationHeaderFullName)).setText(this.modelUser.getUserFullName());
        ((TextView) headerView.findViewById(R.id.itemNavigationHeaderEmail)).setText(this.modelUser.getUserEmail());
        ((RequestBuilder) Glide.with((FragmentActivity) this).load(this.modelUser.getUserImage()).placeholder((int) R.drawable.ic_user)).into((ImageView) (CircleImageView) headerView.findViewById(R.id.itemNavigationHeaderImg));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void onBackPressed() {
        if (this.itemDrawer.isDrawerOpen((int) GravityCompat.START)) {
            this.itemDrawer.closeDrawer((int) GravityCompat.START);
        }  else {
            super.onBackPressed();
        }
    }
}