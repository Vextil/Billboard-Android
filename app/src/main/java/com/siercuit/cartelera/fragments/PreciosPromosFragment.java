package com.siercuit.cartelera.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.siercuit.cartelera.App;
import io.vextil.billboard.api.PreciosPromos;
import io.vextil.billboard.R;
import com.siercuit.cartelera.adapters.PreciosPromosAdapter;
import com.siercuit.cartelera.interfaces.animationInterface;
import com.siercuit.cartelera.utilities.ProgressFragment;

import retrofit.Callback;

public class PreciosPromosFragment extends ProgressFragment
{
    public PreciosPromosFragment() { }

    public static Fragment newInstance(Integer color, String title)
    {
        Fragment fragment = new PreciosPromosFragment();
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
        setEmptyText(R.string.precios_promos_error);
        setContentView(R.layout.precios_promos);
    }

    @Override
    public void dataFetcher()
    {
        /*if (!isPaused()) {
            App.API().getPreciosPromos(new Callback<PreciosPromos>() {
                @Override
                public void success(PreciosPromos responsePOJO, retrofit.client.Response response) {
                    setData(responsePOJO, PreciosPromos.class);
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
        PreciosPromos data = (PreciosPromos) getData();
        View footerView = inflater.inflate(R.layout.ad_footer, null, false);
        AdView adView = (AdView) footerView.findViewById(R.id.adView);
        LinearLayout promosCategoriasContainer = (LinearLayout) getContentView().findViewById(R.id.promosCategoriasContainer);
        int categoriasSize = data.getCategorias().length;

        for (int i = 0; i < categoriasSize; ++i) {
            final PreciosPromos.Categorias categoria = data.getCategorias()[i];
            final PreciosPromos.Categorias.Promos[] promos = categoria.getPromos();
            if (promos != null) {
                View categoriaItemView = inflater.inflate(R.layout.precios_promos_item, null);
                TextView categoriaNombre = (TextView) categoriaItemView.findViewById(R.id.promosCategoriaNombre);
                categoriaNombre.setTypeface(App.getRobotoTypeface());
                ListView promosList = (ListView) categoriaItemView.findViewById(R.id.promosPromosList);
                categoriaNombre.setText(categoria.getNombre());
                promosList.setAdapter(new PreciosPromosAdapter(getActivity(), promos));
                promosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        PreciosPromos.Categorias.Promos promo = promos[position];
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(
                                        R.id.frame_container,
                                        PreciosPromoFragment.newInstance(
                                                App.getColor(),
                                                promo.getNombre(),
                                                promo.getDescripcion())
                                ).addToBackStack(promo.getNombre()).commit();
                    }
                });
                promosCategoriasContainer.addView(categoriaItemView);
            }
        }

        promosCategoriasContainer.addView(footerView);

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
