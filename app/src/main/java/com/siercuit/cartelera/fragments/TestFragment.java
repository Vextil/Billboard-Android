package com.siercuit.cartelera.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.siercuit.cartelera.App;
import io.vextil.billboard.R;
import com.siercuit.cartelera.adapters.CineFuncionesAdapter;
import com.siercuit.cartelera.interfaces.GridItemClickInterface;
import com.siercuit.cartelera.interfaces.animationInterface;
import com.siercuit.cartelera.utilities.ColumnCalculator;
import com.siercuit.cartelera.utilities.ProgressFragment;

import io.vextil.billboard.api.Funciones;
import io.vextil.billboard.api.Home;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class TestFragment extends ProgressFragment
{
    public TestFragment() {}

    public TestFragment (int color, String title) {
        setFragmentArguments(color, title, this);
    }

    public static Fragment newInstance(Integer color, String title)
    {
        Fragment fragment = new TestFragment();
        setFragmentArguments(color, title, fragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setRetainInstance(true);
        usesToolbarSpinner(true);
        setToolbarSpinnerResourceArray(R.array.cines_names);
        if (savedInstanceState == null) {
            setDataArrayId(App.getCinemaSlugs()[0]);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setContentView(R.layout.adaptable_list);
    }

    @Override
    public void dataFetcher()
    {
        /*if (!isPaused()) {
            App.API().getCineCartelera(getDataArrayId(), new Callback<Funciones>() {
                @Override
                public void success(Funciones responsePOJO, retrofit.client.Response response) {
                    setDataOnArrayId(responsePOJO, Funciones.class);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    setContentEmpty(true);
                    setContentShown(true);
                }
            });
        }*/

        Observable<Home> home = App.API().getHome();
        home.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Home>() {
            @Override
            public void onCompleted() {
                Log.e("a", "a");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onNext(Home home) {
                Log.e("c", "a");

            }
        });
    }

    @Override
    public void viewBuilder()
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Funciones data = (Funciones) getDataFromArray();

        View footerView = inflater.inflate(R.layout.ad_footer, null, false);

        ListView listView = (ListView) getContentView().findViewById(R.id.listView);
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
                                FuncionFragment.newInstance(
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