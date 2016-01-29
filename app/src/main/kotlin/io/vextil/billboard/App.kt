package io.vextil.billboard

import android.app.Application
import android.content.Context

import java.io.File
import io.vextil.billboard.api.API
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.RxJavaCallAdapterFactory
import kotlin.properties.Delegates

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        var client = OkHttpClient()
        val cacheSize = 10 * 1024 * 2048 // 20 MiB
        val cacheDirectory = File(cacheDir.absolutePath, "BillboardAppHttpCache")
        val cache = Cache(cacheDirectory, cacheSize.toLong())
        client = client.newBuilder().cache(cache).build()

        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.vextil.io/billboard/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(API::class.java)
    }

    companion object {
        private var service: API by Delegates.notNull()

        fun API(): API {
            return service
        }

    }

}