package com.siercuit.cartelera.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.ads.AdView;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.siercuit.cartelera.App;
import com.siercuit.cartelera.POJOs.PreciosSnacksPOJO;
import com.siercuit.cartelera.R;
import com.siercuit.cartelera.adapters.PreciosSnacksAdapter;
import com.siercuit.cartelera.interfaces.animationInterface;
import com.siercuit.cartelera.utilities.ColumnCalculator;
import com.siercuit.cartelera.utilities.ProgressFragment;

import retrofit.Callback;
import retrofit.RetrofitError;

public class PreciosSnacksFragment extends ProgressFragment
{
    public PreciosSnacksFragment() { }

    public static Fragment newInstance(Integer color, String title)
    {
        Fragment fragment = new PreciosSnacksFragment();
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
        setEmptyText(R.string.precios_snacks_error);
        setContentView(R.layout.adaptable_list);
    }

    @Override
    public void dataFetcher()
    {
        if (!isPaused()) {
            App.API().getPreciosSnacks(new Callback<PreciosSnacksPOJO>() {
                @Override
                public void success(PreciosSnacksPOJO responsePOJO, retrofit.client.Response response) {
                    setData(responsePOJO, PreciosSnacksPOJO.class);
                }
                @Override
                public void failure(RetrofitError retrofitError) {
                    setContentEmpty(true);
                    setContentShown(true);
                }
            });
        }
    }

    @Override
    public void viewBuilder()
    {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        PreciosSnacksPOJO data = (PreciosSnacksPOJO) getData();
        ListView listView = (ListView) getContentView().findViewById(R.id.listView);
        View footerView = inflater.inflate(R.layout.ad_footer, null, false);
        AdView adView = (AdView) footerView.findViewById(R.id.adView);
        listView.addFooterView(footerView);
        PreciosSnacksAdapter adapter = new PreciosSnacksAdapter(getActivity(), data);
        adapter.setNumColumns(ColumnCalculator.get(getActivity()));

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
