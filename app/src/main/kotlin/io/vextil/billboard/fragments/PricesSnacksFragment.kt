package io.vextil.billboard.fragments

/**
 * package com.siercuit.cartelera.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.ads.AdView;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;

import io.vextil.billboard.api.PreciosSnacks;
import io.vextil.billboard.R;
import com.siercuit.cartelera.adapters.PreciosSnacksAdapter;
import com.siercuit.cartelera.interfaces.animationInterface;
import com.siercuit.cartelera.utilities.ColumnCalculator;

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
/*if (!isPaused()) {
App.API().getPreciosSnacks(new Callback<PreciosSnacks>() {
@Override
public void success(PreciosSnacks responsePOJO, retrofit.client.Response response) {
setData(responsePOJO, PreciosSnacks.class);
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
PreciosSnacks data = (PreciosSnacks) getData();
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

 */
