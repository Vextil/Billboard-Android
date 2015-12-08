package io.vextil.billboard.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.TextView

import com.siercuit.cartelera.App
import io.vextil.billboard.api.Home
import com.siercuit.cartelera.interfaces.animationInterface
import com.siercuit.cartelera.utilities.ProgressFragment
import io.vextil.billboard.R
import io.vextil.billboard.adapters.HomeItemAdapter
import kotlinx.android.synthetic.main.home.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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
        var call: Observable<Home> = App.API().getHome()
        call
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribe(object : Subscriber<Home>() {
            override fun onCompleted() {
            }

            override fun onNext(data: Home) {
                setData(data, Home::class.java)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        })
    }

    override fun viewBuilder() {
        for (category in (data as Home).categories) {
            val categoryView = LayoutInflater.from(activity).inflate(R.layout.home_category, scrollViewContainer, false)
            val categoryName = categoryView.findViewById(R.id.header_text) as TextView
            val recyclerView = categoryView.findViewById(R.id.recycler) as RecyclerView
            categoryName.typeface = App.getRobotoTypeface()
            categoryName.text = category.name
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.minimumHeight = 700
            recyclerView.layoutManager = layoutManager
            val adapter = HomeItemAdapter(activity, category, (data as Home).poster)
            recyclerView.adapter = adapter
            scrollViewContainer.addView(categoryView)
        }
    }

    override fun inAnimator() {

    }

    override fun outAnimator(callback: animationInterface) {

    }

}


