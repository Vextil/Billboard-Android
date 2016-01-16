package com.siercuit.cartelera.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.siercuit.cartelera.interfaces.GridItemClickInterface;
import io.vextil.billboard.api.Funciones;
import io.vextil.billboard.R;
import com.siercuit.cartelera.adapters.CineFuncionesAdapter;
import com.siercuit.cartelera.interfaces.animationInterface;
import com.siercuit.cartelera.utilities.ColumnCalculator;
import com.siercuit.cartelera.utilities.ProgressFragment;

import io.vextil.billboard.fragments.FunctionFragment;

public class InfantilesEstrenosFragment extends ProgressFragment
{
    public InfantilesEstrenosFragment() { }

    public static Fragment newInstance(Integer color, String title)
    {
        Fragment fragment = new InfantilesEstrenosFragment();
        setFragmentArguments(color, title, fragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(R.string.infantiles_estrenos_error);
        setContentView(R.layout.adaptable_list);
    }

    @Override
    public void dataFetcher()
    {
        /*if (!isPaused()) {
            App.API().getInfantilesEstrenos(new Callback<Funciones>() {
                @Override
                public void success(Funciones responsePOJO, retrofit.client.Response response) {
                    setData(responsePOJO, Funciones.class);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    setContentEmpty(true);
                    setContentShown(true);
                }
            });
        }*/
    }

    @Override
    public void viewBuilder()
    {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        Funciones data = (Funciones) getData();
        ListView listView = (ListView) getContentView().findViewById(R.id.listView);
        View footerView = inflater.inflate(R.layout.ad_footer, null, false);
        AdView adView = (AdView) footerView.findViewById(R.id.adView);
        listView.addFooterView(footerView);
        if (data.getMensaje() != null) {
            View headerView = inflater.inflate(R.layout.mensaje_header_view, null, false);
            TextView mensaje = (TextView) headerView.findViewById(R.id.mensaje);
            mensaje.setText(data.getMensaje());
            listView.addHeaderView(headerView);
        }
        CineFuncionesAdapter adapter = new CineFuncionesAdapter(getActivity(), data);
        adapter.setNumColumns(ColumnCalculator.get(getActivity()));
        adapter.setOnGridClickListener(new GridItemClickInterface()
        {
            @Override
            public void onGridItemClicked(View view, int position, long itemId)
            {
                TextView funcionNombre = (TextView) view.findViewById(R.id.funcionNombre);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(
                                R.id.frame_container,
                                FunctionFragment.Companion.newInstance(
                                        funcionNombre.getText().toString(),
                                        (String) funcionNombre.getTag()
                                )
                        ).addToBackStack(funcionNombre.getText().toString()).commit();
            }
        });

        SwingLeftInAnimationAdapter animationAdapter = new SwingLeftInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);

        setAdView(adView);
    }

    @Override
    public void inAnimator()
    {

    }

    @Override
    public void outAnimator(animationInterface callback)
    {

    }

}
