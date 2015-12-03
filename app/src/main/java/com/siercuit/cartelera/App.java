package com.siercuit.cartelera;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.siercuit.cartelera.interfaces.colorSchemeInterface;
import com.siercuit.cartelera.mappers.DrawerNavItemMapper;
import com.siercuit.cartelera.utilities.Animate;
import com.siercuit.cartelera.utilities.ProgressFragment;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class App extends Application
{
    public static Context context;
    private static API service;

    private static final String[] cinemaNames = {"Montevideo", "Portones", "Punta Carretas", "Nuevocentro"};
    private static final String[] cinemaSlugs = {"montevideo", "portones", "puntacarretas", "nuevocentro"};

    private static Typeface robotoMedium;
    private static Typeface roboto;
    private static Typeface icons;

    private static Integer color;
    private static Integer colorNone;
    private static Drawable colorDrawable;
    private static Map<String, Integer> iconsMap = new HashMap<String, Integer>();
    private static List<DrawerNavItemMapper> navItems = new ArrayList<DrawerNavItemMapper>();
    private static ActionBarDrawerToggle toolbarDrawerToggle;
    private static DrawerLayout toolbarDrawerLayout;
    private static boolean isToggleBurger = true;
    private static ActionBarActivity activity;
    private static Gson gson;

    private static final String PROPERTY_ID = "UA-51154229-3";
    public static int GENERAL_TRACKER = 0;
    public enum TrackerName {
        APP_TRACKER
    }
    HashMap<TrackerName, Tracker> trackers = new HashMap<TrackerName, Tracker>();


    @Override
    public void onCreate()
    {
        super.onCreate();

        context = getApplicationContext();

        OkHttpClient client = new OkHttpClient();

        int cacheSize = 10 * 1024 * 2048; // 20 MiB
        File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "CarteleraAppHttpCache");
        Cache cache = null;
        try {
            cache = new Cache(cacheDirectory, cacheSize);
            client.setCache(cache);
        } catch (IOException e) {

        }

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(Link());
        builder.setClient(new OkClient(client));
        service = builder.build().create(API.class);

        robotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        roboto = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        icons = Typeface.createFromAsset(context.getAssets(), "fonts/icons.ttf");

        color = context.getResources().getColor(R.color.cartelera_blue);
        colorDrawable = new ColorDrawable(color);
        colorNone = context.getResources().getColor(R.color.material_blue_grey_800);

        gson = new Gson();

        Tracker t = getTracker(TrackerName.APP_TRACKER);
        t.enableAdvertisingIdCollection(true);

        setNavItems();
        setIconsMap();
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!trackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
                    : analytics.newTracker(PROPERTY_ID);
            trackers.put(trackerId, t);
        }
        return trackers.get(trackerId);
    }

    public static ProgressFragment getCurrentFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getName();
        return (ProgressFragment) getActivity().getSupportFragmentManager()
                .findFragmentByTag(fragmentTag);
    }

    public static Gson getGson()
    {
        return gson;
    }

    public static void setActivity(ActionBarActivity act)
    {
        activity = act;
    }

    public static ActionBarActivity getActivity()
    {
        return activity;
    }

    public static API API()
    {
        return service;
    }

    public static List<DrawerNavItemMapper> getNavItems() { return navItems; }

    public static Typeface getRobotoMediumTypeface()
    {
        return robotoMedium;
    }

    public static Typeface getRobotoTypeface()
    {
        return roboto;
    }

    public static Typeface getIconsTypeface()
    {
        return icons;
    }

    public static void buildToolbar(Toolbar toolbar, ActionBarActivity activity)
    {
        activity.setSupportActionBar(toolbar);
    }

    public static void setToolbarDrawer(ActionBarDrawerToggle toggle, DrawerLayout layout)
    {
        toolbarDrawerToggle = toggle;
        toolbarDrawerLayout = layout;
    }

    public static ActionBarDrawerToggle getToolbarDrawerToggle()
    {
        return toolbarDrawerToggle;
    }

    private static void animateToggle(final float start, float end)
    {
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
            anim.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
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

    public static void animateBurgerToArrow()
    {
        if (isToggleBurger) {
            isToggleBurger = false;
            animateToggle(0, 1);
            toolbarDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public static void animateArrowToBurger()
    {
        if (!isToggleBurger) {
            isToggleBurger = true;
            animateToggle(1, 0);
            toolbarDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public static Boolean isToggleBurger()
    {
        return isToggleBurger;
    }

    public static void setColorScheme(int rgbColor)
    {
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

    public static void setColorScheme(Bitmap bitmap)
    {
        setColorScheme(bitmap, new colorSchemeInterface() {
            @Override
            public void onPaletteGenerated(Integer color) {}
        });
    }

    public static void setColorScheme(Bitmap bitmap, final colorSchemeInterface callback)
    {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                Integer paletteColor;
                if (palette.getVibrantColor(0) == 0
                        || palette.getMutedColor(0) != 0
                        && palette.getVibrantSwatch().getPopulation() < palette.getMutedSwatch().getPopulation()) {
                    paletteColor = palette.getMutedColor(getActivity().getResources().getColor(R.color.material_blue_grey_800));
                } else {
                    paletteColor = palette.getVibrantColor(getActivity().getResources().getColor(R.color.material_blue_grey_800));
                }
                setColorScheme(paletteColor);
                callback.onPaletteGenerated(paletteColor);
            }
        });
    }

    public static Integer getColor()
    {
        return color;
    }

    public static Integer getColorNone()
    {
        return colorNone;
    }

    public static Drawable getColorDrawable()
    {
        return colorDrawable;
    }

    public static String Link()
    {
        return "https://api.vextil.io/billboard/";
    }

    public static String[] getCinemaNames()
    {
        return cinemaNames;
    }

    public static String[] getCinemaSlugs()
    {
        return cinemaSlugs;
    }

    public static int getIconByName(String name)
    {
        return iconsMap.get(name);
    }

    private void setNavItems()
    {
        List<String> child;
        List<Integer> childIcons;
        List<String> childFragments;

        // INICIO group
        navItems.add(new DrawerNavItemMapper("Inicio", R.string.fi_house, R.color.cartelera_blue, "fragments.InicioFragment"));

        // CINE group
        child = new ArrayList<String>();
        childIcons = new ArrayList<Integer>();
        childFragments = new ArrayList<String>();
        child.add("Cartelera");
        child.add("Estrenos");
        childIcons.add(R.string.fi_sign);
        childIcons.add(R.string.fi_star);
        childFragments.add("fragments.CineCarteleraFragment");
        childFragments.add("fragments.CineEstrenosFragment");
        navItems.add(new DrawerNavItemMapper("Cine", R.string.fi_movie_cinema, R.color.cartelera_purple,
                child, childIcons, childFragments));

        // INFANTILES group
        child = new ArrayList<String>();
        childIcons = new ArrayList<Integer>();
        childFragments = new ArrayList<String>();
        child.add("Cartelera");
        child.add("Estrenos");
        childIcons.add(R.string.fi_sign);
        childIcons.add(R.string.fi_star);
        childFragments.add("fragments.InfantilesCarteleraFragment");
        childFragments.add("fragments.InfantilesEstrenosFragment");
        navItems.add(new DrawerNavItemMapper("Infantiles", R.string.fi_baby, R.color.cartelera_yellow,
                child, childIcons, childFragments));

        // TEATRO group
        child = new ArrayList<String>();
        childIcons = new ArrayList<Integer>();
        childFragments = new ArrayList<String>();
        child.add("Cartelera");
        child.add("Estrenos");
        childIcons.add(R.string.fi_sign);
        childIcons.add(R.string.fi_star);
        childFragments.add("fragments.TeatroCarteleraFragment");
        childFragments.add("fragments.TeatroEstrenosFragment");
        navItems.add(new DrawerNavItemMapper("Teatro", R.string.fi_masks, R.color.cartelera_orange,
                child, childIcons, childFragments));

        // PRECIOS group
        child = new ArrayList<String>();
        childIcons = new ArrayList<Integer>();
        childFragments = new ArrayList<String>();
        child.add("Entradas");
        child.add("Snacks");
        child.add("Promos");
        childIcons.add(R.string.fi_ticket);
        childIcons.add(R.string.fi_food);
        childIcons.add(R.string.fi_gift);
        childFragments.add("fragments.PreciosEntradasFragment");
        childFragments.add("fragments.PreciosSnacksFragment");
        childFragments.add("fragments.PreciosPromosFragment");
        navItems.add(new DrawerNavItemMapper("Precios", R.string.fi_pig, R.color.cartelera_green,
                child, childIcons, childFragments));

        // OPCIONES group
        //navItems.add(new DrawerNavItemMapper("Opciones", R.string.fi_settings, R.color.card_grey_text, "fragments.OpcionesFragment"));
    }

    private static void setIconsMap()
    {
        iconsMap.put("baby", R.string.fi_baby);
        iconsMap.put("earth", R.string.fi_earth);
        iconsMap.put("calendar", R.string.fi_calendar);
        iconsMap.put("camera", R.string.fi_camera_video);
        iconsMap.put("clock", R.string.fi_clock);
        iconsMap.put("house", R.string.fi_house);
        iconsMap.put("pin", R.string.fi_pin);
        iconsMap.put("trash", R.string.fi_trash);
        iconsMap.put("support", R.string.fi_support);
        iconsMap.put("sign", R.string.fi_sign);
        iconsMap.put("eye", R.string.fi_eye);
        iconsMap.put("settings", R.string.fi_settings);
        iconsMap.put("movie", R.string.fi_movie_cinema);
        iconsMap.put("money", R.string.fi_money);
        iconsMap.put("creditcard", R.string.fi_creditcard);
        iconsMap.put("bug", R.string.fi_bug);
        iconsMap.put("smile_very_sad", R.string.fi_smile_very_sad);
        iconsMap.put("smile_sad", R.string.fi_smile_sad);
        iconsMap.put("smile_super_sad", R.string.fi_smile_super_sad);
        iconsMap.put("smile_happy", R.string.fi_smile_happy);
        iconsMap.put("smile_very_happy", R.string.fi_smile_very_happy);
        iconsMap.put("smile_super_happy", R.string.fi_smile_super_happy);
        iconsMap.put("smile_normal", R.string.fi_smile_normal);
        iconsMap.put("thumb_up", R.string.fi_thumb_up);
        iconsMap.put("thumb_down", R.string.fi_thumb_down);
        iconsMap.put("star", R.string.fi_star);
        iconsMap.put("ok", R.string.fi_ok);
        iconsMap.put("delete", R.string.fi_delete);
        iconsMap.put("plus", R.string.fi_plus);
        iconsMap.put("delete", R.string.fi_delete);
        iconsMap.put("plus", R.string.fi_plus);
        iconsMap.put("pig", R.string.fi_pig);
        iconsMap.put("arrow_right_round", R.string.fi_arrow_right_round);
        iconsMap.put("arrow_left_round", R.string.fi_arrow_left_round);
        iconsMap.put("minus", R.string.fi_minus);
        iconsMap.put("sync", R.string.fi_sync);
        iconsMap.put("minus", R.string.fi_minus);
        iconsMap.put("user", R.string.fi_user);
        iconsMap.put("facebook", R.string.fi_facebook);
        iconsMap.put("twitter", R.string.fi_twitter);
        iconsMap.put("youtube", R.string.fi_youtube);
        iconsMap.put("warning", R.string.fi_warning_round);
        iconsMap.put("comment", R.string.fi_comment);
        iconsMap.put("heart", R.string.fi_heart);
        iconsMap.put("search", R.string.fi_search);
        iconsMap.put("key", R.string.fi_key);
        iconsMap.put("lightbulb", R.string.fi_lightbulb);
        iconsMap.put("ok", R.string.fi_ok);
        iconsMap.put("esp", R.string.fi_esp);
        iconsMap.put("3d", R.string.fi_3d);
        iconsMap.put("masks", R.string.fi_masks);
        iconsMap.put("ticket", R.string.fi_ticket);
        iconsMap.put("food", R.string.fi_food);
        iconsMap.put("gift", R.string.fi_gift);
        iconsMap.put("sub", R.string.fi_sub);
        iconsMap.put("tag", R.string.fi_tag);
        iconsMap.put("bomb", R.string.fi_bomb);
        iconsMap.put("cup", R.string.fi_cup);
        iconsMap.put("ghost", R.string.fi_ghost);
        iconsMap.put("download", R.string.fi_download);
        iconsMap.put("mail", R.string.fi_mail);
        iconsMap.put("message", R.string.fi_message);
        iconsMap.put("market", R.string.fi_market);
        iconsMap.put("play", R.string.fi_play);
        iconsMap.put("users", R.string.fi_users);
        iconsMap.put("cart", R.string.fi_cart);
    }

}