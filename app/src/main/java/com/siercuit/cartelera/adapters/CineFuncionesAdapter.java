package com.siercuit.cartelera.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siercuit.cartelera.App;
import com.siercuit.cartelera.POJOs.FuncionesPOJO;
import com.siercuit.cartelera.R;
import com.siercuit.cartelera.views.FontIconView;
import com.siercuit.cartelera.views.SmileyRatingView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CineFuncionesAdapter extends ListAsGridAdapter
{
    private Context context;
    private FuncionesPOJO data;

    public CineFuncionesAdapter(Context context, FuncionesPOJO data)
    {
        super(context);
        this.context = context;
        this.data = data;
    }

    public View getItemView(int position, View view, ViewGroup parent)
    {
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.cine_funcion_grid_item, parent, false);

            holder = new ViewHolder(view);

            holder.funcionRating.setTextColor(App.getColor());
            holder.funcionLenguaje.setTextColor(App.getColor());
            holder.funcion3D.setTextColor(App.getColor());
            holder.funcionEstreno.setTextColor(App.getColor());
            holder.funcionNombre.setTypeface(App.getRobotoTypeface());
            holder.funcionRatingText.setTypeface(App.getRobotoTypeface());
            holder.funcionEstreno.setTypeface(App.getRobotoTypeface());

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        FuncionesPOJO.Funciones funcion = data.funciones[position];
        holder.funcionNombre.setText(funcion.nombre);
        holder.funcionNombre.setTag(String.valueOf(funcion.id));
        holder.funcionRating.setRating(funcion.rating);
        holder.funcionRatingText.setText(funcion.rating + "%");

        if (funcion.lenguaje.equals("esp")) {
            holder.funcionLenguaje.setText(R.string.fi_esp);
        } else {
            holder.funcionLenguaje.setText(R.string.fi_sub);
        }
        if (funcion.threeD) {
            holder.funcion3D.setVisibility(View.VISIBLE);
            holder.divider2.setVisibility(View.VISIBLE);
        } else {
            holder.funcion3D.setVisibility(View.INVISIBLE);
            holder.divider2.setVisibility(View.INVISIBLE);
        }

        if (funcion.estreno == null) {
            holder.funcionEstreno.setVisibility(View.GONE);
        } else {
            holder.funcionEstreno.setText(funcion.estreno);
            holder.funcionEstreno.setVisibility(View.VISIBLE);
        }

        Picasso.with(context).load(data.poster_url + funcion.poster + data.poster_extension)
                .placeholder(R.drawable.poster_holder_small)
                .into(holder.funcionPoster);

        return view;
    }

    static class ViewHolder
    {
        @InjectView(R.id.funcionNombre) TextView funcionNombre;
        @InjectView(R.id.funcionRating) SmileyRatingView funcionRating;
        @InjectView(R.id.funcionRatingText) TextView funcionRatingText;
        @InjectView(R.id.funcionLenguaje) FontIconView funcionLenguaje;
        @InjectView(R.id.divider2) View divider2;
        @InjectView(R.id.funcion3D) FontIconView funcion3D;
        @InjectView(R.id.funcionEstreno) TextView funcionEstreno;
        @InjectView(R.id.funcionPoster) ImageView funcionPoster;

        public ViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public int getItemCount() {
        return data.funciones.length;
    }

    @Override
    public Object getItem(int position) { return position; }

    @Override
    public long getItemId(int position) {
        return position;
    }

}