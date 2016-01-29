package io.vextil.billboard.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.vextil.billboard.api.Functions
import io.vextil.billboard.R
import com.squareup.picasso.Picasso
import io.vextil.billboard.api.Poster

import kotlinx.android.synthetic.main.functions_grid_item.view.*

class FunctionsAdapter(var context: Context, val data: Functions,
                       val itemClick: (String, Int) -> Unit) : RecyclerView.Adapter<FunctionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.functions_grid_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data.functions[position], data.poster)
    }

    override fun getItemCount() = data.functions.size

    class ViewHolder(view: View, val itemClick: (String, Int) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindData(function: Functions.Functions, poster: Poster) {
            itemView.name.text = function.name
            itemView.name.tag = function.id
            itemView.rating.setRating(function.rating)
            itemView.ratingText.text = function.rating.toString() + "%"

            if (function.language.isEmpty()) {
                itemView.language.visibility = View.INVISIBLE
                itemView.languageDivider.visibility = View.INVISIBLE
                itemView.DDD.visibility = View.INVISIBLE
                itemView.DDDdivider.visibility = View.INVISIBLE
            } else {
                if (function.language == "esp") {
                    itemView.language.setText(R.string.fi_esp)
                } else {
                    itemView.language.setText(R.string.fi_sub)
                }
                if (function.DDD) {
                    itemView.DDD.visibility = View.VISIBLE
                    itemView.DDDdivider.visibility = View.VISIBLE
                } else {
                    itemView.DDD.visibility = View.INVISIBLE
                    itemView.DDDdivider.visibility = View.INVISIBLE
                }
            }

            if (function.premiere.isEmpty()) {
                itemView.premiere.visibility = View.GONE
            } else {
                itemView.premiere.text = function.premiere
                itemView.premiere.visibility = View.VISIBLE
            }

            itemView.setOnClickListener { itemClick(function.name, function.id) }

            Picasso.with(itemView.context)
                    .load(poster.getSmall(function.poster))
                    .placeholder(R.drawable.poster_holder_small)
                    .into(itemView.poster)
        }
    }


}