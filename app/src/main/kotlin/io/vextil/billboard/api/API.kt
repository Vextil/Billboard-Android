package io.vextil.billboard.api

import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

interface API {

    @GET("home")
    fun getHome() : Observable<Home>

    @GET("/cine/cartelera/{slug}")
    fun getCineCartelera(@Path("slug") slug: String): Call<Functions>
    @GET("/cinema/premiere")
    fun getCinemaPremiere() : Call<Functions>
    @GET("/teatro/cartelera")
    fun teatroCartelera() : Call<Functions>
    @GET("/teatro/estrenos")
    fun teatroEstrenos() : Call<Functions>

    @GET("/infantiles/cartelera")
    fun infantilesCartelera() : Call<Functions>
    @GET("/infantiles/estrenos")
    fun infantilesEstrenos() : Call<Functions>

    @GET("function/{id}")
    fun getFunction(@Path("id") id: String): Observable<Function>

    @GET("/precios/entradas")
    fun preciosEntradas() : Call<PreciosEntradas>
    @GET("/precios/snacks")
    fun preciosSnacks() : Call<PreciosSnacks>
    @GET("/precios/promos")
    fun preciosPromos() : Call<PreciosPromos>
}