package io.vextil.billboard.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.siercuit.cartelera.App
import io.vextil.billboard.POJOs.HomePOJO
import io.vextil.billboard.POJOs.PosterPOJO
import com.siercuit.cartelera.R
import com.squareup.picasso.Picasso
import io.vextil.billboard.activities.ExpandableActivity
import kotlinx.android.synthetic.main.home_item.view.*

class HomeItemAdapter(var context: Context, var data: HomePOJO.Categories, var posterData: PosterPOJO) : RecyclerView.Adapter<HomeItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeItemAdapter.ViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.items.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.name.typeface = App.getRobotoTypeface()
            itemView.language.setTextColor(App.getColor())
            itemView.threeD.setTextColor(App.getColor())
        }

        fun bindData(position: Int) {
            val item = data.items[position]
            itemView.name.text = item.name
            itemView.clickableView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val intent = Intent(context, ExpandableActivity::class.java)
                    intent.putExtra("id", item.id)
                    intent.putExtra("name", item.name)
                    context.startActivity(intent)
                }
                true
            }
            Picasso.with(context)
                    .load(posterData.getBig(item.poster))
                    .placeholder(R.drawable.poster_holder_big)
                    .into(itemView.poster)
            if (item.language == "") {
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
