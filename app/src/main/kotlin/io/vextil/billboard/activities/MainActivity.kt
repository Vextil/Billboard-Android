package io.vextil.billboard.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View

import io.vextil.billboard.R
import com.siercuit.cartelera.fragments.*
import io.vextil.billboard.fragments.*
import io.vextil.billboard.ui.Drawer
import io.vextil.billboard.ui.Icon
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_awesome_toolbar)

        val drawer = Drawer(this, drawer_layout, left_drawer, my_awesome_toolbar)

        drawer.addHeader(R.layout.drawer_header);

        // Home
        drawer.addItem(R.string.drawer_home, R.color.cartelera_blue, Icon.HOUSE)
                .fragment(::HomeFragment)
        // Cinema
        drawer.addItem(R.string.drawer_cinema, R.color.cartelera_purple, Icon.MOVIE)
            .addChild(R.string.drawer_billboard, Icon.SIGN).fragment(::CinemaFragment)
            .addChild(R.string.drawer_premieres, Icon.STAR).fragment(::CinemaPremiereFragment)
        // Kids
        drawer.addItem(R.string.drawer_kids, R.color.cartelera_yellow, Icon.BABY)
            .addChild(R.string.drawer_billboard, Icon.SIGN).fragment(::KidsFragment)
            .addChild(R.string.drawer_premieres, Icon.STAR).fragment(::KidsPremiereFragment)
        // Theatre
        drawer.addItem(R.string.drawer_theatre, R.color.cartelera_orange, Icon.MASKS)
            .addChild(R.string.drawer_billboard, Icon.SIGN).fragment(::TheatreFragment)
            .addChild(R.string.drawer_premieres, Icon.STAR).fragment(::TheatrePremiereFragment)
        // Prices
        drawer.addItem(R.string.drawer_prices, R.color.cartelera_green, Icon.PIG)
            .addChild(R.string.drawer_tickets, Icon.TICKET).fragment(::PreciosEntradasFragment)
            .addChild(R.string.drawer_snacks, Icon.FOOD).fragment(::PreciosSnacksFragment)
            .addChild(R.string.drawer_offers, Icon.GIFT).fragment(::PreciosPromosFragment)

        drawer.build() { fragment, title, color ->
            setTitle(title)
            my_awesome_toolbar.setBackgroundColor(ContextCompat.getColor(this, color))
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(title.toString()).commit()
        }

        val toolbarDrawerToggle = object : ActionBarDrawerToggle(
                this, /* host Activity */
                drawer_layout, /* DrawerLayout object */
                my_awesome_toolbar, /* toolbar */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */) {

            override fun onDrawerClosed(view: View?) {
                super.onDrawerClosed(view)
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawer_layout.setDrawerListener(toolbarDrawerToggle)
        toolbarDrawerToggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawers()
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}