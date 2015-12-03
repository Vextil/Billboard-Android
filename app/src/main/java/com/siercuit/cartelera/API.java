package com.siercuit.cartelera;

import com.siercuit.cartelera.POJOs.FuncionPOJO;
import com.siercuit.cartelera.POJOs.FuncionesPOJO;
import com.siercuit.cartelera.POJOs.HomePOJO;
import com.siercuit.cartelera.POJOs.PreciosEntradasPOJO;
import com.siercuit.cartelera.POJOs.PreciosPromosPOJO;
import com.siercuit.cartelera.POJOs.PreciosSnacksPOJO;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface API
{
    @GET("/")
    public void getHome(Callback<HomePOJO> callback);

    @GET("/cine/cartelera/{slug}")
    public void getCineCartelera(@Path("slug") String slug, Callback<FuncionesPOJO> callback);
    @GET("/cine/estrenos")
    public void getCineEstrenos(Callback<FuncionesPOJO> callback);

    @GET("/teatro/cartelera")
    public void getTeatroCartelera(Callback<FuncionesPOJO> callback);
    @GET("/teatro/estrenos")
    public void getTeatroEstrenos(Callback<FuncionesPOJO> callback);

    @GET("/infantiles/cartelera")
    public void getInfantilesCartelera(Callback<FuncionesPOJO> callback);
    @GET("/infantiles/estrenos")
    public void getInfantilesEstrenos(Callback<FuncionesPOJO> callback);

    @GET("/funcion/{id}")
    public void getFuncion(@Path("id") String id, Callback<FuncionPOJO> callback);

    @GET("/precios/entradas")
    public void getPreciosEntradas(Callback<PreciosEntradasPOJO> callback);
    @GET("/precios/snacks")
    public void getPreciosSnacks(Callback<PreciosSnacksPOJO> callback);
    @GET("/precios/promos")
    public void getPreciosPromos(Callback<PreciosPromosPOJO> callback);
}