package com.siercuit.cartelera.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdView;
import com.siercuit.cartelera.App;
import io.vextil.billboard.services.FuncionService;
import com.siercuit.cartelera.R;
import com.siercuit.cartelera.adapters.FuncionHorariosAdapter;
import com.siercuit.cartelera.interfaces.animationInterface;
import com.siercuit.cartelera.interfaces.colorSchemeInterface;
import com.siercuit.cartelera.utilities.Animate;
import com.siercuit.cartelera.utilities.ProgressFragment;
import com.siercuit.cartelera.views.ExpandablePanel;
import com.siercuit.cartelera.views.FontIconView;
import com.siercuit.cartelera.views.IndicatorView;
import com.siercuit.cartelera.views.SmileyRatingView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;

public class FuncionFragment extends ProgressFragment
{
    private String id;
    private int lastExpandedPosition = -1;

    @InjectView(R.id.funcionDescripcionContainer) ExpandablePanel funcionDescripcionContainer;
    @InjectView(R.id.funcionDescripcionIndicator) IndicatorView funcionDescripcionIndicator;
    @InjectView(R.id.funcionPoster) ImageView funcionPoster;
    @InjectView(R.id.funcionRating) SmileyRatingView funcionRating;
    @InjectView(R.id.divider2) View divider2;
    @InjectView(R.id.funcionLenguaje) FontIconView funcionLenguaje;
    @InjectView(R.id.funcion3D) FontIconView funcion3D;
    @InjectView(R.id.iconEstreno) FontIconView iconEstreno;
    @InjectView(R.id.iconDuracion) FontIconView iconDuracion;
    @InjectView(R.id.iconGenero) FontIconView iconGenero;
    @InjectView(R.id.iconClasificacion) FontIconView iconClasificacion;
    @InjectView(R.id.funcionRatingText) TextView funcionRatingText;
    @InjectView(R.id.funcionEstreno) TextView funcionEstreno;
    @InjectView(R.id.funcionDuracion) TextView funcionDuracion;
    @InjectView(R.id.funcionGenero) TextView funcionGenero;
    @InjectView(R.id.funcionClasificacion) TextView funcionClasificacion;
    @InjectView(R.id.funcionDescripcion) TextView funcionDescripcion;
    @InjectView(R.id.funcionDescripcionTitulo) TextView funcionDescripcionTitulo;
    @InjectView(R.id.headerText) TextView headerText;
    @InjectView(R.id.headerText2) TextView headerText2;
    @InjectView(R.id.header) RelativeLayout header;
    @InjectView(R.id.header2) RelativeLayout header2;
    @InjectView(R.id.row1) LinearLayout row1;
    @InjectView(R.id.row2) LinearLayout row2;
    @InjectView(R.id.row3) LinearLayout row3;
    @InjectView(R.id.row4) LinearLayout row4;
    @InjectView(R.id.row5) LinearLayout row5;

    public FuncionFragment(){}

    public static Fragment newInstance(String title, String id)
    {
        Fragment fragment = new FuncionFragment();
        setFragmentArguments(title, id, fragment, true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(R.string.cine_funcion_error);
        setContentView(R.layout.funcion);
    }

    @Override
    public void dataFetcher()
    {
        if (!isPaused()) {

                App.API().getFuncion(id, new Callback<FuncionService>() {
                    @Override
                    public void success(FuncionService responsePOJO, retrofit.client.Response response) {
                        setData(responsePOJO, FuncionService.class);
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
        final View view = getContentView();
        final View headerView = inflater.inflate(R.layout.funcion_header, null, false);
        final View footerView = inflater.inflate(R.layout.ad_footer, null, false);

        ButterKnife.inject(this, headerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            funcionPoster.setTransitionName(id);
        }

        FuncionService data = (FuncionService) getData();
        FuncionService.Funcion funcion = data.getFuncion();

        funcionDescripcionContainer.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                funcionDescripcionIndicator.setState(false);
            }

            public void onExpand(View handle, View content) {
                funcionDescripcionIndicator.setState(true);
            }
        });
        funcionDescripcionIndicator.setState(false);

        iconEstreno.setTextColor(App.getColor());
        iconDuracion.setTextColor(App.getColor());
        iconGenero.setTextColor(App.getColor());
        iconClasificacion.setTextColor(App.getColor());
        funcionRating.setTextColor(App.getColor());
        funcionLenguaje.setTextColor(App.getColor());
        funcion3D.setTextColor(App.getColor());

        funcionRatingText.setTypeface(App.getRobotoTypeface());
        funcionDescripcionTitulo.setTypeface(App.getRobotoTypeface());
        funcionDescripcion.setTypeface(App.getRobotoTypeface());
        funcionEstreno.setTypeface(App.getRobotoTypeface());
        funcionDuracion.setTypeface(App.getRobotoTypeface());
        funcionGenero.setTypeface(App.getRobotoTypeface());
        funcionClasificacion.setTypeface(App.getRobotoTypeface());
        headerText.setTypeface(App.getRobotoTypeface());
        headerText2.setTypeface(App.getRobotoTypeface());

        funcionDescripcion.setText(funcion.getDescripcion());
        funcionRating.setRating(funcion.getRating());
        funcionEstreno.setText(funcion.getEstreno());
        funcionPoster.setImageResource(R.drawable.poster_holder_big);
        Picasso.with(getActivity()).load(data.getPoster_url_grande() + funcion.getPoster()
                + data.getPoster_extension())
                .placeholder(R.drawable.poster_holder_big)
                .into(funcionPoster, new com.squareup.picasso.Callback.EmptyCallback() {
                    @Override public void onSuccess() {
                        App.setColorScheme(((BitmapDrawable)funcionPoster.getDrawable()).getBitmap(), new colorSchemeInterface() {
                            @Override
                            public void onPaletteGenerated(Integer color) {
                                Animate.textColor(App.getColorNone(), color, new TextView[]{
                                        funcionRating, funcionLenguaje, funcion3D, iconEstreno
                                        ,iconDuracion, iconGenero, iconClasificacion

                                });
                            }
                        });
                    }
                });
        funcionDuracion.setText(funcion.getDuracion());
        funcionGenero.setText(funcion.getGenero());
        funcionClasificacion.setText(funcion.getClasificacion());
        funcionRating.setRating(funcion.getRating());
        funcionRatingText.setText(funcion.getRating() + "%");

        final ExpandableListView movieHorariosList = (ExpandableListView) view.findViewById(R.id.movieHorarios);

        if (funcion.getLenguaje().equals("esp")) {
            funcionLenguaje.setText(R.string.fi_esp);
        } else {
            funcionLenguaje.setText(R.string.fi_sub);
        }
        if (funcion.getThreeD()) {
            funcion3D.setVisibility(View.VISIBLE);
            divider2.setVisibility(View.VISIBLE);
        } else {
            funcion3D.setVisibility(View.INVISIBLE);
            divider2.setVisibility(View.INVISIBLE);
        }

        movieHorariosList.addHeaderView(headerView);
        movieHorariosList.addFooterView(footerView);
        movieHorariosList.setAdapter(new FuncionHorariosAdapter(getActivity(), funcion.getSalas()));
        movieHorariosList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    movieHorariosList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        funcionPoster.setVisibility(View.INVISIBLE);
        header.setVisibility(View.INVISIBLE);
        header2.setVisibility(View.INVISIBLE);
        row1.setVisibility(View.INVISIBLE);
        row2.setVisibility(View.INVISIBLE);
        row3.setVisibility(View.INVISIBLE);
        row4.setVisibility(View.INVISIBLE);
        row5.setVisibility(View.INVISIBLE);
        funcionDescripcionContainer.setVisibility(View.INVISIBLE);

        AdView adView = (AdView) footerView.findViewById(R.id.adView);
        setAdView(adView);

    }

    @Override
    public void inAnimator()
    {
        funcionPoster.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInDown).duration(600).playOn(funcionPoster);
        header.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInLeft).duration(600).playOn(header);
        header2.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInLeft).duration(600).playOn(header2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                row1.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInLeft).duration(400).playOn(row1);
            }
        }, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                row2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInLeft).duration(400).playOn(row2);
            }
        }, 100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                row3.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInLeft).duration(400).playOn(row3);
            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                row4.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInLeft).duration(400).playOn(row4);
            }
        }, 300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                row5.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInLeft).duration(400).playOn(row5);
            }
        }, 400);

        funcionDescripcionContainer.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp).duration(600).playOn(funcionDescripcionContainer);
    }

    @Override
    public void outAnimator(animationInterface callback)
    {

    }

}
