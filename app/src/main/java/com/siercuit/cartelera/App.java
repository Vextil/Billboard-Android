package com.siercuit.cartelera;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.google.gson.Gson;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.siercuit.cartelera.utilities.Animate;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import io.vextil.billboard.R;
import io.vextil.billboard.api.API;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class App extends Application {
    public static Context context;
    private static API service;

    private static final String[] cinemaSlugs = {"montevideo", "portones", "puntacarretas", "nuevocentro"};

    private static Typeface robotoMedium;
    private static Typeface roboto;
    private static Typeface icons;

    private static Integer color;
    private static Drawable colorDrawable;
    private static ActionBarDrawerToggle toolbarDrawerToggle;
    private static DrawerLayout toolbarDrawerLayout;
    private static boolean isToggleBurger = true;
    private static AppCompatActivity activity;
    private static Gson gson;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        OkHttpClient client = new OkHttpClient();

        int cacheSize = 10 * 1024 * 2048; // 20 MiB
        File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "BillboardAppHttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        client.setCache(cache);

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.vextil.io/billboard/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(API.class);

        robotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        roboto = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        icons = Typeface.createFromAsset(context.getAssets(), "fonts/icons.ttf");

        color = context.getResources().getColor(R.color.cartelera_blue);
        colorDrawable = new ColorDrawable(color);

        gson = new Gson();

    }

    public static Gson getGson() {
        return gson;
    }

    public static void setActivity(AppCompatActivity act) {
        activity = act;
    }

    public static AppCompatActivity getActivity() {
        return activity;
    }

    public static API API() {
        return service;
    }

    public static Typeface getRobotoMediumTypeface() {
        return robotoMedium;
    }

    public static Typeface getRobotoTypeface() {
        return roboto;
    }

    public static Typeface getIconsTypeface() {
        return icons;
    }

    public static void buildToolbar(Toolbar toolbar, AppCompatActivity activity) {
        activity.setSupportActionBar(toolbar);
    }

    public static void setToolbarDrawer(ActionBarDrawerToggle toggle, DrawerLayout layout) {
        toolbarDrawerToggle = toggle;
        toolbarDrawerLayout = layout;
    }

    public static ActionBarDrawerToggle getToolbarDrawerToggle() {
        return toolbarDrawerToggle;
    }

    private static void animateToggle(final float start, float end) {
        if (start == 1) {
            toolbarDrawerToggle.setDrawerIndicatorEnabled(true);
            App.getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getToolbarDrawerToggle().onDrawerSlide(toolbarDrawerLayout, 1);
            toolbarDrawerLayout.setDrawerListener(toolbarDrawerToggle);
            toolbarDrawerToggle.syncState();
        }
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toolbarDrawerToggle.onDrawerSlide(toolbarDrawerLayout, slideOffset);
            }
        });
        if (start == 0) {
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    toolbarDrawerToggle.setDrawerIndicatorEnabled(false);
                    toolbarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().onBackPressed();
                        }
                    });
                    App.getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            });
        }
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(250);
        anim.start();
    }

    public static void animateBurgerToArrow() {
        if (isToggleBurger) {
            isToggleBurger = false;
            animateToggle(0, 1);
            toolbarDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public static void animateArrowToBurger() {
        if (!isToggleBurger) {
            isToggleBurger = true;
            animateToggle(1, 0);
            toolbarDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public static void setColorScheme(int rgbColor) {
        Integer colorFrom = color;
        color = rgbColor;
        colorDrawable = new ColorDrawable(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            App.getActivity().setTaskDescription(new ActivityManager.TaskDescription(
                    (String) getActivity().getTitle(),
                    ((BitmapDrawable) App.getActivity().getResources().getDrawable(R.drawable.ic_launcher)).getBitmap(),
                    color
            ));
        }
        Animate.actionBarColor(colorFrom, color, getActivity().getSupportActionBar());
    }

    public static Integer getColor() {
        return color;
    }

    public static Drawable getColorDrawable() {
        return colorDrawable;
    }

    public static String[] getCinemaSlugs() {
        return cinemaSlugs;
    }

}