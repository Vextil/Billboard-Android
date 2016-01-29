package io.vextil.billboard.api

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface API {

    @GET("home")
    fun getHome() : Observable<Home>

    @GET("cinema")
    fun getCinema(): Observable<Functions>
    @GET("cinema/premiere")
    fun getCinemaPremiere() : Observable<Functions>

    @GET("cinema/kids")
    fun getKids() : Observable<Functions>
    @GET("cinema/premiere/kids")
    fun getKidsPremiere() : Observable<Functions>

    @GET("theatre")
    fun getTheatre() : Observable<Functions>
    @GET("theatre/premiere")
    fun getTheatrePremiere() : Observable<Functions>

    @GET("function/{id}")
    fun getFunction(@Path("id") id: Int): Observable<Function>

    @GET("prices/tickets")
    fun getPricesTickets() : Observable<PreciosEntradas>
    @GET("prices/snacks")
    fun getPricesSnacks() : Observable<PreciosSnacks>
    @GET("prices/pro")
    fun getPricesOffers() : Observable<PreciosPromos>
}