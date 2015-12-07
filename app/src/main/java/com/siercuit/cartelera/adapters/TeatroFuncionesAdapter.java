package com.siercuit.cartelera.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siercuit.cartelera.App;
import io.vextil.billboard.services.FuncionesService;
import com.siercuit.cartelera.R;
import com.siercuit.cartelera.views.SmileyRatingView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TeatroFuncionesAdapter extends ListAsGridAdapter
{
    private Context context;
    private FuncionesService data;

    public TeatroFuncionesAdapter(Context context, FuncionesService data)
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
            view = inflater.inflate(R.layout.teatro_funcion_grid_item, parent, false);

            holder = new ViewHolder(view);

            holder.funcionRating.setTextColor(App.getColor());
            holder.funcionEstreno.setTextColor(App.getColor());
            holder.funcionNombre.setTypeface(App.getRobotoTypeface());
            holder.funcionRatingText.setTypeface(App.getRobotoTypeface());
            holder.funcionEstreno.setTypeface(App.getRobotoTypeface());

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        FuncionesService.Funciones funcion = data.getFunciones()[position];
        holder.funcionNombre.setText(funcion.getNombre());
        holder.funcionNombre.setTag(String.valueOf(funcion.getId()));
        holder.funcionRating.setRating(funcion.getRating());
        holder.funcionRatingText.setText(funcion.getRating() + "%");
        Picasso.with(context).load(data.getPoster_url() + funcion.getPoster() + data.getPoster_extension())
                .placeholder(R.drawable.poster_holder_small)
                .into(holder.funcionPoster);

        if (funcion.getEstreno() == null) {
            holder.funcionEstreno.setVisibility(View.INVISIBLE);
        } else {
            holder.funcionEstreno.setText(funcion.getEstreno());
            holder.funcionEstreno.setVisibility(View.VISIBLE);
        }

        return view;
    }

    static class ViewHolder
    {
        @InjectView(R.id.funcionNombre) TextView funcionNombre;
        @InjectView(R.id.funcionRating) SmileyRatingView funcionRating;
        @InjectView(R.id.funcionRatingText) TextView funcionRatingText;
        @InjectView(R.id.funcionEstreno) TextView funcionEstreno;
        @InjectView(R.id.funcionPoster) ImageView funcionPoster;

        public ViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public int getItemCount() {
        return data.getFunciones().length;
    }

    @Override
    public Object getItem(int position) { return position; }

    @Override
    public long getItemId(int position) {
        return position;
    }

}