package com.siercuit.cartelera.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siercuit.cartelera.App;
import io.vextil.billboard.api.Function;
import io.vextil.billboard.R;
import com.siercuit.cartelera.views.IndicatorView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FuncionHorariosAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private Function.Function.Theatres[] cines;

    public FuncionHorariosAdapter(Context context, Function.Function.Theatres[] cines)
    {
        this.context = context;
        this.cines = cines;
    }

    @Override
    public Object getGroup(int position) { return position; }

    @Override
    public int getGroupCount() { return cines.length; }

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
            view = mInflater.inflate(R.layout.cine_funcion_horarios_item, null);
            holder = new GroupHolder(view);
            holder.horarioCine.setTypeface(App.getRobotoTypeface());
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }
        holder.horarioCine.setText(cines[position].getName());

        if (cines[position].getScreenings() == null) {
            holder.indicator.setVisibility(View.INVISIBLE);
            holder.horarioCine.setTextColor(context.getResources().getColor(R.color.card_text_disabled));
        } else {
            holder.indicator.setVisibility(View.VISIBLE);
            holder.indicator.setState(isExpanded);
            holder.horarioCine.setTextColor(context.getResources().getColor(R.color.card_text));
        }

        return view;
    }

    static class GroupHolder
    {
        @InjectView(R.id.funcionHorarioCine) TextView horarioCine;
        @InjectView(R.id.indicator) IndicatorView indicator;

        public GroupHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public Object getChild(int position, int childPosition) { return childPosition; }

    @Override
    public long getChildId(int position, int childPosition) { return childPosition; }

    @Override
    public int getChildrenCount(int position) {
        if (cines[position].getScreenings() == null) {
            return 0;
        } else {
            return cines[position].getScreenings().length;
        }
    }

    @Override
    public View getChildView(int position, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent)
    {
        final ChildHolder holder;
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.function_screening_day, parent, false);
            holder = new ChildHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        Function.Function.Theatres.Screenings horario = cines[position].getScreenings()[childPosition];
        holder.funcionHoras.removeAllViews();
        holder.funcionDia.setText(horario.getDay());
        holder.funcionDia.setTypeface(App.getRobotoTypeface());
        if (isLastChild) {
            holder.divider.setVisibility(View.INVISIBLE);
        }
        int horasLength = horario.getHours().length;
        for (int i = 0; i < horasLength; ++i) {
            View horaView = layoutInflater.inflate(R.layout.function_screening_hour, null, false);
            LinearLayout layout = (LinearLayout) horaView.findViewById(R.id.layout);
            TextView hora = (TextView) horaView.findViewById(R.id.hora);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                layout.setBackgroundDrawable(App.getColorDrawable());
            } else {
                layout.setBackground(App.getColorDrawable());
            }
            hora.setText(horario.getHours()[i]);
            hora.setTypeface(App.getRobotoTypeface());
            holder.funcionHoras.addView(horaView);
        }
        return view;
    }

    static class ChildHolder
    {
        @InjectView(R.id.funcionDia) TextView funcionDia;
        @InjectView(R.id.funcionHoras) LinearLayout funcionHoras;
        @InjectView(R.id.divider) View divider;

        public ChildHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    @Override
    public boolean hasStableIds() { return false; }

    public void setClickEvents(final ExpandableListView drawerList)
    {
        drawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, final View view, int position, long id) {
                return false;
            }
        });

        drawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View view, int position, int childPosition, long id) {
                return false;

            }
        });
    }

}
