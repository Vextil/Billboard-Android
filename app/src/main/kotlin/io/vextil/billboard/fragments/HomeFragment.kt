package io.vextil.billboard.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.TextView

import com.siercuit.cartelera.App
import io.vextil.billboard.POJOs.HomePOJO
import com.siercuit.cartelera.R
import com.siercuit.cartelera.interfaces.animationInterface
import com.siercuit.cartelera.utilities.ProgressFragment
import io.vextil.billboard.adapters.HomeItemAdapter
import kotlinx.android.synthetic.main.home.*

import retrofit.Callback
import retrofit.RetrofitError

class HomeFragment() : ProgressFragment() {

    constructor(color: Int, title: String) : this() {
        setFragmentArguments(color, title, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setContentView(R.layout.home)
    }

    override fun dataFetcher() {
        if (!isPaused) {
            App.API().getHome(object : Callback<HomePOJO> {
                override fun success(responsePOJO: HomePOJO, response: retrofit.client.Response) {
                    setData(responsePOJO, HomePOJO::class.java)
                }

                override fun failure(retrofitError: RetrofitError) {
                    isContentEmpty = true
                    setContentShown(true)
                }
            })
        }
    }

    override fun viewBuilder() {
        for (category in (data as HomePOJO).categories) {
            val categoryView = LayoutInflater.from(activity).inflate(R.layout.home_category, scrollViewContainer, false)
            val categoryName = categoryView.findViewById(R.id.header_text) as TextView
            val recyclerView = categoryView.findViewById(R.id.recycler) as RecyclerView
            categoryName.typeface = App.getRobotoTypeface()
            categoryName.text = category.name
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.minimumHeight = 700
            recyclerView.layoutManager = layoutManager
            val adapter = HomeItemAdapter(activity, category, (data as HomePOJO).poster)
            recyclerView.adapter = adapter
            scrollViewContainer.addView(categoryView)
        }
    }

    override fun inAnimator() {

    }

    override fun outAnimator(callback: animationInterface) {

    }

}


