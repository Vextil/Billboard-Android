package com.siercuit.cartelera.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siercuit.cartelera.App;
import io.vextil.billboard.services.PreciosSnacksService;
import com.siercuit.cartelera.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PreciosSnacksAdapter extends ListAsGridAdapter
{
    private Context context;
    private PreciosSnacksService data;

    public PreciosSnacksAdapter(Context context, PreciosSnacksService data)
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

        holder.snackNombre.setText(data.getSnacks()[position].getNombre());
        holder.snackContenido.setText(data.getSnacks()[position].getContenido());
        holder.snackPrecio.setText(data.getSnacks()[position].getPrecio());
        Picasso.with(context).load(data.getImagen_url() + data.getSnacks()[position].getImagen() + data.getImagen_extension())
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
        return data.getSnacks().length;
    }

    @Override
    public Object getItem(int position) { return position; }

    @Override
    public long getItemId(int position) {
        return position;
    }

}