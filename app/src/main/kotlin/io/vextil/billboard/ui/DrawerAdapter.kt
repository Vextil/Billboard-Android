package io.vextil.billboard.ui

import android.content.Context
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import com.siercuit.cartelera.views.IndicatorView
import io.vextil.billboard.R

class DrawerAdapter(private val context: Context, var drawer: Drawer,
                    private val fragmentManager: FragmentManager) : BaseExpandableListAdapter() {

    override fun getGroup(position: Int): Any {
        return drawer.items[position]
    }

    override fun getGroupCount(): Int {
        return drawer.items.size
    }

    override fun onGroupCollapsed(groupPosition: Int) {
        super.onGroupCollapsed(groupPosition)
    }

    override fun onGroupExpanded(groupPosition: Int) {
        super.onGroupExpanded(groupPosition)
    }

    override fun getGroupId(position: Int): Long {
        return position.toLong()
    }

    override fun getGroupView(position: Int, isExpanded: Boolean, view: View?, parent: ViewGroup): View {
        val holder: GroupHolder
        if (view == null) {
            holder = GroupHolder(LayoutInflater.from(context).inflate(R.layout.drawer_list_item, parent, false))
        } else {
            holder = view.tag as GroupHolder
        }

        holder.icon.setTextColor(context.resources.getColor(drawer.items[position].color))
        holder.icon.setText(Icon.getCharacter(drawer.items[position].icon))
        holder.title.setText(drawer.items[position].title)
        if (drawer.items[position].hasChild()) {
            holder.indicator.visibility = View.VISIBLE
            holder.indicator.setState(isExpanded)
        } else {
            holder.indicator.visibility = View.INVISIBLE
        }

        return holder.view
    }

    internal class GroupHolder(var view: View) {

        var icon: TextView
        var title: TextView
        var indicator: IndicatorView

        init {
            view.tag = this
            icon = view.findViewById(R.id.icon) as TextView
            title = view.findViewById(R.id.title) as TextView
            indicator = view.findViewById(R.id.indicator) as IndicatorView
        }
    }

    override fun getChild(position: Int, childPosition: Int): Any {
        return drawer.items[position].children[childPosition]
    }

    override fun getChildId(position: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildrenCount(position: Int): Int {
        return drawer.items[position].children.size
    }

    override fun getChildView(position: Int, childPosition: Int, isLastChild: Boolean,
                              view: View?, parent: ViewGroup): View {
        val holder: ChildHolder
        if (view == null) {
            holder = ChildHolder(LayoutInflater.from(context).inflate(R.layout.drawer_list_subitem, parent, false))
        } else {
            holder = view.tag as ChildHolder
        }
        val navItem = drawer.items[position]
        holder.icon.setTextColor(context.resources.getColor(navItem.color))
        holder.icon.setText(Icon.getCharacter(navItem.children[childPosition].icon))
        holder.title.setText(navItem.children[childPosition].title)

        return holder.view
    }

    internal class ChildHolder(var view: View) {

        var icon: TextView
        var title: TextView

        init {
            view.tag = this
            icon = view.findViewById(R.id.icon) as TextView
            title = view.findViewById(R.id.title) as TextView
        }
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    fun setClickEvents(drawerLayout: DrawerLayout,
                       drawerList: ExpandableListView) {
        drawerList.setOnGroupClickListener { parent, view, position, id ->
            if (!drawer.items[position].hasChild()) {
                drawerLayout.closeDrawer(drawerList)
                Handler().postDelayed({ setGroupFragment(position) }, 250)
            }
            false
        }

        drawerList.setOnChildClickListener { parent, view, position, childPosition, id ->
            drawerLayout.closeDrawer(drawerList)
            Handler().postDelayed({
                try {
                    setChildFragment(position, childPosition)
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                }
            }, 200)
            false
        }
    }

    fun setGroupFragment(position: Int) {
        if (!drawer.items[position].hasChild()) {
            @Suppress("UNCHECKED_CAST")
            val fragment = (drawer.items[position].fragment as () -> Fragment)()
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment)
                    .addToBackStack(drawer.items[position].title.toString()).commit()

        }
    }

    fun setChildFragment(position: Int, childPosition: Int) {
    }

}