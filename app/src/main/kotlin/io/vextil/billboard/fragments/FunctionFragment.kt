package io.vextil.billboard.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ExpandableListView
import android.widget.TextView
import com.siercuit.cartelera.App
import io.vextil.billboard.api.Function
import io.vextil.billboard.R
import com.siercuit.cartelera.adapters.FuncionHorariosAdapter
import com.siercuit.cartelera.utilities.Animate
import com.siercuit.cartelera.views.ExpandablePanel
import com.squareup.picasso.Picasso

import io.vextil.billboard.ui.RetrofitFragment
import kotlinx.android.synthetic.main.funcion_header.*
import rx.Observable

class FunctionFragment : RetrofitFragment() {

    override fun onGetObservable(): Observable<Function> {
        return App.API().getFunction(id)
    }

    private var id: String = ""
    private var lastExpandedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        id = bundle.getString("id")
        retainInstance = true
    }

    override fun onCreateSuccessView(data: Any): View {
        val inflater = LayoutInflater.from(activity)
        val view = contentView
        val headerView = inflater.inflate(R.layout.funcion_header, null, false)

        val funcion = data.funcion

        funcionDescripcionContainer.setOnExpandListener(object : ExpandablePanel.OnExpandListener {
            override fun onCollapse(handle: View, content: View) {
                funcionDescripcionIndicator.setState(false)
            }

            override fun onExpand(handle: View, content: View) {
                funcionDescripcionIndicator.setState(true)
            }
        })
        funcionDescripcionIndicator.setState(false)

        iconEstreno.setTextColor(App.getColor()!!)
        iconDuracion.setTextColor(App.getColor()!!)
        iconGenero.setTextColor(App.getColor()!!)
        iconClasificacion.setTextColor(App.getColor()!!)
        funcionRating.setTextColor(App.getColor()!!)
        funcionLenguaje.setTextColor(App.getColor()!!)
        funcion3D.setTextColor(App.getColor()!!)

        funcionRatingText.typeface = App.getRobotoTypeface()
        funcionDescripcionTitulo.typeface = App.getRobotoTypeface()
        funcionDescripcion.typeface = App.getRobotoTypeface()
        funcionEstreno.typeface = App.getRobotoTypeface()
        funcionDuracion.typeface = App.getRobotoTypeface()
        funcionGenero.typeface = App.getRobotoTypeface()
        funcionClasificacion.typeface = App.getRobotoTypeface()
        headerText.typeface = App.getRobotoTypeface()
        headerText2.typeface = App.getRobotoTypeface()

        funcionDescripcion.text = funcion.descripcion
        funcionRating.setRating(funcion.rating)
        funcionEstreno.text = funcion.estreno
        funcionPoster.setImageResource(R.drawable.poster_holder_big)
        Picasso.with(activity).load(data.poster_url_grande + funcion.poster
                + data.poster_extension).placeholder(R.drawable.poster_holder_big).into(funcionPoster, object : com.squareup.picasso.Callback.EmptyCallback() {
            override fun onSuccess() {
                App.setColorScheme((funcionPoster.drawable as BitmapDrawable).bitmap) { color -> Animate.textColor(App.getColorNone()!!, color!!, arrayOf<TextView>(funcionRating, funcionLenguaje, funcion3D, iconEstreno, iconDuracion, iconGenero, iconClasificacion)) }
            }
        })
        funcionDuracion.text = funcion.duracion
        funcionGenero.text = funcion.genero
        funcionClasificacion.text = funcion.clasificacion
        funcionRating.setRating(funcion.rating)
        funcionRatingText.setText(funcion.rating + "%")

        val movieHorariosList = view.findViewById(R.id.movieHorarios) as ExpandableListView

        if (funcion.lenguaje == "esp") {
            funcionLenguaje.setText(R.string.fi_esp)
        } else {
            funcionLenguaje.setText(R.string.fi_sub)
        }
        if (funcion.threeD) {
            funcion3D.visibility = View.VISIBLE
            divider2.visibility = View.VISIBLE
        } else {
            funcion3D.visibility = View.INVISIBLE
            divider2.visibility = View.INVISIBLE
        }

        movieHorariosList.addHeaderView(headerView)
        movieHorariosList.setAdapter(FuncionHorariosAdapter(activity, funcion.salas))
        movieHorariosList.setOnGroupExpandListener { groupPosition ->
            if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                movieHorariosList.collapseGroup(lastExpandedPosition)
            }
            lastExpandedPosition = groupPosition
        }

    }

}
