package com.siercuit.cartelera;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import io.vextil.billboard.api.API;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class App extends Application {
    public static Context context;
    private static API service;

    private static Typeface icons;

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient client = new OkHttpClient();

        int cacheSize = 10 * 1024 * 2048; // 20 MiB
        File cacheDirectory = new File(getCacheDir().getAbsolutePath(), "BillboardAppHttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        client.setCache(cache);

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.vextil.io/billboard/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(API.class);

        icons = Typeface.createFromAsset(getAssets(), "fonts/icons.ttf");
    }

    public static API API() {
        return service;
    }

    public static Typeface getIconsTypeface() {
        return icons;
    }

}