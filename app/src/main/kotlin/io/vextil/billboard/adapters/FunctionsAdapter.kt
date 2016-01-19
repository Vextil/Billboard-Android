package io.vextil.billboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.siercuit.cartelera.App
import io.vextil.billboard.api.Functions
import io.vextil.billboard.R
import com.squareup.picasso.Picasso

import com.siercuit.cartelera.adapters.ListAsGridAdapter
import kotlinx.android.synthetic.main.functions_grid_item.view.*

class FunctionsAdapter(var context: Context, val data: Functions) : ListAsGridAdapter(context) {

    public override fun getItemView(position: Int, view: View?, parent: ViewGroup): View? {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.functions_grid_item, parent, false)
            holder = ViewHolder(view, context, data)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        holder.bindData(position)

        return view
    }

    internal class ViewHolder(view: View, context: Context, data: Functions) {

        val view  = view
        val context = context
        val data = data

        init {
            view.rating.setTextColor(App.getColor()!!)
            view.language.setTextColor(App.getColor()!!)
            view.DDD.setTextColor(App.getColor()!!)
            view.premiere.setTextColor(App.getColor()!!)
        }

        fun bindData(position: Int) {
            val function = data.functions[position]
            view.name.text = function.name
            view.name.tag = function.id
            view.rating.setRating(function.rating)
            view.ratingText.text = function.rating.toString() + "%"

            if (function.language == "") {
                view.language.visibility = View.INVISIBLE
                view.languageDivider.visibility = View.INVISIBLE
                view.DDD.visibility = View.INVISIBLE
                view.DDDdivider.visibility = View.INVISIBLE

            } else {
                if (function.language == "esp") {
                    view.language.setText(R.string.fi_esp)
                } else {
                    view.language.setText(R.string.fi_sub)
                }
                if (function.DDD) {
                    view.DDD.visibility = View.VISIBLE
                    view.DDDdivider.visibility = View.VISIBLE
                } else {
                    view.DDD.visibility = View.INVISIBLE
                    view.DDDdivider.visibility = View.INVISIBLE
                }
            }

            if (function.premiere == "") {
                view.premiere.visibility = View.GONE
            } else {
                view.premiere.text = function.premiere
                view.premiere.visibility = View.VISIBLE
            }

            Picasso.with(context)
                    .load(data.poster.getSmall(function.poster))
                    .placeholder(R.drawable.poster_holder_small)
                    .into(view.poster)
        }
    }

    override fun getItemCount(): Int {
        return data.functions.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}