package org.numisoft.eurocoinsii.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.numisoft.eurocoinsii.R;
import org.numisoft.eurocoinsii.adapters.PageViewAdapter;
import org.numisoft.eurocoinsii.utils.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Constants {

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    PageViewAdapter pagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    SharedPreferences preferences;
    private Menu navigationViewMenu;

    int[] finland = new int[]{2005, 2004, 2003, 2002, 2001, 2000, 1999};
    int[] estonia = new int[]{2011, 2010, 2009};

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        onCreate(savedInstanceState);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

// Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.the_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Finland");

// Tabs
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        resetTabs(finland);


// Pager
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                "Finland", finland);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

// Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

// Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.addHeaderView(View.inflate(this, R.layout.nav_header, null));
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

// Navigation menu transformation

    }

    private void resetTabs(int[] country) {
        tabLayout.removeAllTabs();
        for (int i = 0; i < country.length; i++) {
            tabLayout.addTab(
                    tabLayout.newTab().setText(Integer.toString(country[i])));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aux_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu_estonia:
                resetTabs(estonia);
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        "Estonia", estonia);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle("Estonia");
                break;
            case R.id.nav_menu_finland:
                resetTabs(finland);
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        "Finland", finland);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle("Finland");
                break;

        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int viewMode = preferences.getInt(VIEW_MODE, 1);
        switch (viewMode) {
            case 0:
                menu.getItem(3).setChecked(true);
                break;
            case 1:
                menu.getItem(1).setChecked(true);
                break;
            case 2:
                menu.getItem(2).setChecked(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        resetDisplay();
    }

//    private void resetDisplay() {
//        int viewMode = preferences.getInt(VIEW_MODE, 1);
//        switch (viewMode) {
//            case 0:
//                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
//                        tabLayout.getTabCount(), Theme.PARKS, Theme.PARKS);
//                viewPager.setAdapter(pagerAdapter);
//                getSupportActionBar().setTitle(Theme.PARKS.getValue());
//                break;
//            case 1:
//                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
//                        tabLayout.getTabCount(), Theme.PARKS_P, Theme.PARKS_P);
//                viewPager.setAdapter(pagerAdapter);
//                getSupportActionBar().setTitle(Theme.PARKS_P.getValue());
//                break;
//            case 2:
//                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
//                        tabLayout.getTabCount(), Theme.PARKS_P, Theme.PARKS_D);
//                viewPager.setAdapter(pagerAdapter);
//                getSupportActionBar().setTitle(Theme.PARKS.getValue());
//                break;
//        }
//    }
}
