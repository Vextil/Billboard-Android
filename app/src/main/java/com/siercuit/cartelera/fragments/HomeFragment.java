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
    protected View messageView;
    protected Map<RelativeLayout, Map<ImageView, RelativeLayout>> categories;
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
        categories = new LinkedHashMap<RelativeLayout, Map<ImageView, RelativeLayout>>();

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout categoriesContainer = (LinearLayout) getContentView().findViewById(R.id.homeCartelerasContainer);
        HomePOJO data = (HomePOJO) getData();

        if (data.message != null) {
            messageView = inflater.inflate(R.layout.mensaje_header_view, categoriesContainer, false);
            TextView mensaje = (TextView) messageView.findViewById(R.id.mensaje);
            mensaje.setText(data.message);
            categoriesContainer.addView(messageView);
            messageView.setVisibility(View.INVISIBLE);
        }

        HomePOJO.Categories[] categories = data.categories;
        for (HomePOJO.Categories category : categories) {

            HomePOJO.Categories.Items[] items = category.items;
            View categoryView = inflater.inflate(R.layout.inicio_cartelera, categoriesContainer, false);
            TextView categoryName = (TextView) categoryView.findViewById(R.id.homeCarteleraNombre);
            final RelativeLayout header = (RelativeLayout) categoryView.findViewById(R.id.header);

            header.setVisibility(View.INVISIBLE);

            categoryName.setTypeface(App.getRobotoTypeface());
            LinearLayout categoryItemsContainer = (LinearLayout)
                    categoryView.findViewById(R.id.homeCarteleraFuncionesContainer);
            categoryName.setText(category.name);

            this.items = new LinkedHashMap<ImageView, RelativeLayout>();

            for (final HomePOJO.Categories.Items item : items) {
                View itemView = inflater.inflate(R.layout.inicio_cartelera_funcion, categoryItemsContainer, false);
                RelativeLayout itemClickableView = (RelativeLayout) itemView.findViewById(R.id.funcionClickableView);
                final ImageView itemPoster = (ImageView) itemView.findViewById(R.id.funcionPoster);
                TextView itemName = (TextView) itemView.findViewById(R.id.funcionNombre);
                FontIconView itemLanguage = (FontIconView) itemView.findViewById(R.id.funcionLenguaje);
                View divider2 = itemView.findViewById(R.id.divider2);
                FontIconView item3D = (FontIconView) itemView.findViewById(R.id.funcion3D);
                final RelativeLayout footerContainer = (RelativeLayout) itemView.findViewById(R.id.footerContainer);

                itemPoster.setVisibility(View.INVISIBLE);
                footerContainer.setVisibility(View.INVISIBLE);

                this.items.put(itemPoster, footerContainer);

                itemClickableView.setOnTouchListener(new View.OnTouchListener() {
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

                itemName.setTypeface(App.getRobotoTypeface());
                itemName.setText(item.name);
                Picasso.with(getActivity()).load(data.poster.url + item.poster)
                        .placeholder(R.drawable.poster_holder_big)
                        .into(itemPoster);

                if (item.language == null) {
                    itemView.findViewById(R.id.len3Dcontainer).setVisibility(View.GONE);
                } else {
                    itemLanguage.setTextColor(App.getColor());
                    if (item.language.equals("esp")) {
                        itemLanguage.setText(R.string.fi_esp);
                    } else {
                        itemLanguage.setText(R.string.fi_sub);
                    }
                    if (item.threeD) {
                        item3D.setTextColor(App.getColor());
                    } else {
                        item3D.setVisibility(View.GONE);
                        divider2.setVisibility(View.GONE);
                    }
                }

                categoryItemsContainer.addView(itemView);

            }

            this.categories.put(header, this.items);
            categoriesContainer.addView(categoryView);
        }
    }

    @Override
    public void inAnimator()
    {
        if (messageView != null) {
            messageView.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInDown)
                    .duration(400)
                    .playOn(messageView);
        }
        for (final Map.Entry<RelativeLayout, Map<ImageView, RelativeLayout>> category : categories.entrySet()) {
            Integer i = 0;
            category.getKey().setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft).duration(500).playOn(category.getKey());
            for (final Map.Entry<ImageView, RelativeLayout> entry : category.getValue().entrySet()) {
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
        if (messageView != null) {
            YoYo.with(Techniques.SlideOutUp)
                    .duration(500)
                    .playOn(messageView);
        }
        for (final Map.Entry<RelativeLayout, Map<ImageView, RelativeLayout>> categoria : categories.entrySet()) {
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