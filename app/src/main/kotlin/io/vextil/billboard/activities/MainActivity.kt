package io.vextil.billboard.activities

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarActivity
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.ExpandableListView

import com.google.android.gms.analytics.GoogleAnalytics
import com.siercuit.cartelera.App
import com.siercuit.cartelera.R
import com.siercuit.cartelera.adapters.DrawerAdapter
import com.siercuit.cartelera.utilities.ColumnCalculator
import com.squareup.otto.Subscribe

class MainActivity : ActionBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).getTracker(App.TrackerName.APP_TRACKER)
        setContentView(R.layout.activity_main)

        App.setActivity(this)

        val toolbar = findViewById(R.id.my_awesome_toolbar) as Toolbar
        if (!ColumnCalculator.isTabletHeight(this) && ColumnCalculator.get(this) > 1) {
            toolbar.minimumHeight = 166
        } else if (ColumnCalculator.get(this) > 1) {
            toolbar.minimumHeight = 116
        }
        App.buildToolbar(toolbar, this)

        drawerAdapter = DrawerAdapter(this, App.getNavItems(), supportFragmentManager)
        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val drawerList = findViewById(R.id.left_drawer) as ExpandableListView

        // Set onGroupClick and onChildClick
        drawerAdapter!!.setClickEvents(drawerLayout, drawerList)
        val headerView = layoutInflater.inflate(R.layout.drawer_header, null)
        drawerList.addHeaderView(headerView)
        drawerList.setAdapter(drawerAdapter)
        // Set fragment for app home
        if (savedInstanceState == null) {
            drawerAdapter!!.setGroupFragment(0)
        }

        val toolbarDrawerToggle = object : ActionBarDrawerToggle(
                this, /* host Activity */
                drawerLayout, /* DrawerLayout object */
                toolbar, /* toolbar */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */) {

            override fun onDrawerClosed(view: View?) {
                super.onDrawerClosed(view)
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawerLayout!!.setDrawerListener(toolbarDrawerToggle)
        toolbarDrawerToggle.syncState()
        App.setToolbarDrawer(toolbarDrawerToggle, drawerLayout)
    }

    override fun onStart() {
        super.onStart()
        GoogleAnalytics.getInstance(this).reportActivityStart(this)
    }

    override fun onStop() {
        super.onStop()
        GoogleAnalytics.getInstance(this).reportActivityStop(this)
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout!!.closeDrawers()
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    @Subscribe
    fun stuff() {

    }

    companion object {
        private var drawerAdapter: DrawerAdapter? = null
        private var drawerLayout: DrawerLayout? = null
    }

}