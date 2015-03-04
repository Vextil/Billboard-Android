package com.siercuit.cartelera.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siercuit.cartelera.App;
import com.siercuit.cartelera.POJOs.PreciosPromosPOJO;
import com.siercuit.cartelera.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PreciosPromosAdapter extends BaseAdapter
{
    private Context context;
    private PreciosPromosPOJO.Categorias.Promos[] promos;

    public PreciosPromosAdapter(Context context, PreciosPromosPOJO.Categorias.Promos[] promos)
    {
        this.context = context;
        this.promos = promos;
    }

    @Override
    public Object getItem(int position) { return position; }

    @Override
    public int getCount() { return promos.length; }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View view, ViewGroup container)
    {
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.precios_promos_subitem, container, false);
            holder = new ViewHolder(view);
            holder.promoNombre.setTypeface(App.getRobotoTypeface());
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.promoNombre.setText(promos[position].nombre);
        if (position == (getCount() - 1)) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }
        return view;
    }

    static class ViewHolder
    {
        @InjectView(R.id.promosPromoNombre) TextView promoNombre;
        @InjectView(R.id.divider) View divider;

        public ViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }

}
