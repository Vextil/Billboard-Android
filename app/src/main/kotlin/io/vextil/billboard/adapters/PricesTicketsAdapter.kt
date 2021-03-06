package io.vextil.billboard.adapters

class PricesTicketsAdapter {

}

/*
public class PreciosEntradasAdapter extends ListAsGridAdapter
{
    private Context context;
    private PreciosEntradas data;

    public PreciosEntradasAdapter(Context context, PreciosEntradas data)
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
        PreciosEntradas.Precios categoria = data.getPrecios()[position];
        holder.nombre.setText(categoria.getNombre().toUpperCase());
        holder.nombre.setTextColor(App.getColor());
        PreciosEntradas.Precios.Opciones[] opciones = categoria.getOpciones();
        for (PreciosEntradas.Precios.Opciones opcion : opciones) {
            View opcionView = inflater.inflate(R.layout.precios_entradas_opciones_list_item, null, false);
            TextView opcionNombre = (TextView) opcionView.findViewById(R.id.nombre);
            opcionNombre.setTypeface(App.getRobotoTypeface());
            opcionNombre.setText(opcion.getNombre());
            LinearLayout horariosList = (LinearLayout) opcionView.findViewById(R.id.horariosList);
            if (opcion.getHorarios() != null) {
                PreciosEntradas.Precios.Opciones.Horarios[] horarios = opcion.getHorarios();
                int horariosLength = horarios.length;
                for (int ii = 0; ii < horariosLength; ++ii) {
                    PreciosEntradas.Precios.Opciones.Horarios horario = opcion.getHorarios()[ii];
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
 */