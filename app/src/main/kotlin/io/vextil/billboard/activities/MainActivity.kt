package io.vextil.billboard.activities

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.ExpandableListView

import com.google.android.gms.analytics.GoogleAnalytics
import com.siercuit.cartelera.App
import io.vextil.billboard.R
import com.siercuit.cartelera.adapters.DrawerAdapter
import com.siercuit.cartelera.fragments.*
import io.vextil.billboard.fragments.HomeFragment
import io.vextil.billboard.ui.Drawer
import io.vextil.billboard.ui.Icon
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var drawerLayout: DrawerLayout by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).getTracker(App.TrackerName.APP_TRACKER)
        setContentView(R.layout.activity_main)

        App.setActivity(this)

        val toolbar = findViewById(R.id.my_awesome_toolbar) as Toolbar
        App.buildToolbar(toolbar, this)

        val drawerAdapter = DrawerAdapter(this, App.getNavItems(), supportFragmentManager)
        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout

        val drawer = Drawer()
                .setLayout(drawerLayout)
                .setToolbar(toolbar)

        drawer.addItem(R.string.drawer_home, R.color.cartelera_blue, Icon.HOUSE)
                .fragment(HomeFragment::class)

        drawer.addItem(R.string.drawer_cinema, R.color.cartelera_purple, Icon.MOVIE)
            .addChild(R.string.drawer_billboard, Icon.SIGN).fragment(CineCarteleraFragment::class)
            .addChild(R.string.drawer_premieres, Icon.STAR).fragment(CineEstrenosFragment::class)

        drawer.addItem(R.string.drawer_kids, R.color.cartelera_yellow, Icon.BABY)
            .addChild(R.string.drawer_billboard, Icon.SIGN).fragment(InfantilesCarteleraFragment::class)
            .addChild(R.string.drawer_premieres, Icon.STAR).fragment(InfantilesEstrenosFragment::class)

        drawer.addItem(R.string.drawer_theatre, R.color.cartelera_orange, Icon.MASKS)
            .addChild(R.string.drawer_billboard, Icon.SIGN).fragment(TeatroCarteleraFragment::class)
            .addChild(R.string.drawer_premieres, Icon.STAR).fragment(TeatroEstrenosFragment::class)

        drawer.addItem(R.string.drawer_prices, R.color.cartelera_green, Icon.PIG)
            .addChild(R.string.drawer_tickets, Icon.TICKET).fragment(PreciosEntradasFragment::class)
            .addChild(R.string.drawer_snacks, Icon.FOOD).fragment(PreciosSnacksFragment::class)
            .addChild(R.string.drawer_offers, Icon.GIFT).fragment(PreciosPromosFragment::class)

        drawer.build();


        val drawerList = findViewById(R.id.left_drawer) as ExpandableListView

        // Set onGroupClick and onChildClick
        drawerAdapter.setClickEvents(drawerLayout, drawerList)
        val headerView = layoutInflater.inflate(R.layout.drawer_header, null)
        drawerList.addHeaderView(headerView)
        drawerList.setAdapter(drawerAdapter)
        // Set fragment for app home
        if (savedInstanceState == null) {
            drawerAdapter.setGroupFragment(0)
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
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers()
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}