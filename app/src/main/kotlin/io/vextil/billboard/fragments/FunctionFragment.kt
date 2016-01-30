package io.vextil.billboard.fragments

import android.os.Bundle
import android.view.View
import io.vextil.billboard.App
import io.vextil.billboard.api.Function
import io.vextil.billboard.adapters.FuncionHorariosAdapter
import io.vextil.billboard.ui.ExpandablePanel
import com.squareup.picasso.Picasso
import io.vextil.billboard.R

import io.vextil.billboard.ui.RxRetrofitFragment
import kotlinx.android.synthetic.main.function.view.*
import kotlinx.android.synthetic.main.function_header.view.*
import rx.Observable

class FunctionFragment : RxRetrofitFragment() {

    private var lastExpandedPosition = -1
    private var functionId: Int = 0

    override fun onGetObservable(): Observable<Function> {
        return App.API().getFunction(functionId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        functionId = arguments.getInt("id");
    }

    override fun onCreateSuccessView(data: Any): View {
        val view = inflate(R.layout.function)
        val headerView = inflate(R.layout.function_header, null)

        val function = (data as Function).function

        headerView.funcionDescripcionContainer.setOnExpandListener(object : ExpandablePanel.OnExpandListener {
            override fun onCollapse(handle: View?, content: View?) {
                headerView.funcionDescripcionIndicator.setState(false)
            }

            override fun onExpand(handle: View?, content: View?) {
                headerView.funcionDescripcionIndicator.setState(true)
            }
        })
        headerView.funcionDescripcionIndicator.setState(false)

        headerView.funcionDescripcion.text = function.description
        headerView.rating.setRating(function.rating)
        headerView.premiere.text = function.premiere
        headerView.poster.setImageResource(R.drawable.poster_holder_big)
        Picasso.with(activity).load(data.poster.getBig(function.poster))
                .placeholder(R.drawable.poster_holder_big)
                .into(headerView.poster)
        headerView.funcionDuracion.text = function.duration
        headerView.funcionGenero.text = function.genre
        headerView.funcionClasificacion.text = function.age_restriction
        headerView.rating.setRating(function.rating)
        headerView.ratingText.text = function.rating.toString() + "%"

        if (function.language == "esp") {
            headerView.language.setText(R.string.fi_esp)
        } else {
            headerView.language.setText(R.string.fi_sub)
        }
        if (function.DDD) {
            headerView.DDD.visibility = View.VISIBLE
            headerView.DDDdivider.visibility = View.VISIBLE
        } else {
            headerView.DDD.visibility = View.INVISIBLE
            headerView.DDDdivider.visibility = View.INVISIBLE
        }

        view.movieHorarios.addHeaderView(headerView)
        view.movieHorarios.setAdapter(FuncionHorariosAdapter(activity, function.theatres))
        view.movieHorarios.setOnGroupExpandListener { groupPosition ->
            if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                view.movieHorarios.collapseGroup(lastExpandedPosition)
            }
            lastExpandedPosition = groupPosition
        }
        return view
    }

}
