package io.vextil.billboard.fragments

import io.vextil.billboard.ui.RetrofitFragment
import rx.Observable


class PricesOfferFragment : RetrofitFragment() {

    override fun onGetObservable(): Observable<*> {
        throw UnsupportedOperationException()
    }

}
/**
 * package com.siercuit.cartelera.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import io.vextil.billboard.App;
import io.vextil.billboard.R;
import com.siercuit.cartelera.interfaces.animationInterface;

public class PreciosPromoFragment extends ProgressFragment
{
private String descripcion;

public PreciosPromoFragment() {}

public static Fragment newInstance(Integer color, String title, String id)
{
Fragment fragment = new PreciosPromoFragment();
setFragmentArguments(color, title, id, fragment, true);
return fragment;
}

@Override
public void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
Bundle bundle = getArguments();
descripcion = bundle.getString("id");
setRetainInstance(true);
}

@Override
public void onActivityCreated(Bundle savedInstanceState)
{
super.onActivityCreated(savedInstanceState);
setContentView(R.layout.precios_promos_promo);
}

@Override
public void dataFetcher()
{
setData(new empty(), Class.class);
}

public class empty { }

@Override
public void viewBuilder()
{
LayoutInflater inflater = LayoutInflater.from(getActivity());

View footerView = inflater.inflate(R.layout.ad_footer, null, false);
AdView adView = (AdView) footerView.findViewById(R.id.adView);
LinearLayout adContainer = (LinearLayout) getContentView().findViewById(R.id.adContainer);
TextView promoDescripcion = (TextView) getContentView().findViewById(R.id.promosPromoDescripcion);
promoDescripcion.setTypeface(App.getRobotoTypeface());
promoDescripcion.setText(descripcion);
adContainer.addView(footerView);
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
