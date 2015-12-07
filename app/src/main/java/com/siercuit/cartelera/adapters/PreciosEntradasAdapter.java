package com.siercuit.cartelera.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siercuit.cartelera.App;
import io.vextil.billboard.services.PreciosEntradasService;
import com.siercuit.cartelera.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PreciosEntradasAdapter extends ListAsGridAdapter
{
    private Context context;
    private PreciosEntradasService data;

    public PreciosEntradasAdapter(Context context, PreciosEntradasService data)
    {
        super(context);
        this.context = context;
        this.data = data;
    }

    public View getItemView(int position, View view, ViewGroup parent)
    {
        final ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null) {
            view = inflater.inflate(R.layout.precios_entradas_grid_item, parent, false);
            holder = new ViewHolder(view);
            holder.nombre.setTypeface(App.getRobotoMediumTypeface());
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.opcionesList.removeAllViews();
        PreciosEntradasService.Precios categoria = data.getPrecios()[position];
        holder.nombre.setText(categoria.getNombre().toUpperCase());
        holder.nombre.setTextColor(App.getColor());
        PreciosEntradasService.Precios.Opciones[] opciones = categoria.getOpciones();
        for (PreciosEntradasService.Precios.Opciones opcion : opciones) {
            View opcionView = inflater.inflate(R.layout.precios_entradas_opciones_list_item, null, false);
            TextView opcionNombre = (TextView) opcionView.findViewById(R.id.nombre);
            opcionNombre.setTypeface(App.getRobotoTypeface());
            opcionNombre.setText(opcion.getNombre());
            LinearLayout horariosList = (LinearLayout) opcionView.findViewById(R.id.horariosList);
            if (opcion.getHorarios() != null) {
                PreciosEntradasService.Precios.Opciones.Horarios[] horarios = opcion.getHorarios();
                int horariosLength = horarios.length;
                for (int ii = 0; ii < horariosLength; ++ii) {
                    PreciosEntradasService.Precios.Opciones.Horarios horario = opcion.getHorarios()[ii];
                    View horarioView = inflater.inflate(R.layout.precios_entradas_horarios_list_item, null, false);
                    TextView nombre = (TextView) horarioView.findViewById(R.id.nombre);
                    nombre.setTypeface(App.getRobotoTypeface());
                    nombre.setText(horario.getNombre());
                    TextView nombreSub = (TextView) horarioView.findViewById(R.id.nombreSub);
                    nombreSub.setTypeface(App.getRobotoTypeface());
                    if (horario.getNombre_sub() != null) {
                        nombreSub.setText(horario.getNombre_sub());
                        nombreSub.setVisibility(View.VISIBLE);
                    } else {
                        nombreSub.setVisibility(View.GONE);
                    }
                    if ((ii + 1) == horariosLength) {
                        horarioView.findViewById(R.id.divider).setVisibility(View.GONE);
                    }
                    TextView precio = (TextView) horarioView.findViewById(R.id.precio);
                    precio.setTypeface(App.getRobotoTypeface());
                    precio.setText(horario.getPrecio());
                    precio.setTextColor(App.getColor());
                    horariosList.addView(horarioView);
                }
            }
            holder.opcionesList.addView(opcionView);
        }

        return view;
    }

    static class ViewHolder
    {
        @InjectView(R.id.nombre) TextView nombre;
        @InjectView(R.id.opcionesList) LinearLayout opcionesList;

        public ViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public int getItemCount() { return data.getPrecios().length; }

    @Override
    public Object getItem(int position) { return position; }

    @Override
    public long getItemId(int position) {
        return position;
    }

}