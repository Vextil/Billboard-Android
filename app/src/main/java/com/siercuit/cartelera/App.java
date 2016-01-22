package com.siercuit.cartelera;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
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

    private static Typeface robotoMedium;
    private static Typeface roboto;
    private static Typeface icons;

    private static Integer color;
    private static Drawable colorDrawable;
    private static AppCompatActivity activity;


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


    public static Integer getColor() {
        return color;
    }

    public static Drawable getColorDrawable() {
        return colorDrawable;
    }

}