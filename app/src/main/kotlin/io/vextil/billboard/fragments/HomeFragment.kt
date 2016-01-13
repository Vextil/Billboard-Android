package io.vextil.billboard.fragments

import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.siercuit.cartelera.App
import io.vextil.billboard.R
import io.vextil.billboard.adapters.HomeItemAdapter
import io.vextil.billboard.api.Home
import io.vextil.billboard.ui.RetrofitFragment
import kotlinx.android.synthetic.main.home.view.*
import kotlinx.android.synthetic.main.home_category.view.*
import rx.Observable

class HomeFragment : RetrofitFragment() {

    override fun onGetObservable(): Observable<Home> {
        return App.API().getHome()
    }

    override fun onCreateSuccessView(data: Any) : View {
        val view = inflate(R.layout.home)
        for (category in (data as Home).categories) {
            val categoryView = inflate(R.layout.home_category, view.scrollViewContainer)
            categoryView.header_text.typeface = App.getRobotoTypeface()
            categoryView.header_text.text = category.name
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            categoryView.recycler.minimumHeight = 700
            categoryView.recycler.layoutManager = layoutManager
            val adapter = HomeItemAdapter(activity, category, data.poster)
            categoryView.recycler.adapter = adapter
            view.scrollViewContainer.addView(categoryView)
        }
        return view
    }

}


