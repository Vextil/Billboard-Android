package com.siercuit.cartelera;

import io.vextil.billboard.services.FuncionService;
import io.vextil.billboard.services.FuncionesService;
import io.vextil.billboard.services.PreciosEntradasService;
import io.vextil.billboard.services.PreciosPromosService;
import io.vextil.billboard.services.PreciosSnacksService;

import io.vextil.billboard.services.HomeService;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface API
{
    @GET("/")
    public void getHome(Callback<HomeService> callback);

    @GET("/cine/cartelera/{slug}")
    public void getCineCartelera(@Path("slug") String slug, Callback<FuncionesService> callback);
    @GET("/cine/estrenos")
    public void getCineEstrenos(Callback<FuncionesService> callback);

    @GET("/teatro/cartelera")
    public void getTeatroCartelera(Callback<FuncionesService> callback);
    @GET("/teatro/estrenos")
    public void getTeatroEstrenos(Callback<FuncionesService> callback);

    @GET("/infantiles/cartelera")
    public void getInfantilesCartelera(Callback<FuncionesService> callback);
    @GET("/infantiles/estrenos")
    public void getInfantilesEstrenos(Callback<FuncionesService> callback);

    @GET("/funcion/{id}")
    public void getFuncion(@Path("id") String id, Callback<FuncionService> callback);

    @GET("/precios/entradas")
    public void getPreciosEntradas(Callback<PreciosEntradasService> callback);
    @GET("/precios/snacks")
    public void getPreciosSnacks(Callback<PreciosSnacksService> callback);
    @GET("/precios/promos")
    public void getPreciosPromos(Callback<PreciosPromosService> callback);
}