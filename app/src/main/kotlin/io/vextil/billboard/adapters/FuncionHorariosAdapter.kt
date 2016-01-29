package io.vextil.billboard.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView

import io.vextil.billboard.api.Function
import io.vextil.billboard.R
import io.vextil.billboard.ui.IndicatorView

class FuncionHorariosAdapter(private val context: Context, private val theatres: Array<Function.Function.Theatres>) : BaseExpandableListAdapter() {

    override fun getGroup(position: Int): Any {
        return position
    }

    override fun getGroupCount(): Int {
        return theatres.size
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
        var view = view
        val holder: GroupHolder
        if (view == null) {
            val mInflater = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = mInflater.inflate(R.layout.cine_funcion_horarios_item, null)
            holder = GroupHolder(view)
            view!!.tag = holder
        } else {
            holder = view.tag as GroupHolder
        }
        holder.horarioCine.text = theatres[position].name

        if (theatres[position].screenings.isEmpty()) {
            holder.indicator.visibility = View.INVISIBLE
            holder.horarioCine.setTextColor(context.resources.getColor(R.color.card_text_disabled))
        } else {
            holder.indicator.visibility = View.VISIBLE
            holder.indicator.setState(isExpanded)
            holder.horarioCine.setTextColor(context.resources.getColor(R.color.card_text))
        }

        return view
    }

    internal class GroupHolder(view: View) {
        val horarioCine = view.findViewById(R.id.funcionHorarioCine) as TextView
        val indicator = view.findViewById(R.id.indicator) as IndicatorView
    }

    override fun getChild(position: Int, childPosition: Int): Any {
        return childPosition
    }

    override fun getChildId(position: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildrenCount(position: Int): Int {
        if (theatres[position].screenings.isEmpty()) {
            return 0
        } else {
            return theatres[position].screenings.size
        }
    }

    override fun getChildView(position: Int, childPosition: Int, isLastChild: Boolean,
                              view: View?, parent: ViewGroup): View {
        var view = view
        val holder: ChildHolder
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (view == null) {
            view = layoutInflater.inflate(R.layout.function_screening_day, parent, false)
            holder = ChildHolder(view)
            view!!.tag = holder
        } else {
            holder = view.tag as ChildHolder
        }
        val horario = theatres[position].screenings[childPosition]
        holder.funcionHoras.removeAllViews()
        holder.funcionDia.text = horario.day
        if (isLastChild) {
            holder.divider.visibility = View.INVISIBLE
        }
        val horasLength = horario.hours.size
        for (i in 0..horasLength - 1) {
            val horaView = layoutInflater.inflate(R.layout.function_screening_hour, null, false)
            val hora = horaView.findViewById(R.id.hora) as TextView
            hora.text = horario.hours[i]
            holder.funcionHoras.addView(horaView)
        }
        return view
    }

    internal class ChildHolder(view: View) {
        val funcionDia = view.findViewById(R.id.funcionDia) as TextView
        val funcionHoras = view.findViewById(R.id.funcionHoras) as LinearLayout
        val divider = view.findViewById(R.id.divider)
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

}
