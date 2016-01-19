package io.vextil.billboard.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.widget.ExpandableListView

class Drawer (context: Context, layout: DrawerLayout, list: ExpandableListView, toolbar: Toolbar) {

    val context = context
    val layout = layout
    val list = list
    val toolbar = toolbar
    var items = arrayListOf<DrawerItem>()
    var headerId = 0

    fun addHeader(id: Int) {
        headerId = id
    }

    fun addItem(title: Int, color: Int, icon: Icon): DrawerItem {
        val item = DrawerItem(title, color, icon)
        items.add(item)
        return item
    }

    fun build(listener: (fragment: Fragment, title: Int, color: Int) -> Unit) {
        var adapter = DrawerAdapter(context, this)
        adapter.onSelect(layout, list, listener)
        list.setAdapter(adapter)
        if (headerId != 0) {
            list.addHeaderView(LayoutInflater.from(context).inflate(headerId, null))
        }

    }

}
