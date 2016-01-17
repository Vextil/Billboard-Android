package com.siercuit.cartelera.adapters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.siercuit.cartelera.App;
import com.siercuit.cartelera.mappers.DrawerNavItemMapper;
import com.siercuit.cartelera.R;
import com.siercuit.cartelera.views.IndicatorView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DrawerAdapter extends BaseExpandableListAdapter
{
    public List<DrawerNavItemMapper> navItems;
    private Context context;
    private FragmentManager fragmentManager;

    public DrawerAdapter(Context context, List<DrawerNavItemMapper> navItems,
            FragmentManager fragmentManager)
    {
        this.context = context;
        this.navItems = navItems;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Object getGroup(int position) { return navItems.get(position); }

    @Override
    public int getGroupCount() { return navItems.size(); }

    @Override
    public void onGroupCollapsed(int groupPosition) { super.onGroupCollapsed(groupPosition); }

    @Override
    public void onGroupExpanded(int groupPosition) { super.onGroupExpanded(groupPosition); }

    @Override
    public long getGroupId(int position) { return position; }

    @Override
    public View getGroupView(int position, boolean isExpanded, View view, ViewGroup parent)
    {
        final GroupHolder holder;
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new GroupHolder(view);
            holder.title.setTypeface(App.getRobotoTypeface());
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }

        holder.icon.setTextColor(context.getResources().getColor(navItems.get(position).getColor()));
        holder.icon.setText(navItems.get(position).getIcon());
        holder.title.setText(navItems.get(position).getTitle());
        if (navItems.get(position).hasChild()) {
            holder.indicator.setVisibility(View.VISIBLE);
            holder.indicator.setState(isExpanded);
        } else {
            holder.indicator.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    static class GroupHolder
    {
        View view;
        @InjectView(R.id.icon) TextView icon;
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.indicator) IndicatorView indicator;

        public GroupHolder(View view)
        {
            this.view = view;
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public Object getChild(int position, int childPosition)
    {
        return navItems.get(position).getChildTitle(childPosition);
    }

    @Override
    public long getChildId(int position, int childPosition) { return childPosition; }

    @Override
    public int getChildrenCount(int position) { return navItems.get(position).getChildItemsSize(); }

    @Override
    public View getChildView(int position, int childPosition, boolean isLastChild,
        View view, ViewGroup parent)
    {
        final ChildHolder holder;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.drawer_list_subitem, parent, false);
            holder = new ChildHolder(view);
            holder.title.setTypeface(App.getRobotoTypeface());
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        DrawerNavItemMapper navItem = navItems.get(position);
        holder.icon.setTextColor(context.getResources().getColor(navItem.getColor()));
        holder.icon.setText(navItem.getChildIcon(childPosition));
        holder.title.setText(navItem.getChildTitle(childPosition));
        return view;
    }

    static class ChildHolder
    {
        View view;
        @InjectView(R.id.icon) TextView icon;
        @InjectView(R.id.title) TextView title;

        public ChildHolder(View view)
        {
            this.view = view;
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    @Override
    public boolean hasStableIds() { return false; }

    public void setClickEvents(final DrawerLayout drawerLayout,
                               final ExpandableListView drawerList)
    {
        drawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, final View view, final int position, long id) {
                if (!navItems.get(position).hasChild()) {
                    drawerLayout.closeDrawer(drawerList);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setGroupFragment(position);
                        }
                    }, 250);
                }
                return false;
            }
        });

        drawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View view, final int position, final int childPosition, long id) {
                drawerLayout.closeDrawer(drawerList);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setChildFragment(position, childPosition);
                    }
                }, 200);
                return false;

            }
        });
    }

    public void setGroupFragment(int position)
    {
        if (!navItems.get(position).hasChild()) {
            try {
                Class<?> fragmentClass = Class.forName(context.getPackageName() + '.' + navItems.get(position).getFragment());
                Method method = fragmentClass.getDeclaredMethod("newInstance", Integer.class, String.class );
                Fragment fragment = (Fragment) method.invoke(
                        fragmentClass,
                        context.getResources().getColor(navItems.get(position).getColor()),
                        navItems.get(position).getTitle()
                );
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .addToBackStack(String.valueOf(navItems.get(position).getTitle() + context.getResources().getColor(navItems.get(position).getColor())))
                        .commit();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void setChildFragment(int position, int childPosition)
    {
        try {
            Class<?> fragmentClass = Class.forName(context.getPackageName() + '.' + navItems.get(position)
                    .getChildFragment(childPosition));
            Method method = fragmentClass.getDeclaredMethod("newInstance", Integer.class, String.class );
            Fragment fragment = (Fragment) method.invoke(
                    fragmentClass,
                    context.getResources().getColor(navItems.get(position).getColor()),
                    navItems.get(position).getChildTitle(childPosition)
            );
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(navItems.get(position).getChildTitle(childPosition) + String.valueOf(context.getResources().getColor(navItems.get(position).getColor())))
                    .commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}