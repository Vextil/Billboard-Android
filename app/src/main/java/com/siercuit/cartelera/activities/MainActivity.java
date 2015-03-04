package com.siercuit.cartelera.activities;

import com.crashlytics.android.Crashlytics;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.siercuit.cartelera.App;
import com.siercuit.cartelera.R;
import com.siercuit.cartelera.adapters.DrawerAdapter;
import com.siercuit.cartelera.utilities.ColumnCalculator;

public class MainActivity extends ActionBarActivity
{
    private static DrawerAdapter drawerAdapter;
    private static DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        ((App) getApplication()).getTracker(App.TrackerName.APP_TRACKER);
        setContentView(R.layout.activity_main);

        App.setActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        if (!ColumnCalculator.isTabletHeight(this) && ColumnCalculator.get(this) > 1) {
            toolbar.setMinimumHeight(166);
        } else if (ColumnCalculator.get(this) > 1) {
            toolbar.setMinimumHeight(116);
        }
        App.buildToolbar(toolbar, this);

        drawerAdapter = new DrawerAdapter(this, App.getNavItems(), getSupportFragmentManager());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ExpandableListView drawerList = (ExpandableListView) findViewById(R.id.left_drawer);

        // Set onGroupClick and onChildClick
        drawerAdapter.setClickEvents(drawerLayout, drawerList);
        View headerView = getLayoutInflater().inflate(R.layout.drawer_header, null);
        drawerList.addHeaderView(headerView);
        drawerList.setAdapter(drawerAdapter);
        // Set fragment for app home
        if (savedInstanceState == null) {
            drawerAdapter.setGroupFragment(0);
        }

        ActionBarDrawerToggle toolbarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,          /* DrawerLayout object */
                toolbar,               /* toolbar */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(toolbarDrawerToggle);
        toolbarDrawerToggle.syncState();
        App.setToolbarDrawer(toolbarDrawerToggle, drawerLayout);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            drawerLayout.closeDrawers();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        } else {
            super.onBackPressed();
        }
    }

}