package com.siercuit.cartelera;

import android.app.Application;
import android.content.Context;

import java.io.File;
import io.vextil.billboard.api.API;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

public class App extends Application {

    public static Context context;
    private static API service;

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient client = new OkHttpClient();

        int cacheSize = 10 * 1024 * 2048; // 20 MiB
        File cacheDirectory = new File(getCacheDir().getAbsolutePath(), "BillboardAppHttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        client = client.newBuilder().cache(cache).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.vextil.io/billboard/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(API.class);
    }

    public static API API() {
        return service;
    }

}