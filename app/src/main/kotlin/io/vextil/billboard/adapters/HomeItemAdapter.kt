package io.vextil.billboard.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import io.vextil.billboard.api.Home
import io.vextil.billboard.api.Poster
import io.vextil.billboard.R
import com.squareup.picasso.Picasso
import io.vextil.billboard.activities.FunctionActivity
import kotlinx.android.synthetic.main.home_item.view.*

class HomeItemAdapter(var context: Context, val data: Home.Categories, val poster: Poster) : RecyclerView.Adapter<HomeItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeItemAdapter.ViewHolder, position: Int) {
        holder.bindData(data.items[position], poster)
    }

    override fun getItemCount() = data.items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(item: Home.Categories.Items, poster: Poster) {
            itemView.name.text = item.name
            itemView.clickableView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val intent = Intent(itemView.context, FunctionActivity::class.java)
                    intent.putExtra("id", item.id)
                    intent.putExtra("name", item.name)
                    itemView.context.startActivity(intent)
                }
                true
            }
            Picasso.with(itemView.context)
                    .load(poster.getBig(item.poster))
                    .placeholder(R.drawable.poster_holder_big)
                    .into(itemView.poster)
            if (item.language.isEmpty()) {
                itemView.subFooterContainer.visibility = View.GONE
            } else {
                itemView.subFooterContainer.visibility = View.VISIBLE
                if (item.language == "esp") {
                    itemView.language.setText(R.string.fi_esp)
                } else {
                    itemView.language.setText(R.string.fi_sub)
                }
                if ((item.DDD)) {
                    itemView.threeD.visibility = View.GONE
                    itemView.divider3D.visibility = View.GONE
                } else {
                    itemView.threeD.visibility = View.VISIBLE
                    itemView.divider3D.visibility = View.VISIBLE
                }
            }
        }

    }
}
