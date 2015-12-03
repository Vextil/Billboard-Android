package com.siercuit.cartelera.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.siercuit.cartelera.App;
import com.siercuit.cartelera.POJOs.HomePOJO;
import com.siercuit.cartelera.R;
import com.siercuit.cartelera.interfaces.animationInterface;
import com.siercuit.cartelera.utilities.ProgressFragment;
import com.siercuit.cartelera.views.FontIconView;
import com.squareup.picasso.Picasso;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;

public class HomeFragment extends ProgressFragment
{
    protected View mensajeView;
    protected Map<RelativeLayout, Map<ImageView, RelativeLayout>> categorias;
    protected Map<ImageView, RelativeLayout> items;

    public HomeFragment() { }

    public static Fragment newInstance(Integer color, String title)
    {
        Fragment fragment = new HomeFragment();
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
        setContentView(R.layout.inicio_fragment);
    }

    @Override
    public void dataFetcher()
    {
        if (!isPaused()) {
            App.API().getHome(new Callback<HomePOJO>() {
                @Override
                public void success(final HomePOJO responsePOJO, retrofit.client.Response response) {
                    setData(responsePOJO, HomePOJO.class);
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
        categorias = new LinkedHashMap<RelativeLayout, Map<ImageView, RelativeLayout>>();

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout cartelerasContainer = (LinearLayout) getContentView().findViewById(R.id.homeCartelerasContainer);
        HomePOJO data = (HomePOJO) getData();

        if (data.message != null) {
            mensajeView = inflater.inflate(R.layout.mensaje_header_view, null, false);
            TextView mensaje = (TextView) mensajeView.findViewById(R.id.mensaje);
            mensaje.setText(data.message);
            cartelerasContainer.addView(mensajeView);
            mensajeView.setVisibility(View.INVISIBLE);
        }

        HomePOJO.Categories[] carteleras = data.categories;
        for (HomePOJO.Categories cartelera : carteleras) {

            HomePOJO.Categories.Items[] items = cartelera.items;
            View carteleraView = inflater.inflate(R.layout.inicio_cartelera, null);
            TextView carteleraNombre = (TextView) carteleraView.findViewById(R.id.homeCarteleraNombre);
            final RelativeLayout header = (RelativeLayout) carteleraView.findViewById(R.id.header);

            header.setVisibility(View.INVISIBLE);

            carteleraNombre.setTypeface(App.getRobotoTypeface());
            LinearLayout carteleraFuncionesContainer = (LinearLayout)
                    carteleraView.findViewById(R.id.homeCarteleraFuncionesContainer);
            carteleraNombre.setText(cartelera.name);

            this.items = new LinkedHashMap<ImageView, RelativeLayout>();

            for (final HomePOJO.Categories.Items item : items) {
                View funcionView = inflater.inflate(R.layout.inicio_cartelera_funcion, null);
                RelativeLayout funcionClickableView = (RelativeLayout) funcionView.findViewById(R.id.funcionClickableView);
                final ImageView funcionPoster = (ImageView) funcionView.findViewById(R.id.funcionPoster);
                TextView funcionNombre = (TextView) funcionView.findViewById(R.id.funcionNombre);
                FontIconView funcionLenguaje = (FontIconView) funcionView.findViewById(R.id.funcionLenguaje);
                View divider2 = funcionView.findViewById(R.id.divider2);
                FontIconView funcion3D = (FontIconView) funcionView.findViewById(R.id.funcion3D);
                final RelativeLayout footerContainer = (RelativeLayout) funcionView.findViewById(R.id.footerContainer);

                funcionPoster.setVisibility(View.INVISIBLE);
                footerContainer.setVisibility(View.INVISIBLE);

                this.items.put(funcionPoster, footerContainer);

                funcionClickableView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {

                            animationInterface callback = new animationInterface() {
                                @Override
                                public void onFinished() {
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(
                                                    R.id.frame_container,
                                                    FuncionFragment.newInstance(
                                                            item.name,
                                                            String.valueOf(item.id)
                                                    )
                                            ).addToBackStack(item.name).commit();
                                }
                            };
                            outAnimator(callback);

                        }
                        return true;
                    }
                });

                funcionNombre.setTypeface(App.getRobotoTypeface());
                funcionNombre.setText(item.name);
                Picasso.with(getActivity()).load(data.poster.url + item.poster)
                        .placeholder(R.drawable.poster_holder_big)
                        .into(funcionPoster);

                if (item.language == null) {
                    funcionView.findViewById(R.id.len3Dcontainer).setVisibility(View.GONE);
                } else {
                    funcionLenguaje.setTextColor(App.getColor());
                    if (item.language.equals("esp")) {
                        funcionLenguaje.setText(R.string.fi_esp);
                    } else {
                        funcionLenguaje.setText(R.string.fi_sub);
                    }
                    if (item.threeD) {
                        funcion3D.setTextColor(App.getColor());
                    } else {
                        funcion3D.setVisibility(View.GONE);
                        divider2.setVisibility(View.GONE);
                    }
                }

                carteleraFuncionesContainer.addView(funcionView);

            }

            categorias.put(header, this.items);
            cartelerasContainer.addView(carteleraView);
        }
    }

    @Override
    public void inAnimator()
    {
        if (mensajeView != null) {
            mensajeView.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInDown)
                    .duration(400)
                    .playOn(mensajeView);
        }
        for (final Map.Entry<RelativeLayout, Map<ImageView, RelativeLayout>> categoria : categorias.entrySet()) {
            Integer i = 0;
            categoria.getKey().setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft).duration(500).playOn(categoria.getKey());
            for (final Map.Entry<ImageView, RelativeLayout> entry : categoria.getValue().entrySet()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        entry.getKey().setVisibility(View.VISIBLE);
                        entry.getValue().setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInDown).duration(400).playOn(entry.getKey());
                        YoYo.with(Techniques.SlideInUp).duration(500).playOn(entry.getValue());
                    }
                }, i * 100);
                i = i + 1;
            }

        }
    }

    @Override
    public void outAnimator(final animationInterface callback)
    {
        int maxI = 0;
        if (mensajeView != null) {
            YoYo.with(Techniques.SlideOutUp)
                    .duration(500)
                    .playOn(mensajeView);
        }
        for (final Map.Entry<RelativeLayout, Map<ImageView, RelativeLayout>> categoria : categorias.entrySet()) {
            Integer i = 0;
            YoYo.with(Techniques.SlideOutRight).duration(500).playOn(categoria.getKey());
            for (final Map.Entry<ImageView, RelativeLayout> entry : categoria.getValue().entrySet()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.FadeOutRight).duration(300).playOn(entry.getKey());
                        YoYo.with(Techniques.SlideOutDown).duration(500).playOn(entry.getValue());
                    }
                }, i * 100);
                i = i + 1;
                if (i > maxI) {
                    maxI = i;
                }
            }

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onFinished();
            }
        }, (maxI * 100) + 300);
    }

}