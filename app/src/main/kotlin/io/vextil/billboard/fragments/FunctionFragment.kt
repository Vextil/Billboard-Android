package io.vextil.billboard.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.siercuit.cartelera.App
import io.vextil.billboard.api.Function
import io.vextil.billboard.R
import com.siercuit.cartelera.adapters.FuncionHorariosAdapter
import com.siercuit.cartelera.utilities.Animate
import com.siercuit.cartelera.views.ExpandablePanel
import com.squareup.picasso.Picasso

import io.vextil.billboard.ui.RetrofitFragment
import kotlinx.android.synthetic.main.function.view.*
import kotlinx.android.synthetic.main.function_header.view.*
import rx.Observable

class FunctionFragment : RetrofitFragment() {

    private var lastExpandedPosition = -1
    private var id : String = ""

    fun setId(id: String) {
        this.id = id
    }

    override fun onGetObservable(): Observable<Function> {
        return App.API().getFunction(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateSuccessView(data: Any): View {
        val view = inflate(R.layout.function)
        val headerView = inflate(R.layout.function_header, null)

        val function = (data as Function).function

        headerView.funcionDescripcionContainer.setOnExpandListener(object : ExpandablePanel.OnExpandListener {
            override fun onCollapse(handle: View, content: View) {
                headerView.funcionDescripcionIndicator.setState(false)
            }

            override fun onExpand(handle: View, content: View) {
                headerView.funcionDescripcionIndicator.setState(true)
            }
        })
        headerView.funcionDescripcionIndicator.setState(false)

        headerView.funcionDescripcion.text = function.description
        headerView.funcionRating.setRating(function.rating)
        headerView.funcionEstreno.text = function.premiere
        headerView.funcionPoster.setImageResource(R.drawable.poster_holder_big)
        Picasso.with(activity).load(data.poster.getBig(function.poster))
                .placeholder(R.drawable.poster_holder_big)
                .into(headerView.funcionPoster, object : com.squareup.picasso.Callback.EmptyCallback() {
            override fun onSuccess() {
                App.setColorScheme((headerView.funcionPoster.drawable as BitmapDrawable).bitmap) {
                    color -> Animate.textColor(App.getColorNone()!!, color!!, arrayOf<TextView>(
                        headerView.funcionRating,
                        headerView.funcionLenguaje,
                        headerView.funcion3D,
                        headerView.iconEstreno,
                        headerView.iconDuracion,
                        headerView.iconGenero,
                        headerView.iconClasificacion
                ))}
            }
        })
        headerView.funcionDuracion.text = function.duration
        headerView.funcionGenero.text = function.genre
        headerView.funcionClasificacion.text = function.age_restriction
        headerView.funcionRating.setRating(function.rating)
        headerView.funcionRatingText.text = function.rating.toString() + "%"

        if (function.language == "esp") {
            headerView.funcionLenguaje.setText(R.string.fi_esp)
        } else {
            headerView.funcionLenguaje.setText(R.string.fi_sub)
        }
        if (function.DDD) {
            headerView.funcion3D.visibility = View.VISIBLE
            headerView.divider2.visibility = View.VISIBLE
        } else {
            headerView.funcion3D.visibility = View.INVISIBLE
            headerView.divider2.visibility = View.INVISIBLE
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
