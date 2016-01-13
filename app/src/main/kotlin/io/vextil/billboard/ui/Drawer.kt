package io.vextil.billboard.ui

import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar

class Drawer {

    var layout: DrawerLayout? = null
    val toolbar: Toolbar? = null
    var items = arrayListOf<DrawerItem>()

    fun setLayout(layout: DrawerLayout): Drawer {
        return this
    }

    fun setToolbar(toolbar: Toolbar): Drawer {
        return this
    }

    fun addItem(title: Int, color: Int, icon: Icon): DrawerItem {
        val item = DrawerItem(title, color, icon)
        items.add(item)
        return item
    }

    fun build() {

    }

}
