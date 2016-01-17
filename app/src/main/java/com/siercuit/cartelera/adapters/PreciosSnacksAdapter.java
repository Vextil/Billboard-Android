package com.siercuit.cartelera.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siercuit.cartelera.App;
import com.siercuit.cartelera.POJOs.PreciosSnacksPOJO;
import com.siercuit.cartelera.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PreciosSnacksAdapter extends ListAsGridAdapter
{
    private Context context;
    private PreciosSnacksPOJO data;

    public PreciosSnacksAdapter(Context context, PreciosSnacksPOJO data)
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
            view = inflater.inflate(R.layout.precios_snacks_grid_item, parent, false);
            holder = new ViewHolder(view);
            holder.snackNombre.setTypeface(App.getRobotoMediumTypeface());
            holder.snackNombre.setTextColor(App.getColor());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                holder.snackPrecio.setBackgroundDrawable(App.getColorDrawable());
            } else {
                holder.snackPrecio.setBackground(App.getColorDrawable());
            }
            holder.snackPrecio.setTypeface(App.getRobotoTypeface());
            holder.snackContenido.setTypeface(App.getRobotoTypeface());
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.snackNombre.setText(data.snacks[position].nombre);
        holder.snackContenido.setText(data.snacks[position].contenido);
        holder.snackPrecio.setText(data.snacks[position].precio);
        Picasso.with(context).load(data.imagen_url + data.snacks[position].imagen + data.imagen_extension)
                .into(holder.snackImagen);

        return view;
    }

    static class ViewHolder
    {
        @InjectView(R.id.snackNombre) TextView snackNombre;
        @InjectView(R.id.snackImagen) ImageView snackImagen;
        @InjectView(R.id.snackContenido) TextView snackContenido;
        @InjectView(R.id.snackPrecio) TextView snackPrecio;

        public ViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public int getItemCount() {
        return data.snacks.length;
    }

    @Override
    public Object getItem(int position) { return position; }

    @Override
    public long getItemId(int position) {
        return position;
    }

}