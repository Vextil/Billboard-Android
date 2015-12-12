package io.vextil.billboard.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.siercuit.cartelera.App
import io.vextil.billboard.R
import io.vextil.billboard.adapters.HomeItemAdapter
import io.vextil.billboard.api.Home
import io.vextil.billboard.ui.RetrofitFragment
import kotlinx.android.synthetic.main.home.view.*
import rx.Observable


class HomeFragment() : RetrofitFragment() {


    constructor(color: Int, title: String) : this() {

    }

    override fun onGetObservable(): Observable<Home> {
        return App.API().getHome()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateSuccessView(data: Any) : View {
        val view = inflate(R.layout.home)
        for (category in (data as Home).categories) {
            val categoryView = inflate(R.layout.home_category, view.scrollViewContainer)
            val categoryName = categoryView.findViewById(R.id.header_text) as TextView
            val recyclerView = categoryView.findViewById(R.id.recycler) as RecyclerView
            categoryName.typeface = App.getRobotoTypeface()
            categoryName.text = category.name
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.minimumHeight = 700
            recyclerView.layoutManager = layoutManager
            val adapter = HomeItemAdapter(activity, category, data.poster)
            recyclerView.adapter = adapter
            view.scrollViewContainer.addView(categoryView)
        }
        return view
    }

}


